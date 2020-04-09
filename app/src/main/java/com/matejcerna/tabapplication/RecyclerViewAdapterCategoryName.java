package com.matejcerna.tabapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    public static ArrayList<Category_name> itemsList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onCreateView(Bundle savedInstanceState);

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public RecyclerViewAdapterCategoryName(Context context, ArrayList<Category_name> itemsList) {
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
        final Category_name category_name = itemsList.get(position);

        final String itemId=category_name.getItem_id();
        final String itemName = category_name.getItem_name();
        final String itemImage = category_name.getItem_image();
        final String categoryName = category_name.getCategory_name();


        holder.textViewItemName.setText(itemName);
        Picasso.get().load(itemImage).fit().centerCrop().into(holder.imageViewCategory);

        holder.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NarudzbaActivity.class);
                intent.putExtra("item_id", itemId);
                intent.putExtra("item_name", itemName);
                intent.putExtra("item_image", itemImage);
                intent.putExtra("item_category", categoryName);
                context.startActivity(intent);
            }
        });
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
