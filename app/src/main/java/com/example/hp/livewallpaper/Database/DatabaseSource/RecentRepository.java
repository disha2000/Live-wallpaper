package com.example.hp.livewallpaper.Database.DatabaseSource;

import com.example.hp.livewallpaper.Database.Recents;

import java.util.List;

import io.reactivex.Flowable;

public class RecentRepository implements IRecentDataSource {
   private IRecentDataSource mlocalDataSource;
   private static RecentRepository instance;

    public RecentRepository(IRecentDataSource mlocalDataSource) {
        this.mlocalDataSource = mlocalDataSource;
    }

    public static  RecentRepository getInstance(IRecentDataSource mlocalDataSource) {
      if(instance==null)
      {
          instance=new RecentRepository(mlocalDataSource);

      }
      return  instance;
    }


    public Flowable<List<Recents>> getAllRecents() {
        return mlocalDataSource.getAllRecents();
    }

    @Override
    public void insertRecents(Recents... recents) {
        mlocalDataSource.insertRecents(recents);
    }

    @Override
    public void updateRecents(Recents... recents) {
        mlocalDataSource.updateRecents(recents);

    }

    @Override
    public void DeleteRecents(Recents... recents) {
        mlocalDataSource.DeleteRecents(recents);
    }

    @Override
    public void DeleteAllRecents() {
        mlocalDataSource.DeleteAllRecents();
    }
}
