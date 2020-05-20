package com.matejcerna.tabapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterCategoryName extends RecyclerView.Adapter<RecyclerViewAdapterCategoryName.ViewHolder> {
    private Context context;
    public static ArrayList<Item> itemsList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onCreateView(Bundle savedInstanceState);

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public RecyclerViewAdapterCategoryName(Context context, ArrayList<Item> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_content_category_drinks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Item category_name = itemsList.get(position);

        final String itemId=category_name.getItem_id();
        final String itemName = category_name.getItem_name();
        final String itemImage = category_name.getItem_image();
        final String itemPrice = category_name.getItem_price();
        final String categoryName = category_name.getCategory_name();


        holder.textViewItemName.setText(itemName);
        holder.textViewItemPrice.setText(itemPrice);
        Picasso.get().load(itemImage).fit().centerCrop().into(holder.imageViewCategory);

        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(context, NarudzbaActivity.class);
                intent.putExtra("item_id", itemId);
                intent.putExtra("item_name", itemName);
                intent.putExtra("item_image", itemImage);
                intent.putExtra("item_price", itemPrice);
                intent.putExtra("item_category", categoryName);
                context.startActivity(intent);*/
               openDialog(itemId, itemName, itemImage, itemPrice);

            }
        });
    }

    private void openDialog(String item_id, String item_name, String item_image, String item_price) {
        OrderItemDialog exampleDialog = new OrderItemDialog();
        Bundle bundle = new Bundle();
        bundle.putString("item_id", item_id);
        bundle.putString("item_name", item_name);
        bundle.putString("item_image", item_image);
        bundle.putString("item_price", item_price);
        exampleDialog.setArguments(bundle);
        exampleDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "tag");
    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_item_name)
        TextView textViewItemName;

        @BindView(R.id.image_view_category)
        ImageView imageViewCategory;

        @BindView(R.id.text_view_item_price)
        TextView textViewItemPrice;

        @BindView(R.id.order_button)
        Button orderButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
