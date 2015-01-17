package com.ihgoo.rosi.adapter;

import java.util.List;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageSimpleBean;
import com.ihgoo.rosi.net.LargeImageAsync;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import uk.co.senab.photoview.PhotoView;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

	private List<ImageSimpleBean> list;
	private LayoutInflater inflater;
	private Context mContext;

	public PagerAdapter(Context context, List<ImageSimpleBean> list,
			LayoutInflater layoutInflater) {
		this.list = list;
		this.inflater = layoutInflater;
		mContext = context;
	}

	@Override
	public int getCount() {
		return list!=null ? list.size() : 0;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
	   View imageLayout = inflater.inflate(R.layout.pro_imageshow, container,false);
       final PhotoView photoView = (PhotoView) imageLayout
               .findViewById(R.id.image);

		ImageSimpleBean bean = list.get(position);
		final String detailurl = bean.getDetailurl();

		new LargeImageAsync(){
			@Override
			public ImageBean doInBackground(String... arg0) {
				return super.doInBackground(detailurl);
			}

			@Override
			protected void onPostExecute(ImageBean result) {
				String imgurl = result.getImgurl();

				ImageLoader.getInstance().displayImage(imgurl, photoView,new LoadingListener());
			}
		}.execute();
		
		
		

		
		  ((ViewPager) container).addView(imageLayout, 0);
		
         return imageLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	
	private class LoadingListener implements ImageLoadingListener{

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
//			loading.setVisibility(View.GONE);
		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
//			loading.setVisibility(View.GONE);
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
//			loading.setVisibility(View.GONE);
		}

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
//			loading.setVisibility(View.VISIBLE);
		}
		
	}
}
