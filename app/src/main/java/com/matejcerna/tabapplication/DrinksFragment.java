package com.matejcerna.tabapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.matejcerna.tabapplication.TableActivity.tablesList;

public class DrinksFragment extends Fragment {
    static ArrayList<Category_name> drinksList;
    @BindView(R.id.recycler_view_drinks)
    RecyclerView recyclerViewDrinks;
    String string_table_id;
    String category_name="Drinks";

    private RecyclerViewAdapterCategoryName recyclerViewAdapterCategoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);
        ButterKnife.bind(this, view);

        int position = ((PocetnaActivity) getActivity()).getIntent().getExtras().getInt("key");
        string_table_id = tablesList.get(position).getTable_id();
        Log.d("KOD DRINKS TABLE ID", string_table_id);
        //kategorija=((PocetnaActivity) getActivity()).ime_kategorije;
        //Log.d("GETMYDATA", kategorija);


        recyclerViewDrinks.setHasFixedSize(true);
        recyclerViewDrinks.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        drinksList = new ArrayList<>();
        fetchCategoryByName();
        return view;
    }


    private void fetchCategoryByName() {
        String url = "https://low-pressure-lists.000webhostapp.com/try.php";
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, "Please wait");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Log.d("CATEGORY_NAME", String.valueOf(response));
                }
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("item_join");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    Category_name categoryName = null;
                    try {
                        categoryName = gson.fromJson(jsonArray.get(i).toString(), Category_name.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    drinksList.add(categoryName);
                }


                recyclerViewAdapterCategoryName = new RecyclerViewAdapterCategoryName(getActivity(), (ArrayList<Category_name>) drinksList);
                recyclerViewDrinks.setAdapter(recyclerViewAdapterCategoryName);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("category_name", category_name);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}
