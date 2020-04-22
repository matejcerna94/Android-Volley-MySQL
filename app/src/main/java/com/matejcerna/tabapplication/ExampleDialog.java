package com.matejcerna.tabapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ExampleDialog extends DialogFragment {


    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_image)
    ImageView itemImage;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.edit_text_note)
    EditText editTextNote;
    @BindView(R.id.text_view_amount)
    TextView textViewAmount;
    @BindView(R.id.minus_button)
    Button minusButton;
    @BindView(R.id.plus_button)
    Button plusButton;

    int amount = 1;

    int table_id;
    String item_name;
    String string_item_id;
    String item_image;
    String string_item_price;
    String date_and_time;
    String note = "";
    String table_availability="";
    int single_item_price;
    int order_price;
    String table_name;
    @BindView(R.id.text_view_table_name_dialog)
    TextView textViewTableNameDialog;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alert = builder.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.my_dialog, null);
        ButterKnife.bind(this, view);


        Bundle bundle = getArguments();
        string_item_id = bundle.getString("item_id");
        item_name = bundle.getString("item_name");
        item_image = bundle.getString("item_image");
        string_item_price = bundle.getString("item_price");

        table_id = PocetnaActivity.table_id;
        table_name = PocetnaActivity.table_name;

        textViewTableNameDialog.setText(table_name);
        itemName.setText(item_name);
        single_item_price = Integer.parseInt(string_item_price);
        order_price = single_item_price * amount;
        itemPrice.setText(String.valueOf(order_price));
        Picasso.get().load(item_image).fit().centerInside().into(itemImage);



        Log.d("kod table id dialog", String.valueOf(table_id));
        Log.d("kod item id dialog", string_item_id);
        Log.d("kod tablename dialog", table_name);


        minusButton.setEnabled(false);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount == 1) {
                    minusButton.setEnabled(false);
                } else {
                    amount--;
                    textViewAmount.setText(String.valueOf(amount));
                    single_item_price = Integer.parseInt(string_item_price);
                    order_price = single_item_price * amount;
                    itemPrice.setText(String.valueOf(order_price));
                }

            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusButton.setEnabled(true);
                amount++;
                textViewAmount.setText(String.valueOf(amount));
                single_item_price = Integer.parseInt(string_item_price);
                order_price = single_item_price * amount;
                itemPrice.setText(String.valueOf(order_price));
            }
        });


        alert.setView(view);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alert;


    }


    @OnClick(R.id.image_view_close)
    public void onViewClicked() {
        dismiss();
    }


    @OnClick(R.id.cancel_button)
    public void onCancelButtonClicked() {
        dismiss();
    }

    @OnClick(R.id.order_button_dialog)
    public void onOrderButtonClicked() {
        saveOrder();
        changeTableAvailability();
    }

    private void saveOrder() {
        Log.d("POJEDINACNA CIJENA", String.valueOf(single_item_price));
        note = editTextNote.getText().toString();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        date_and_time = simpleDateFormat.format(calendar.getTime());
        // formattedDate have current date/time
        Toast.makeText(getContext(), date_and_time, Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving");

        progressDialog.show();
        String INSERT_URL = "https://low-pressure-lists.000webhostapp.com/save_order.php";
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(getContext(), "Item saved!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("item_name", item_name);
                params.put("item_image", item_image);
                params.put("amount", String.valueOf(amount));
                params.put("single_item_price", String.valueOf(single_item_price));
                params.put("order_price", String.valueOf(order_price));
                params.put("note", note);
                params.put("order_date_and_time", date_and_time);
                params.put("item_id", string_item_id);
                params.put("table_id", String.valueOf(table_id));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    private void changeTableAvailability() {
        table_availability="No";

        String change_url = "https://low-pressure-lists.000webhostapp.com/change_table_availability.php";
        StringRequest request = new StringRequest(Request.Method.POST, change_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(getContext(), "Item saved!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

}
