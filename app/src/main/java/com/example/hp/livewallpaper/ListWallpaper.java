package com.example.hp.livewallpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration;
import com.example.hp.livewallpaper.Common.Common;
import com.example.hp.livewallpaper.Interface.ItemClickListener;
import com.example.hp.livewallpaper.ViewHolder.ListWallpaperViewHolder;
import com.example.hp.livewallpaper.model.ModelItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ListWallpaper extends AppCompatActivity {
    Query query;
    FirebaseRecyclerOptions<ModelItem> options;
    FirebaseRecyclerAdapter<ModelItem, ListWallpaperViewHolder> adapter;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int lastPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Common.CATEGORY_SELECTED);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);


        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        loadBackgroundList();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBackgroundList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void loadBackgroundList() {

        query = FirebaseDatabase.getInstance().getReference(Common.STR_WALLPAPER).orderByChild("categoryId").equalTo(Common.CATEGORY_ID_SELECTED);
        options = new FirebaseRecyclerOptions.Builder<ModelItem>().setQuery(query, ModelItem.class).build();

        adapter = new FirebaseRecyclerAdapter<ModelItem, ListWallpaperViewHolder>(options) {

            @Override

            public ListWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.layout_wallpapaer_layout, parent, false);
                int height = parent.getMeasuredHeight() / 2;
                itemview.setMinimumHeight(height);

                return new ListWallpaperViewHolder(itemview);

            }

            @Override
            protected void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, int position, @NonNull final ModelItem model) {
                System.gc();
                Picasso.with(getBaseContext()).load(model.getImageUrl())
                        .networkPolicy(NetworkPolicy.OFFLINE).
                        into(holder.wallpaper, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(getBaseContext()).load(model.getImageUrl()).error(R.drawable.ic_terrain_black_24dp)

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
                          //  holder.itemView.viewTreeObserver.addOnGlobalLayoutListener
                            // holder.animateItem()



                        });

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent=new Intent(ListWallpaper.this,ViewWallpaper.class);
                        Common.select_background=model;
                        Common.select_background_key=adapter.getRef(position).getKey();
                        startActivity(intent);
                    }
                });

                setAnimation(holder.itemView, position);
            }



        };
        adapter.startListening();

            Context context=recyclerView.getContext();
            LayoutAnimationController controller=null;
            controller=AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(controller);

        recyclerView.scheduleLayoutAnimation();


    }

    private void setAnimation(View itemView, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {




            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_from_bottom);
            itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null)
        {
            adapter.startListening();


        }
    }

    @Override
    protected void onStop() {

        if(adapter!=null)
        {
            adapter.stopListening();


        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
        {
            adapter.startListening();


        }
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }



}
