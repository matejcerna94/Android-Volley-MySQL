package com.matejcerna.tabapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterCategoryName extends RecyclerView.Adapter<RecyclerViewAdapterCategoryName.ViewHolder> {
    private Context context;
    private ArrayList<Category_name> category_nameList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onCreateView(Bundle savedInstanceState);

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public RecyclerViewAdapterCategoryName(Context context, ArrayList<Category_name> categoryNameList) {
        this.context = context;
        this.category_nameList = categoryNameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_content_category_drinks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Category_name category_name = category_nameList.get(position);

        String itemName = category_name.getItem_name();
        String categoryName = category_name.getCategory_name();

        holder.textViewItemName.setText(itemName);
        holder.textViewCategoryName.setText(categoryName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Category_name categoryName = category_nameList.get(position);
                Intent intent = new Intent(context, PocetnaActivity.class);
                intent.putExtra("key", position);
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return category_nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_item_name)
        TextView textViewItemName;
        @BindView(R.id.text_view_category_name)
        TextView textViewCategoryName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
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
