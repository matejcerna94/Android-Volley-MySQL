package com.matejcerna.tabapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WaiterLoginDialog extends DialogFragment {

    ArrayList<Staff> staffList;
    @BindView(R.id.image_view_close)
    ImageView imageViewClose;
    @BindView(R.id.edit_text_waiter_username)
    EditText editTextWaiterUsername;
    @BindView(R.id.edit_text_waiter_password)
    EditText editTextWaiterPassword;
    String role_name;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alert = builder.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.waiter_login_dialog, null);
        ButterKnife.bind(this, view);

        staffList = new ArrayList<>();

        Bundle bundle = getArguments();
        role_name = bundle.getString("role_name");
        Log.d("WAITER LOGIN ROLE", role_name);


        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        alert.setView(view);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alert;


    }

    private void login(final String staff_name, final String staff_password) {
        String url = "https://low-pressure-lists.000webhostapp.com/login.php";
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), null, "Please wait");
        final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Log.d("RESPONSE", response);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(String.valueOf(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String success = null;
                    try {
                        success = jsonObject.getString("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (success.equals("1")){
                        Toast.makeText(getContext(), "Login successfull!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), TableActivity.class);
                        intent.putExtra("waiter_name", staff_name);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show();
                    }
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
                    jsonArray = jsonObject.getJSONArray("login");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    Staff staff = null;
                    try {
                        staff = gson.fromJson(jsonArray.get(i).toString(), Staff.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    staffList.add(staff);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
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

                params.put("staff_name", String.valueOf(staff_name));
                params.put("staff_password", String.valueOf(staff_password));
                params.put("role_name", String.valueOf(role_name));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }



    @OnClick(R.id.login_button_waiter_dialog)
    public void onViewClicked() {
        String staff_name = editTextWaiterUsername.getText().toString();
        String staff_password = editTextWaiterPassword.getText().toString();
        login(staff_name, staff_password);
    }
}
