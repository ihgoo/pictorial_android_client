package com.ihgoo.rosi.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.ihgoo.rosi.R;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class ImageLoaderHelper {

	private ImageLoaderHelper loaderHelper;
	private Context mContext;
	private DisplayImageOptions defaultOptions;
	
	
	public DisplayImageOptions getDefaultOptions() {
		return defaultOptions;
	}

	public void init(Context paramContext) {
		this.mContext = paramContext;
//		if (ImageLoader.getInstance().isInited()) {
			defaultOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.load_default)
				.showImageOnFail(R.drawable.load_default)
				.showStubImage(R.drawable.load_default)
			    .cacheOnDisc(true)//图片存本地
			    .cacheInMemory(true)
			    .displayer(new FadeInBitmapDisplayer(500))
			    .bitmapConfig(Bitmap.Config.RGB_565)
			    .imageScaleType(ImageScaleType.EXACTLY) // default
			    .build();
			ImageLoaderConfiguration config =new ImageLoaderConfiguration
				.Builder(mContext)
				.memoryCache(new UsingFreqLimitedMemoryCache(16 * 1024 * 1024))
				.defaultDisplayImageOptions(defaultOptions).build();
			ImageLoader.getInstance().init(config);
//		}
	}
	
	public static ImageLoaderHelper getInstance() {
		return SingletonHolder.INSTANCE;
	}
	

	private static class SingletonHolder {
		public static final ImageLoaderHelper INSTANCE = new ImageLoaderHelper();
	}
}
