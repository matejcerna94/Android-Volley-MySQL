package com.matejcerna.tabapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyTablesRecyclerAdapter extends RecyclerView.Adapter<EmptyTablesRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Table> emptyTablesList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onCreateView(Bundle savedInstanceState);

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public EmptyTablesRecyclerAdapter(Context context, ArrayList<Table> emptyTablesList) {
        this.context = context;
        this.emptyTablesList = emptyTablesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_content_tables, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Table currentTable = emptyTablesList.get(position);

        String tableName = currentTable.getTable_name();
        String tableCapacity = currentTable.getTable_capacity();

        holder.textViewTableName.setText(tableName);
        holder.textViewTableCapacity.setText(tableCapacity);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table table = emptyTablesList.get(position);
                Intent intent = new Intent(context, PocetnaActivity.class);
                intent.putExtra("key_empty_table_position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emptyTablesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_table_name)
        TextView textViewTableName;
        @BindView(R.id.text_view_table_capacity)
        TextView textViewTableCapacity;


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