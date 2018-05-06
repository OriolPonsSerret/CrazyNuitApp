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
 * Created on 30/03/2018.
 */

public class adapterLocal extends RecyclerView.Adapter<adapterLocal.ViewHolderLocal> implements View.OnClickListener{

    ArrayList<local> listLocal;
    private View.OnClickListener listener;


    public adapterLocal(ArrayList<local> listLocal) {
        this.listLocal = listLocal;
    }

    @Override
    public adapterLocal.ViewHolderLocal onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_routes_profile,null,false);

        view.setOnClickListener(this);

        return new adapterLocal.ViewHolderLocal(view);
    }

    @Override
    public void onBindViewHolder(adapterLocal.ViewHolderLocal holder, int position) {
        holder.tvTitle.setText(listLocal.get(position).getName());
        holder.txtDescription.setText(listLocal.get(position).getDescription());
        holder.txtAssessment.setText(listLocal.get(position).getAssessment() + "/5");

        if(listLocal.get(position).getType().charAt(0) == 'r'){
            holder.icon.setImageResource(R.drawable.restaurant_icon);
        }
        else{
            if(listLocal.get(position).getType().charAt(0) == 'p'){
                holder.icon.setImageResource(R.drawable.pub_icon);
            }
            else{
                if(listLocal.get(position).getType().charAt(0) == 'd'){
                    holder.icon.setImageResource(R.drawable.disco_icon);
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

    public class ViewHolderLocal extends RecyclerView.ViewHolder {

        TextView tvTitle, txtDescription, txtCreator, txtAssessment;
        ImageView icon;

        public ViewHolderLocal(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            txtCreator = (TextView) itemView.findViewById(R.id.tvCreator);
            txtAssessment = (TextView) itemView.findViewById(R.id.tvAssessment);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
