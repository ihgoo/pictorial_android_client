package com.ihgoo.rosi.ui;

import android.app.Activity;
import android.content.Intent;

import com.ihgoo.rosi.R;

public class Theme {
	private static  boolean isdark;


	public final static int THEME_WHITE = 0;
	public final static int THEME_DARK = 1;

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity
	 * of the same type.
	 */
	public static void changeToTheme(Activity activity, boolean theme) {
		isdark = theme;
		activity.finish();

		activity.startActivity(new Intent(activity, activity.getClass()));
	}

	/** Set the theme of the activity, according to the configuration. */
	public static void onActivityCreateSetTheme(Activity activity) {
		if(isdark){
			activity.setTheme(R.style.Theme_Mystyledark);
		}else{
			activity.setTheme(R.style.Theme_Mystyle);
		}
		
		
	}

}
