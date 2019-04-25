package com.atakanakar.orderfoodapplication.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atakanakar.orderfoodapplication.Interfaces.ItemClickListener;
import com.atakanakar.orderfoodapplication.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textViewMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewMenuName = itemView.findViewById(R.id.menu_text_id);
        imageView        = itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setOnItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
