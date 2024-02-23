package com.example.civiladvocacyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfficialAdapter extends RecyclerView.Adapter<OfficalViewHolder>{

    private final List<Offical> officialsList;
    private final MainActivity mainAct;

    public OfficialAdapter(List<Offical> officialsList, MainActivity mainAct) {
        this.officialsList = officialsList;
        this.mainAct = mainAct;
    }

    @NonNull
    @Override
    public OfficalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.officials_list, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new OfficalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficalViewHolder holder, int position) {

        Offical offical = officialsList.get(position);

        holder.name.setText(offical.getName());
        holder.office.setText(offical.getOffice());
        //holder.pic.setImageBitmap(offical.getPhotoUrl());
    }



    @Override
    public int getItemCount() {
        return officialsList.size();
    }
}
