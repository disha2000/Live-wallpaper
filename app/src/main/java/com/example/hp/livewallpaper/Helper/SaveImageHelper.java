package com.example.hp.livewallpaper.Helper;

import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class SaveImageHelper implements Target {
    private Context context;
    private WeakReference<AlertDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name,desc;

    public SaveImageHelper(Context context, AlertDialog alertDialog, ContentResolver contentResolver, String name, String desc) {
        this.context = context;
        this.alertDialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver r=contentResolverWeakReference.get();
        final AlertDialog alertDialog=alertDialogWeakReference.get();
        if(r!=null)
            MediaStore.Images.Media.insertImage(r,bitmap,name,desc);
        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                Looper.prepare();
                alertDialog.dismiss();

                timer2.cancel(); //this will cancel the timer of the system

            }
        }, 3000);
        Toast.makeText(context,"Download Succeed",Toast.LENGTH_SHORT).show();




    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
