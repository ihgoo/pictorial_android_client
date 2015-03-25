package com.ihgoo.rosi.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageSimpleBean;
import com.ihgoo.rosi.net.LargeImageAsync;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

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

				ImageLoader.getInstance().displayImage(imgurl, photoView);
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
	
	
}
