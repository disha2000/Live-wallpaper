package com.example.hp.livewallpaper.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.livewallpaper.R;


public class DailyPopularFragment extends Fragment {

    private static DailyPopularFragment INSTANCE=null;
    public DailyPopularFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static  DailyPopularFragment getInstance()
    {
        if(INSTANCE==null)
            INSTANCE=new DailyPopularFragment();
        return INSTANCE;



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_popular, container, false);
    }




}
