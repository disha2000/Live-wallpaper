package com.example.hp.livewallpaper.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hp.livewallpaper.R;

import com.example.hp.livewallpaper.Interface.ItemClickListener;

public class CatagoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView category_name;
    public ImageView backgeround_image;
    ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }
    public CatagoryViewHolder(View itemView) {
        super(itemView);
        backgeround_image=(ImageView) itemView.findViewById(R.id.image);
        category_name=(TextView) itemView.findViewById(R.id.name);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());

    }
}
