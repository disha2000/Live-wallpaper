package com.example.hp.livewallpaper.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;


import com.example.hp.livewallpaper.Common.Common;
import com.example.hp.livewallpaper.Interface.ItemClickListener;
import com.example.hp.livewallpaper.ListWallpaper;
import com.example.hp.livewallpaper.R;
import com.example.hp.livewallpaper.ViewHolder.CatagoryViewHolder;
import com.example.hp.livewallpaper.model.CatagoryItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import static android.support.v7.widget.GridLayoutManager.*;


public class CatagoryFragement extends Fragment {

    FirebaseDatabase database;
    DatabaseReference categoryBackground;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<CatagoryItem> options;
    FirebaseRecyclerAdapter<CatagoryItem, CatagoryViewHolder> adapter;

    private static CatagoryFragement INSTANCE = null;

    public CatagoryFragement() {
        database = FirebaseDatabase.getInstance();
        categoryBackground = database.getReference(Common.STR_CATEGORY_BACKGROUND);
        options = new FirebaseRecyclerOptions.Builder<CatagoryItem>().setQuery(categoryBackground, CatagoryItem.class).build();
        adapter = new FirebaseRecyclerAdapter<CatagoryItem, CatagoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final CatagoryViewHolder holder, int position, final CatagoryItem model) {
                System.gc();
                Picasso.with(getActivity()).load(model.imagelink).networkPolicy(NetworkPolicy.OFFLINE).into(holder.backgeround_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getActivity()).load(model.getImagelink()).error(R.drawable.ic_terrain_black_24dp).into(holder.backgeround_image, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Log.e("Error ", "Could't fetch the image");

                            }
                        });
                    }

                });
                holder.category_name.setText(model.getName());
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position)
                    {
                        Common.CATEGORY_ID_SELECTED=adapter.getRef(position).getKey();
                        Common.CATEGORY_SELECTED=model.getName();
                        Intent intent=new Intent(getActivity(),ListWallpaper.class);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CatagoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_catagory, parent, false);
                recyclerView.setHasFixedSize(true);
                return new CatagoryViewHolder(itemview);
            }
        };
    }


    public static CatagoryFragement getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CatagoryFragement();
        return INSTANCE;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catagory_fragement, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(grid);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));
        setcategory();
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setcategory() {
        adapter.startListening();
      //  setAnimation();
        recyclerView.setAdapter(adapter);
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;
        controller=AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(controller);
        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }



    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
        {
            adapter.startListening();


        }
    }
}
