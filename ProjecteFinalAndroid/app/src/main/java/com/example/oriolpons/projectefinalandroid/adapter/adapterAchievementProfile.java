package com.example.oriolpons.projectefinalandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oriolpons.projectefinalandroid.R;
import com.example.oriolpons.projectefinalandroid.achievement;

import java.util.ArrayList;

/**
 * Created on 29/03/2018.
 */

public class adapterAchievementProfile extends RecyclerView.Adapter<adapterAchievementProfile.ViewHolderAchievement> implements View.OnClickListener{

    ArrayList<achievement> listAchievement;
    private View.OnClickListener listener;


    public adapterAchievementProfile(ArrayList<achievement> listAchievement) {
        this.listAchievement = listAchievement;
    }

    @Override
    public adapterAchievementProfile.ViewHolderAchievement onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_achievement,null,false);

        view.setOnClickListener(this);

        return new adapterAchievementProfile.ViewHolderAchievement(view);
    }

    @Override
    public void onBindViewHolder(adapterAchievementProfile.ViewHolderAchievement holder, int position) {
        holder.tvTitle.setText(listAchievement.get(position).getName());
        holder.txtDescription.setText(listAchievement.get(position).getDescription());
        holder.txtDate.setText("Se desbloqueó el " + listAchievement.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return listAchievement.size();
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

    public class ViewHolderAchievement extends RecyclerView.ViewHolder {

        TextView tvTitle, txtDescription, txtDate;
        ImageView icon;

        public ViewHolderAchievement(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            txtDate = (TextView) itemView.findViewById(R.id.tvDate);
              // icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}