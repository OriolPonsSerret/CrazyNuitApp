package com.example.oriolpons.projectefinalandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.routes;

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
        holder.tvTitle.setText(listRoutes.get(position).getName());
        holder.txtDescription.setText(listRoutes.get(position).getDescription());
        holder.txtCreator.setText(listRoutes.get(position).getCreator());
        holder.txtAssessment.setText(listRoutes.get(position).getAssessment() + "/5 - 1 votos");
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

        TextView tvTitle, txtDescription, txtCreator, txtAssessment;
        ImageView icon;

        public ViewHolderRoutes(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            txtCreator = (TextView) itemView.findViewById(R.id.tvCreator);
            txtAssessment = (TextView) itemView.findViewById(R.id.tvAssessment);
           // icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
