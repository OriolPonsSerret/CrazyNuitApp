package com.example.oriolpons.projectefinalandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.local;

import java.util.ArrayList;

/**
 * Created on 05/04/2018.
 */

public class adapterLocalList extends RecyclerView.Adapter<adapterLocalList.ViewHolderLocalList> implements View.OnClickListener{

    ArrayList<local> listLocal;
    private View.OnClickListener listener;


    public adapterLocalList(ArrayList<local> listLocal) {
        this.listLocal = listLocal;
    }

    @Override
    public adapterLocalList.ViewHolderLocalList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_local_remove,null,false);

        view.setOnClickListener(this);

        return new adapterLocalList.ViewHolderLocalList(view);
    }

    @Override
    public void onBindViewHolder(adapterLocalList.ViewHolderLocalList holder, int position) {
        holder.tvTitle.setText(listLocal.get(position).getName());
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

    public class ViewHolderLocalList extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView icon;

        public ViewHolderLocalList(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            // icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}