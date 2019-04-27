package com.example.hp.livewallpaper.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hp.livewallpaper.Fragment.CatagoryFragement;
import com.example.hp.livewallpaper.Fragment.DailyPopularFragment;
import com.example.hp.livewallpaper.Fragment.RecentFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private Context context;

    public MyFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return CatagoryFragement.getInstance();
        else if (position == 1) {
            return DailyPopularFragment.getInstance();
        }
        else if(position==2)
        {
            return RecentFragment.getInstance(context);
        }
        else
        {
            return null;
        }
    }



    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "category";

            case 1:
                return "Daily Popular";

            case 2:
                return "Recents";

        }
        return "";
    }
}

