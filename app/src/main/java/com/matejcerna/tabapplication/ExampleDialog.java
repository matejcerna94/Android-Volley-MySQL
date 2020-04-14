package com.matejcerna.tabapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    Unbinder unbinder;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.my_dialog, null);
        ButterKnife.bind(this, view);


       /* textViewItemNameDialog = view.findViewById(R.id.text_view_item_name_dialog);
        textViewItemPriceDialog = view.findViewById(R.id.text_view_item_price_dialog);
        imageViewItemImageDialog = view.findViewById(R.id.image_view_item_image_dialog);*/

        Bundle bundle = getArguments();
        String item_id = bundle.getString("item_id");
        String item_name = bundle.getString("item_name");
        String item_image = bundle.getString("item_image");
        Log.d("image dialog", item_image);
        String item_price = bundle.getString("item_price");
        itemName.setText(item_name);
        itemPrice.setText(item_price);
        Picasso.get().load(item_image).fit().centerInside().into(itemImage);

        builder.setView(view);

        return builder.create();


    }





    @OnClick(R.id.image_view_close)
    public void onViewClicked() {
        dismiss();
    }
}
