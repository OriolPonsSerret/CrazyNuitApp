package com.example.oriolpons.projectefinalandroid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.Models.routes;

import java.util.ArrayList;

/**
 * Created on 29/03/2018.
 */

public class adapterRoutesProfile extends RecyclerView.Adapter<adapterRoutesProfile.ViewHolderRoutesProfile> implements View.OnClickListener{

    ArrayList<routes> listRoutes;
    private View.OnClickListener listener;


    public adapterRoutesProfile(ArrayList<routes> listRoutes) {
        this.listRoutes = listRoutes;
    }

    @Override
    public adapterRoutesProfile.ViewHolderRoutesProfile onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_routes_profile,null,false);

        view.setOnClickListener(this);

        return new adapterRoutesProfile.ViewHolderRoutesProfile(view);
    }

    @Override
    public void onBindViewHolder(adapterRoutesProfile.ViewHolderRoutesProfile holder, int position) {
        holder.txtTitle.setText(listRoutes.get(position).getName());
        holder.txtDescription.setText(listRoutes.get(position).getDescription());
        holder.txtAssessment.setText(listRoutes.get(position).getAssessment() + "/5");
        holder.txtCity.setText(listRoutes.get(position).getCity());

        if(listRoutes.get(position).getMeasure().equals("short")){
            holder.icon.setImageResource(R.drawable.short_icon);
        }
        else{
            if(listRoutes.get(position).getMeasure().equals("halfways")){
                holder.icon.setImageResource(R.drawable.halfways_icon);
            }
            else{
                if(listRoutes.get(position).getMeasure().equals("long")){
                    holder.icon.setImageResource(R.drawable.long_icon);
                }

            }
        }
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

    public class ViewHolderRoutesProfile extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDescription, txtAssessment, txtCity;
        ImageView icon;

        public ViewHolderRoutesProfile(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            txtAssessment = (TextView) itemView.findViewById(R.id.tvAssessment);
            txtCity = (TextView) itemView.findViewById(R.id.tvCity);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}