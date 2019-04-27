package com.example.hp.livewallpaper.Database.DatabaseSource;

import com.example.hp.livewallpaper.Database.Recents;

import java.util.List;

import io.reactivex.Flowable;

public interface IRecentDataSource {
    Flowable<List<Recents>> getAllRecents();
    void insertRecents(Recents... recents);
    void updateRecents(Recents... recents);
    void DeleteRecents(Recents... recents);
    void DeleteAllRecents();

}
