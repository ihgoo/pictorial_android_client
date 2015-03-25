package com.ihgoo.rosi.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * volley帮助类
 * @author KelvinHu
 *
 */
public class VolleyHelper {

	private Context mContext;
	private RequestQueue mQueue;
	
	public RequestQueue getQueue() {
		return mQueue;
	}

	public static VolleyHelper getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder {
		public static final VolleyHelper INSTANCE = new VolleyHelper();
	}

	public <T> void add(Request<T> paramRequest) {
		this.mQueue.add(paramRequest);
	}

	public void init(Context paramContext) {
		this.mContext = paramContext;
		if (mQueue == null) {
			mQueue = Volley.newRequestQueue(mContext);
		}
	}

}
