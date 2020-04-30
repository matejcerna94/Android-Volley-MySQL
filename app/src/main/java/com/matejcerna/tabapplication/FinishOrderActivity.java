package com.matejcerna.tabapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import butterknife.OnClick;

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

    ArrayList<OrderTotal> orderTotals;
    @BindView(R.id.edit_text_enter_cash_amount)
    EditText editTextEnterCashAmount;
    @BindView(R.id.text_view_refund)
    TextView textViewRefund;
    String string_total;
    int refund;
    int cash_amount;
    String string_cash_amount;
    String table_availability="";

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
        getSupportActionBar().setTitle("Orders for " + table_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        ordersList = new ArrayList<>();
        orderTotals = new ArrayList<>();
        fetchOrdersForSingleTable();
        fetchOrderTotal();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    private void fetchOrderTotal() {
        String url = "https://low-pressure-lists.000webhostapp.com/fetch_order_total.php";
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Please wait");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Log.d("ORDERTOTAL", String.valueOf(response));
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
                    jsonArray = jsonObject.getJSONArray("sum");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    OrderTotal orderTotal = null;
                    try {
                        orderTotal = gson.fromJson(jsonArray.get(i).toString(), OrderTotal.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    orderTotals.add(orderTotal);
                    string_total = orderTotals.get(i).getOrder_price();
                    textViewOrderTotal.setText(string_total);

                    editTextEnterCashAmount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            int total = Integer.parseInt(string_total);
                            string_cash_amount = editTextEnterCashAmount.getText().toString().trim();
                            if (string_cash_amount.length() > 0) {
                                cash_amount = Integer.valueOf(string_cash_amount);


                            }

                            refund = cash_amount - total;


                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (string_cash_amount.length() <= 0) {
                                refund = 0;
                            }
                            textViewRefund.setText(String.valueOf(refund));
                        }
                    });


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

    @OnClick(R.id.finish_order_button)
    public void onViewClicked() {
        showDialog();
        changeTableAvailability();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Finish orders for " + table_name + "?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteOrder();
                        Intent intent = new Intent(FinishOrderActivity.this, TableActivity.class);
                        startActivity(intent);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteOrder() {
        String url = "https://low-pressure-lists.000webhostapp.com/delete_orders.php";
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Please wait");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Log.d("ERROR", String.valueOf(response));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRRRRRR", String.valueOf(error));
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
        Toast.makeText(this, "Order finished!", Toast.LENGTH_SHORT).show();
    }


    private void changeTableAvailability() {
        table_availability="Yes";

        String change_url = "https://low-pressure-lists.000webhostapp.com/change_table_availability.php";
        StringRequest request = new StringRequest(Request.Method.POST, change_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    //Toast.makeText(FinishOrderActivity.this, "Item saved!", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(FinishOrderActivity.this, response, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(FinishOrderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("table_id", String.valueOf(table_id));
                params.put("table_availability", table_availability);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
