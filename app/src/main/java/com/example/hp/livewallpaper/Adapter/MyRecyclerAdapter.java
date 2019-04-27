package com.example.hp.livewallpaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.livewallpaper.Common.Common;
import com.example.hp.livewallpaper.Database.Recents;
import com.example.hp.livewallpaper.Interface.ItemClickListener;
import com.example.hp.livewallpaper.ListWallpaper;
import com.example.hp.livewallpaper.R;
import com.example.hp.livewallpaper.ViewHolder.ListWallpaperViewHolder;
import com.example.hp.livewallpaper.ViewWallpaper;
import com.example.hp.livewallpaper.model.ModelItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<ListWallpaperViewHolder> {
    private Context context;
    private List<Recents> recents;

    public MyRecyclerAdapter(Context context, List<Recents> recents) {
        this.context = context;
        this.recents = recents;
    }

    public ListWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_wallpapaer_layout, parent, false);
        int height = parent.getMeasuredHeight() / 2;
        itemview.setMinimumHeight(height);

        return new ListWallpaperViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, final int position) {
        Picasso.with(context).load(recents.get(position).getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE).
                into(holder.wallpaper, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(recents.get(position).getImageUrl()).error(R.drawable.ic_terrain_black_24dp)

                                .into(holder.wallpaper, new Callback() {


                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("ERROR_WALLPAPER", "Could not fetch image");
                                    }


                                });


                    }


                });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ViewWallpaper.class);
                ModelItem model = new ModelItem();
                model.setCategoryId(recents.get(position).getCategoryId());
                model.setImageUrl(recents.get(position).getImageUrl());
                Common.select_background = model;
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return 0;
    }
}
