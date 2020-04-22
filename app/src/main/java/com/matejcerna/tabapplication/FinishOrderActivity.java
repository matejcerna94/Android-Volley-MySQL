package com.matejcerna.tabapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class FinishOrderActivity extends AppCompatActivity {
    static ArrayList<Order> ordersList;
    public int position;
    public int table_id;
    public String table_name;
    @BindView(R.id.recycler_view_orders)
    RecyclerView recyclerViewOrders;
    @BindView(R.id.text_view_order_total)
    TextView textViewOrderTotal;

    private RecyclerViewAdapterOrders recyclerViewAdapterOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);
        ButterKnife.bind(this);

        position = getIntent().getExtras().getInt("key_table_position_finish_order");
        table_id = Integer.parseInt(tablesList.get(position).getTable_id());
        table_name = tablesList.get(position).getTable_name();
        Log.d("KOD table id finish", String.valueOf(table_id));
        Log.d("KOD table name finish", table_name);

        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        ordersList = new ArrayList<>();
        fetchOrdersForSingleTable();
        fetchOrderTotal();
    }

    private void fetchOrderTotal() {
        String url = "https://low-pressure-lists.000webhostapp.com/fetch_order_total.php";
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Please wait");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Log.d("SUM_TOTAL", String.valueOf(response));
                    textViewOrderTotal.setText(response.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FinishOrderActivity.this);
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

                params.put("table_id", String.valueOf(table_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void fetchOrdersForSingleTable() {
        String url = "https://low-pressure-lists.000webhostapp.com/fetch_order_for_single_table.php";
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Please wait");
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
                    jsonArray = jsonObject.getJSONArray("order");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    Order order = null;
                    try {
                        order = gson.fromJson(jsonArray.get(i).toString(), Order.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ordersList.add(order);
                }


                recyclerViewAdapterOrders = new RecyclerViewAdapterOrders(FinishOrderActivity.this, (ArrayList<Order>) ordersList);
                recyclerViewOrders.setAdapter(recyclerViewAdapterOrders);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FinishOrderActivity.this);
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

                params.put("table_id", String.valueOf(table_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
