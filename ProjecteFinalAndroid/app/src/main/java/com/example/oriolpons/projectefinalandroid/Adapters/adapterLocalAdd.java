package com.example.oriolpons.projectefinalandroid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.Models.local;

import java.util.ArrayList;

/**
 * Created on 05/04/2018.
 */

public class adapterLocalAdd  extends RecyclerView.Adapter<adapterLocalAdd.ViewHolderLocalAdd> implements View.OnClickListener{

    ArrayList<local> listLocal;
    private View.OnClickListener listener;


    public adapterLocalAdd(ArrayList<local> listLocal) {
        this.listLocal = listLocal;
    }

    @Override
    public adapterLocalAdd.ViewHolderLocalAdd onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_local_add,null,false);

        view.setOnClickListener(this);

        return new adapterLocalAdd.ViewHolderLocalAdd(view);
    }

    @Override
    public void onBindViewHolder(adapterLocalAdd.ViewHolderLocalAdd holder, int position) {
        holder.tvTitle.setText(listLocal.get(position).getName());

        if (listLocal.get(position).getType().equals("restaurants")){
            holder.tvType.setText("Bar / Restaurante");
        }
        else{
            if (listLocal.get(position).getType().equals("pubs")){
                holder.tvType.setText("Pub");
            }
            else{
                if (listLocal.get(position).getType().equals("discoteques")){
                    holder.tvType.setText("Discoteca");
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return listLocal.size();
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

    public class ViewHolderLocalAdd extends RecyclerView.ViewHolder {

        TextView tvTitle, tvType;
        ImageView icon;

        public ViewHolderLocalAdd(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            // icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}