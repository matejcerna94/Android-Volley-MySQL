package com.matejcerna.tabapplication;

import android.app.Dialog;
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

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    int amount = 1;
    @BindView(R.id.minus_button)
    Button minusButton;
    @BindView(R.id.plus_button)
    Button plusButton;
    int table_id;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alert = builder.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.my_dialog, null);
        ButterKnife.bind(this, view);


        Bundle bundle = getArguments();
        String item_id = bundle.getString("item_id");
        String item_name = bundle.getString("item_name");
        String item_image = bundle.getString("item_image");
        String item_price = bundle.getString("item_price");
        itemName.setText(item_name);
        itemPrice.setText(item_price);
        Picasso.get().load(item_image).fit().centerInside().into(itemImage);
        table_id = PocetnaActivity.table_id;
        Log.d("kod table id dialog", String.valueOf(table_id));
        Log.d("kod item id dialog", String.valueOf(item_id));

        minusButton.setEnabled(false);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount==1){
                    minusButton.setEnabled(false);
                }else{
                    amount--;
                    textViewAmount.setText(String.valueOf(amount));
                }

            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusButton.setEnabled(true);
                amount++;
                textViewAmount.setText(String.valueOf(amount));
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String date_and_time = simpleDateFormat.format(calendar.getTime());
        // formattedDate have current date/time
        Toast.makeText(getContext(), date_and_time, Toast.LENGTH_SHORT).show();
    }


}
