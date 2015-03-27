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
import com.ihgoo.rosi.persistence.NosqlConstant;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import cn.bmob.v3.Bmob;

/**
 * Created by ihgoo on 2015/3/25.
 */
public class SplashActivity extends Activity {

//    public String isfirst = SettingsV2.get("isfirst", "0");


    boolean isFirst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        try {
            DB snappydb;
            snappydb = DBFactory.open(this);
            isFirst = snappydb.getBoolean(NosqlConstant.IS_FIRST);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        Bmob.initialize(this,"dfe0d0aec29e19cfc5f6c6986416527a");


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        ImageView mView = new ImageView(this);
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


                    if (!isFirst){

                        Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.overridePendingTransition(R.anim.zoomin,
                                R.anim.zoomout);
                        SplashActivity.this.overridePendingTransition(
                                android.R.anim.fade_in, android.R.anim.fade_out);
                        SplashActivity.this.finish();


                    }else{

                        Intent mainIntent = new Intent(SplashActivity.this,MainPageActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.overridePendingTransition(R.anim.zoomin,
                                R.anim.zoomout);
                        SplashActivity.this.overridePendingTransition(
                                android.R.anim.fade_in, android.R.anim.fade_out);
                        SplashActivity.this.finish();

                    }




            }
        }, 3000); // 3000 for release
    }





}
