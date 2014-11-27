package com.ihgoo.rosi.net;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.VolleyError;
import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageListBean;
import com.ihgoo.rosi.parser.ParserImageList;

public class ImageListLoader extends AsyncTaskLoader<ImageListBean> {

	private Object mLocked;
	private String mRet;
	private Handler mHandler;
	private String mUrl;
	private String mPage;
	
	public ImageListLoader(Context context,Handler handler,String url,String page) {
		super(context);
		mHandler = handler;
		mLocked = this;
		mUrl = url;
		mPage = page;
	}

	
	private void fetchImageList(){
		Message message = Message.obtain();
		message.what = 1; //正在获取数据
		mHandler.sendMessage(message);
		StringRequestProxy requestProxy = new StringRequestProxy(mUrl+mPage, new Listener(), new ErrorListener());
		VolleyHelper.getInstance().add(requestProxy);
	}
	
	
	@Override
	public ImageListBean loadInBackground() {
		
		fetchImageList();
		
		synchronized (mLocked) {
			try {
				mLocked.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Message message = Message.obtain();
		message.what = 2; // 解析数据
		mHandler.sendMessage(message);
		
		return ParserImageList.parser(mRet);
	}
	
	
	private class Listener implements com.android.volley.Response.Listener<String>{

		@Override
		public void onResponse(String ret) {
			if (ret!=null) {
				mRet = ret;
			}
			
			synchronized (mLocked) {
				mLocked.notify();
			}
		}
		
	}
	
	private class ErrorListener implements com.android.volley.Response.ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			System.out.println("error");
			synchronized (mLocked) {
				mLocked.notify();
			}
		}
		
	}

}
