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

    //Oluşturulan interface'den bir obje oluşturduk. Öncesinde sınıfı OnClickListener'a implements ettik
    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewMenuName = itemView.findViewById(R.id.menu_text_id);
        imageView        = itemView.findViewById(R.id.menu_image);

        //Oluşan item'ların tıklanabilir olduğunu belirttik.
        itemView.setOnClickListener(this);
    }

    //Interface'den oluşturulan objenin methodu:
    public void setOnItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        //Oluşturduğumuz interface objesini initialize ettik ve interfacede istenen özelliklerini belirledik
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
