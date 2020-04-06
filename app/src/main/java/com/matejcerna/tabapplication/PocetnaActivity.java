package com.matejcerna.tabapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.matejcerna.tabapplication.TableActivity.tablesList;

public class PocetnaActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.tabs)
    TabLayout tab;
    @Nullable
    @BindView(R.id.pager)
    ViewPager viewPager;
    // Fragment List
    private List<Category> categoryList = new ArrayList<>();
    // Title List
    private final List<String> categories = new ArrayList<>();
    String string_table_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna);
        ButterKnife.bind(this);


        int position = getIntent().getExtras().getInt("key");
        string_table_id = tablesList.get(position).getTable_id();
        Log.d("KOD", string_table_id);

        fetchCategories();

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    private void fetchCategories() {
        String url = "https://low-pressure-lists.000webhostapp.com/fetch_categories.php";
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Please wait");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Log.d("Codeeeee", String.valueOf(response));
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("category");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    Category category = null;
                    try {
                        category = gson.fromJson(jsonArray.get(i).toString(), Category.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    categoryList.add(category);
                }

                for (int i = 0; i < categoryList.size(); i++) {
                    Category category = (Category) categoryList.get(i);
                    categories.add(category.getCategory_name());
                    tab.addTab(tab.newTab().setText(category.getCategory_name()));

                }

                CategoriesPagerAdapter adapter = new CategoriesPagerAdapter(getSupportFragmentManager(), categoryList, categories);
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(1);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
//Bonus Code : If your tab layout has more than 2 tabs then tab will scroll other wise they will take whole width of the screen
                if (tab.getTabCount() == 2) {
                    tab.setTabMode(TabLayout.MODE_FIXED);
                } else {
                    tab.setTabMode(TabLayout.MODE_SCROLLABLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
                alertDialog.setMessage("Ups, došlo je do pogreške.").setCancelable(false)
                        .setPositiveButton("U redu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                progressDialog.dismiss();
                            }


                        });
                AlertDialog alert = alertDialog.create();
                alert.setTitle("Greška");
                alert.show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Response<JSONObject> resp = super.parseNetworkResponse(response);
                if (!resp.isSuccess()) {
                    return resp;
                }
                long now = System.currentTimeMillis();
                Cache.Entry entry = resp.cacheEntry;
                if (entry == null) {
                    categoryList.clear();
                    entry = new Cache.Entry();
                    entry.data = response.data;
                    entry.responseHeaders = response.headers;
                }
                entry.ttl = now + 300 * 1000;  //keeps cache for 5 min

                return Response.success(resp.result, entry);
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }


}