package com.matejcerna.tabapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterOrders extends RecyclerView.Adapter<RecyclerViewAdapterOrders.ViewHolder> {
    private Context context;
    public static ArrayList<Order> ordersList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onCreateView(Bundle savedInstanceState);

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public RecyclerViewAdapterOrders(Context context, ArrayList<Order> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_content_orders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Order order = ordersList.get(position);

        final String order_id = order.getOrder_id();
        final String item_name = order.getItem_name();
        final String amount = order.getAmount();
        final String single_item_price = order.getSingle_item_price();
        final String order_price = order.getOrder_price();
        final String order_note = order.getNote();
        final String order_date_and_time = order.getOrder_date_and_time();
        final String order_image = order.getItem_image();


        holder.textViewOrderId.setText(order_id);
        holder.textViewOrderItemName.setText(item_name);
        holder.textViewOrderAmount.setText(amount);
        holder.textViewOrderSingleItemPrice.setText(single_item_price);
        holder.textViewOrderPrice.setText(order_price);
        holder.textViewOrderNote.setText(order_note);
        holder.textViewOrderDateAndTime.setText(order_date_and_time);
        Picasso.get().load(order_image).fit().centerCrop().into(holder.imageViewOrderItem);

    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view_order_item)
        ImageView imageViewOrderItem;
        @BindView(R.id.text_view_order_id)
        TextView textViewOrderId;
        @BindView(R.id.text_view_order_item_name)
        TextView textViewOrderItemName;
        @BindView(R.id.text_view_order_amount)
        TextView textViewOrderAmount;
        @BindView(R.id.text_view_order_single_item_price)
        TextView textViewOrderSingleItemPrice;
        @BindView(R.id.text_view_order_price)
        TextView textViewOrderPrice;
        @BindView(R.id.text_view_order_note)
        TextView textViewOrderNote;
        @BindView(R.id.text_view_order_date_and_time)
        TextView textViewOrderDateAndTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            /*orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });*/
        }
    }
}
