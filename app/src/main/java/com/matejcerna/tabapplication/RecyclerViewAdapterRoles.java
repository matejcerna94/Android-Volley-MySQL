package com.matejcerna.tabapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterRoles extends RecyclerView.Adapter<RecyclerViewAdapterRoles.ViewHolder> {
    private Context context;
    private ArrayList<Role> rolesList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onCreateView(Bundle savedInstanceState);

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public RecyclerViewAdapterRoles(Context context, ArrayList<Role> rolesList) {
        this.context = context;
        this.rolesList = rolesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_content_roles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Role currentRole = rolesList.get(position);

        String roleName = currentRole.getRole_name();


        holder.textViewRoleName.setText(roleName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Role role = rolesList.get(position);
                String role_name = role.getRole_name();
                if (role_name.equals("Waiter")){
                    openWaiterLoginDialog();
                }else{
                    openChefLoginDialog();
                }
                /*Intent intent = new Intent(context, PocetnaActivity.class);
                intent.putExtra("key_table_position", position);
                context.startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return rolesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_role_name)
        TextView textViewRoleName;


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

    private void openWaiterLoginDialog() {
        WaiterLoginDialog waiterLoginDialog = new WaiterLoginDialog();
        waiterLoginDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "tag");
    }

    private void openChefLoginDialog() {
        ChefLoginDialog chefLoginDialog = new ChefLoginDialog();
        chefLoginDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "tag");
    }
}
