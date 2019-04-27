package com.example.hp.livewallpaper;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.hp.livewallpaper.Common.Common;
import com.example.hp.livewallpaper.Database.DatabaseSource.RecentRepository;
import com.example.hp.livewallpaper.Database.LocalDatabase.LocalDatabase;
import com.example.hp.livewallpaper.Database.LocalDatabase.RecentsDataSource;
import com.example.hp.livewallpaper.Database.Recents;
import com.example.hp.livewallpaper.Helper.SaveImageHelper;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ViewWallpaper extends AppCompatActivity {


    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton floatingActionButton;
    FloatingActionButton floatingdownload;
    ImageView imageView;
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;
    private com.squareup.picasso.Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(coordinatorLayout, "Wallpaper was set", Snackbar.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Common.CATEGORY_SELECTED);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        compositeDisposable=new CompositeDisposable();
        LocalDatabase database=LocalDatabase.getInstance(this);
        recentRepository=RecentRepository.getInstance(RecentsDataSource.getInstance(database.recentD()));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.rootbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setTitle(Common.CATEGORY_SELECTED);
        imageView = (ImageView) findViewById(R.id.imageThumb);
        Picasso.with(this).load(Common.select_background.getImageUrl())
                .into(imageView);
        addtoRecents();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabwallpaper);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext()).load(Common.select_background.getImageUrl())
                        .into(target);
            }
        });
        floatingdownload = (FloatingActionButton) findViewById(R.id.fabdownlaod);
        TooltipCompat.setTooltipText(floatingActionButton, "Set Wallpaper");

        floatingdownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(ViewWallpaper.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISSION_REQUEST_CODE);


                } else {
                    final AlertDialog dialog = new SpotsDialog.Builder()
                            .setCancelable(false)
                            .setMessage("Please waitaing...")
                            .setContext(ViewWallpaper.this)
                            .build();
                    dialog.show();




                    dialog.setMessage("Please waiting");
                    String filename = UUID.randomUUID().toString() + ".png";
                    Picasso.with(getBaseContext()).load(Common.select_background.getImageUrl()).into(new SaveImageHelper(getBaseContext(), dialog, getApplicationContext().getContentResolver(), filename, "DD Live Wallpaper Image"));
                }
                

            }

        });
        increaseViewCount();



    }

    private void increaseViewCount() {
        /*FirebaseDatabase.getInstance()
                .getReference(Common.STR_CATEGORY_BACKGROUND)
                .child(Common.select_background);*/
    }


    private void addtoRecents() {
        Disposable disposable=Observable.create(new ObservableOnSubscribe<Object> () {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Recents recents = new Recents(Common.select_background.getImageUrl(), Common.select_background.getCategoryId(), String.valueOf(System.currentTimeMillis()));
                recentRepository.insertRecents(recents);
                e.onComplete();
            }





        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                               public void accept(Object o) throws Exception {
                               }

                           }, new Consumer<Throwable>() {
                               public void accept(Throwable throwable) throws Exception {
                                   Log.e("ERROR", throwable.getMessage());
                               }
                           }, new Action() {
                               @Override
                               public void run() throws Exception {

                               }
                           }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        Picasso.with(this).cancelRequest(target);
        compositeDisposable.clear();
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Common.PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    AlertDialog dialog = new SpotsDialog.Builder()
                            .setCancelable(false)
                            .setMessage("Please wait")
                            .setContext(this)
                            .build();
                    dialog.show();
                    dialog.setMessage("Please waiting");
                    String filename = UUID.randomUUID().toString() + ".png";
                    Picasso.with(getApplicationContext()).load(String.valueOf(Common.select_background)).into(new SaveImageHelper(getBaseContext(), dialog, getApplicationContext().getContentResolver(), filename, "DD Live Wallpaper Image"));
                } else {
                    Toast.makeText(this, "You need permission to download image", Toast.LENGTH_SHORT).show();
                }
            }

        }


    }
}
