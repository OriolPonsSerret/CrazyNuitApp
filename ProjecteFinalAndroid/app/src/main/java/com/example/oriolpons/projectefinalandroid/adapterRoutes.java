package com.example.oriolpons.projectefinalandroid;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created on 26/03/2018.
 */

public class adapterRoutes extends RecyclerView.Adapter<adapterRoutes.ViewHolderRoutes> implements View.OnClickListener{

    ArrayList<routes> listRoutes;
    private View.OnClickListener listener;


    public adapterRoutes(ArrayList<routes> listRoutes) {
        this.listRoutes = listRoutes;
    }

    @Override
    public ViewHolderRoutes onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_routes,null,false);

        view.setOnClickListener(this);

        return new ViewHolderRoutes(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderRoutes holder, int position) {
        holder.txtRouteName.setText(listRoutes.get(position).getName());
        holder.txtRouteDescription.setText(listRoutes.get(position).getDescription());
        holder.txtRouteCreator.setText(listRoutes.get(position).getCreator());
    }

    @Override
    public int getItemCount() {
        return listRoutes.size();
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

    public class ViewHolderRoutes extends RecyclerView.ViewHolder {

        TextView txtRouteName, txtRouteDescription, txtRouteCreator;
        ImageView imagen;

        public ViewHolderRoutes(View itemView) {
            super(itemView);
            txtRouteName = (TextView) itemView.findViewById(R.id.tvRouteName);
            txtRouteDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            txtRouteCreator = (TextView) itemView.findViewById(R.id.tvCreator);
           // imagen = (ImageView) itemView.findViewById(R.id.ivImagen);
        }
    }
}
