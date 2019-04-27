package com.example.hp.livewallpaper.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.livewallpaper.Adapter.MyRecyclerAdapter;
import com.example.hp.livewallpaper.Database.DatabaseSource.RecentRepository;
import com.example.hp.livewallpaper.Database.LocalDatabase.LocalDatabase;
import com.example.hp.livewallpaper.Database.LocalDatabase.RecentsDataSource;
import com.example.hp.livewallpaper.Database.Recents;
import com.example.hp.livewallpaper.R;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@SuppressLint("ValidFragment")
public class RecentFragment extends Fragment {
    RecyclerView recyclerView;
    List<Recents> recentsList;
    MyRecyclerAdapter adapter;
    Context context;
    private static RecentFragment INSTANCE = null;
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;

    @SuppressLint("ValidFragment")
    public RecentFragment(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(context);
        recentRepository = RecentRepository.getInstance(RecentsDataSource.getInstance(database.recentD()));
        // Required empty public constructor
    }


    public static RecentFragment getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new RecentFragment(context);
        return INSTANCE;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_recent);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recentsList=new ArrayList<>();
        adapter = new MyRecyclerAdapter(context, recentsList);
        recyclerView.setAdapter(adapter);

        loadRecents();
        return view;


    }

    private void loadRecents() {
        Disposable disposable = recentRepository.getAllRecents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recents>>() {
                    @Override
                    public void accept(List<Recents> recents) throws Exception {
                        onGetAllRecentSuccess(recents);
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Errornnnnnnnnnnnn", throwable.getMessage());
                    }


                });
        compositeDisposable.add(disposable);
    }

    public void onGetAllRecentSuccess(List<Recents> recents) {
        recentsList.clear();
        recentsList.addAll(recents);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyOptionsMenu() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}