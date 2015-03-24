package com.ihgoo.rosi.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by ihgoo on 2015/3/24.
 */
public class RosiImageView extends ImageView {
    public RosiImageView(Context context) {
        super(context);
    }

    public RosiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RosiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUrl(String url ){
        ImageLoader.getInstance().displayImage(url,this);
    }

}
