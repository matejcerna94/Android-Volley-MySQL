package com.matejcerna.tabapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableActivity extends AppCompatActivity {
    static ArrayList<Table> tablesList;
    @BindView(R.id.recycler_view_tables)
    RecyclerView recyclerViewTables;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);

        recyclerViewTables.setHasFixedSize(true);
        recyclerViewTables.setLayoutManager(new GridLayoutManager(this, 3));
        tablesList = new ArrayList<>();
        fetchTables();

    }

    private void fetchTables() {
        String url = "https://low-pressure-lists.000webhostapp.com/fetch_tables.php";
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Please wait");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
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
                    jsonArray = jsonObject.getJSONArray("table");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    Table table = null;
                    try {
                        table = gson.fromJson(jsonArray.get(i).toString(), Table.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tablesList.add(table);
                }


                recyclerViewAdapter = new RecyclerViewAdapter(TableActivity.this, (ArrayList<Table>) tablesList);
                recyclerViewTables.setAdapter(recyclerViewAdapter);

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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
