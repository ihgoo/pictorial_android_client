package com.ihgoo.rosi.utils;

import android.content.Context;

/**
 * Created by ihgoo on 2015/3/26.
 */
public class Misc {
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
