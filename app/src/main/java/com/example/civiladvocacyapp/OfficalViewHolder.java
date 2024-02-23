package com.example.civiladvocacyapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfficalViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    TextView office;
    ImageView pic;


    public OfficalViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.offical_name);
        office = itemView.findViewById(R.id.official_title);
        pic = itemView.findViewById(R.id.official_pic);

    }
}
