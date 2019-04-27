package com.example.hp.livewallpaper.Database.LocalDatabase;

import com.example.hp.livewallpaper.Database.DatabaseSource.IRecentDataSource;
import com.example.hp.livewallpaper.Database.Recents;

import java.util.List;

import io.reactivex.Flowable;

public class RecentsDataSource implements IRecentDataSource {
    private RecentD recentD;
    private static RecentsDataSource instance;
    public RecentsDataSource(RecentD recentD)
    {
        this.recentD=recentD;

    }



    public static RecentsDataSource getInstance(RecentD recentD)
    {
        if(instance==null)
        {
            instance=new RecentsDataSource(recentD);

        }
        return instance;

    }
    public Flowable<List<Recents>> getAllRecents() {

        return recentD.getAllRecents();
    }

    @Override
    public void insertRecents(Recents... recents) {
        recentD.insertRecents(recents);

    }

    @Override
    public void updateRecents(Recents... recents) {
        recentD.updateRecents(recents);

    }

    @Override
    public void DeleteRecents(Recents... recents) {

        recentD.DeleteRecents(recents);
    }

    @Override
    public void DeleteAllRecents() {

        recentD.DeleteAllRecents();
    }
}
