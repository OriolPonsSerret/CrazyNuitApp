package com.example.oriolpons.projectefinalandroid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.Models.user;

import java.util.ArrayList;

/**
 * Created on 02/04/2018.
 */

public class adapterUser extends RecyclerView.Adapter<adapterUser.ViewHolderUser> implements View.OnClickListener{

    ArrayList<user> listUsers;
    private View.OnClickListener listener;


    public adapterUser(ArrayList<user> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public adapterUser.ViewHolderUser onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_user,null,false);

        view.setOnClickListener(this);

        return new adapterUser.ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(adapterUser.ViewHolderUser holder, int position) {
        holder.tvTitle.setText(listUsers.get(position).getName());
        holder.txtDescription.setText(listUsers.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder {

        TextView tvTitle, txtDescription;
        ImageView icon;

        public ViewHolderUser(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            // icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}