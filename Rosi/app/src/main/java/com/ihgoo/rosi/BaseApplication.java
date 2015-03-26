package com.ihgoo.rosi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.ref.WeakReference;

/**
 * Created by ihgoo on 2015/1/19.
 */
public class BaseApplication extends Application {

    static Context instance;

    @Override
    public void onCreate() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new FadeInBitmapDisplayer(500))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY) //default
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
        instance = getApplicationContext();
        super.onCreate();
    }

    public static WeakReference<Activity> instanceRef;

    public static synchronized Context getInstance() {
        if (instanceRef == null || instanceRef.get() == null) {
            return BaseApplication.getContext();
        } else {
            return instanceRef.get();
        }
    }

    public static synchronized Activity getActivity() {
        Activity result = null;
        if (instanceRef != null && instanceRef.get() != null) {
            result = instanceRef.get();
        }
        return result;
    }

    public static synchronized Context getContext() {
        return instance;
    }

    public static SparseArray<WeakReference<Activity>> taskStack = new SparseArray<WeakReference<Activity>>();

    public static synchronized SparseArray<WeakReference<Activity>> getTaskStack() {
        return taskStack;
    }



}
