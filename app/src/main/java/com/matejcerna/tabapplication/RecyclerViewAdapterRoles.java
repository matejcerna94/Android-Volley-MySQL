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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.height = (parent.getHeight() / 2) - layoutParams.topMargin - layoutParams.bottomMargin;
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Role currentRole = rolesList.get(position);

        String roleName = currentRole.getRole_name();


        holder.textViewRoleName.setText(roleName);
        if (roleName.equals("Waiter")){
            Picasso.get().load(R.drawable.waiter_icon_role).fit().centerCrop().into(holder.imageViewRole);
        }else{
            Picasso.get().load(R.drawable.chef_icon_role).fit().centerCrop().into(holder.imageViewRole);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Role role = rolesList.get(position);
                String role_name = role.getRole_name();
                if (role_name.equals("Waiter")){
                    openWaiterLoginDialog(role_name);
                }else{
                    openChefLoginDialog(role_name);
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
        @BindView(R.id.image_view_role)
        ImageView imageViewRole;


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

    private void openWaiterLoginDialog(String role_name) {
        WaiterLoginDialog waiterLoginDialog = new WaiterLoginDialog();
        Bundle bundle = new Bundle();
        bundle.putString("role_name", role_name);
        waiterLoginDialog.setArguments(bundle);
        waiterLoginDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "tag");
    }

    private void openChefLoginDialog(String role_name) {
        ChefLoginDialog chefLoginDialog = new ChefLoginDialog();
        Bundle bundle = new Bundle();
        bundle.putString("role_name", role_name);
        chefLoginDialog.setArguments(bundle);
        chefLoginDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "tag");
    }
}
