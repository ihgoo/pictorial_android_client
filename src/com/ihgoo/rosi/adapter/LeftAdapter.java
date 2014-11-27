package com.ihgoo.rosi.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihgoo.rosi.R;

public class LeftAdapter extends ArrayAdapter<String>{

	private Context mContext;
	private int mItemRsc;
	private LayoutInflater mInflater;
	private int color;
	private Drawable mLocalDrawable;
	
	public LeftAdapter(Context context, int resource, String[] arrayString) {
		super(context, resource, arrayString);
		this.mContext = context;
	    this.mItemRsc = resource;
	    this.mInflater = LayoutInflater.from(context);
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String str = getItem(position);
		if (convertView==null) {
			convertView = View.inflate(mContext,mItemRsc, null);
		}
		
		TextView tv_title =  (TextView) convertView.findViewById(R.id.tv_title);
		ImageView iv_color_block = (ImageView) convertView.findViewById(R.id.iv_color_block);
		
		
		
		if (str.equals("Rosi写真")) {
			color = mContext.getResources().getColor(R.color.darkpurple);
		}else if (str.equals("秀人写真")) {
			color = mContext.getResources().getColor(R.color.darkgreen);
		}else if (str.equals("切换主题")) {
			color = mContext.getResources().getColor(R.color.darkorange);
		}else if (str.equals("第四印象")) {
			color = mContext.getResources().getColor(R.color.darkred);
		}else if (str.equals("Rosi视频")) {
			color = mContext.getResources().getColor(R.color.darkblue);
		}else if (str.equals("秀人视频")) {
			color = mContext.getResources().getColor(R.color.purple);
		}else if (str.equals("设置")) {
			color = mContext.getResources().getColor(R.color.blue);
		}else if(str.contains("我的收藏")){
			color = mContext.getResources().getColor(R.color.orange);
		}else if(str.contains("随便看看")){
			color = mContext.getResources().getColor(R.color.darkblue);
		}
		
		
		iv_color_block.setBackgroundColor(color);
		tv_title.setText(str);
//		tv_title.setTextColor(mContext.getResources().getColor(R.color.black));
		
		return convertView;
	}
	
	
}
