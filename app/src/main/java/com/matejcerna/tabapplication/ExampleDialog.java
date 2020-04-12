package com.matejcerna.tabapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ExampleDialog extends DialogFragment {

    TextView textViewItemNameDialog;
    TextView textViewItemPriceDialog;
    ImageView imageViewItemImageDialog;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.container, null);
        textViewItemNameDialog = view.findViewById(R.id.text_view_item_name_dialog);
        textViewItemPriceDialog = view.findViewById(R.id.text_view_item_price_dialog);
        imageViewItemImageDialog = view.findViewById(R.id.image_view_item_image_dialog);

        Bundle bundle = getArguments();
        String item_id = bundle.getString("item_id");
        String item_name = bundle.getString("item_name");
        String item_image = bundle.getString("item_image");
        Log.d("image dialog", item_image);
        String item_price = bundle.getString("item_price");
        textViewItemNameDialog.setText(item_name);
        textViewItemPriceDialog.setText(item_price);
        Picasso.get().load(item_image).fit().centerCrop().into(imageViewItemImageDialog);

        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();


    }
}
