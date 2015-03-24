package com.ihgoo.rosi.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingHelper {
	private Context mContext;
	private SharedPreferences mSharedPref;
	private String method;
	private boolean prfetch;
	private boolean bigSize;
	private boolean isEncodeutf8;

	public static SettingHelper getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder {
		public static final SettingHelper INSTANCE = new SettingHelper();
	}

	public void init(Context paramContext) {
		mContext = paramContext;
		this.mSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		reload();
	}

	private void reload() {
		getMethod();
		isPrfetch();
		isEncodeutf8();
		isBigSize();
	}

	
	public String getMethod(){
		method = mSharedPref.getString("PERF_SECNETWORKMETHOD","");
		return method;
	}
	
	public boolean isPrfetch(){
		prfetch = mSharedPref.getBoolean("PERF_PREFETCH",false);
		return prfetch;
	}
	
	
	public boolean isBigSize(){
		bigSize = mSharedPref.getBoolean("PERF_BIG_OPTIMIZATION",false);
		return bigSize;
	}
	
	
	public boolean isEncodeutf8(){
		isEncodeutf8 = mSharedPref.getBoolean("PERF_ENCODEUTF8",false);
		return isEncodeutf8;
	}
	
	
	
	
	
}
