package com.matejcerna.tabapplication;

import android.app.AlertDialog;
import android.app.Dialog;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChefLoginDialog extends DialogFragment {

    String role_name;
    @BindView(R.id.image_view_close)
    ImageView imageViewClose;
    @BindView(R.id.edit_text_waiter_username)
    EditText editTextWaiterUsername;
    @BindView(R.id.edit_text_waiter_password)
    EditText editTextWaiterPassword;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alert = builder.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.chef_login_dialog, null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        role_name = bundle.getString("role_name");
        Log.d("CHEF LOGIN ROLE", role_name);

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




}
