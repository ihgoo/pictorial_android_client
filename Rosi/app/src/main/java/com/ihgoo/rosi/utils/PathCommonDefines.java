package com.ihgoo.rosi.utils;

import android.os.Environment;

/**
 * 与路径相关的常量
 * 
 * @author admin
 * 
 */
public class PathCommonDefines {
	// System
	public static final String APP_FOLDER_ON_SD = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/AllInOne/AllInOne";

	public static final String PHOTOCACHE_FOLDER = APP_FOLDER_ON_SD
			+ "/photo_cache";
	public static final String MY_FAVOURITE_FOLDER = APP_FOLDER_ON_SD
			+ "/my_favourite";
    public static final String FILTER_BEAN = APP_FOLDER_ON_SD
            + "/my_favourite";

    public static final String CONTACT = APP_FOLDER_ON_SD
            + "/my_contact";
    public static final String WEBVIEW_CACHE = APP_FOLDER_ON_SD
            + "/webview";


	/**
	 * 创建用户时的头像缓存
	 */
	public static final String USER_AVATAR_FOLDER = APP_FOLDER_ON_SD
			+ "/avatar";
}
