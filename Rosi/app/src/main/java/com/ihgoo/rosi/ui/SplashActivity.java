package com.ihgoo.rosi.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.ihgoo.rosi.R;

/**
 * Created by ihgoo on 2015/3/25.
 */
public class SplashActivity extends Activity {

//    public String isfirst = SettingsV2.get("isfirst", "0");
    public Intent mainIntent;
    private ImageView mView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        mView = new ImageView(this);
        mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mView.setScaleType(ImageView.ScaleType.FIT_XY);
        mView.setImageResource(R.drawable.bg_splash);
        setContentView(mView);
        AnimatorSet anim = new AnimatorSet();
        anim.playTogether(ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1f),
                ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f));
        anim.setDuration(3000);
        anim.start();



        new Handler().postDelayed(new Runnable() {
            public void run() {
                mainIntent = new Intent(SplashActivity.this,MainPageActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.overridePendingTransition(R.anim.zoomin,
                        R.anim.zoomout);
                SplashActivity.this.overridePendingTransition(
                        android.R.anim.fade_in, android.R.anim.fade_out);
                SplashActivity.this.finish();
            }
        }, 3000); // 3000 for release
    }





}
