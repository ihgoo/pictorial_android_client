package com.ihgoo.rosi.ui;

import java.util.List;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.adapter.PagerAdapter;
import com.ihgoo.rosi.bean.ImageSimpleBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.LIKESTATUS;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;

public class DetailActivity extends Activity implements OnClickListener {

	
	@ViewInject(R.id.adLayout)
	private RelativeLayout adLayout;
	
	@ViewInject(R.id.pager)
	private ViewPager mViewPager;
	
	@ViewInject(R.id.favorites)
	private TextView favorites;
	
	@ViewInject(R.id.share)
	private  TextView share;
	
	@ViewInject(R.id.chat)
	private  TextView chat;

	private UMSocialService mController;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		List<ImageSimpleBean> list = (List<ImageSimpleBean>) getIntent().getSerializableExtra("info");
		String url = getIntent().getStringExtra("url");
//		mController = initAcitonBar(this,url);
		
		
		SpotManager.getInstance(this).loadSpotAds();
		
		
		setContentView(R.layout.layout_detail);
		
		// init ad
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		ViewUtils.inject(this);
		adLayout.addView(adView);
		
		mViewPager.setAdapter(new PagerAdapter(getApplicationContext(),list,getLayoutInflater()));
		
		share.setOnClickListener(this);
		favorites.setOnClickListener(this);
		chat.setOnClickListener(this);

		
		
	}
	
	@Override
    protected void onDestroy() {
		
		if(SpotManager.getInstance(this).checkLoadComplete()){
			SpotManager.getInstance(this).unregisterSceenReceiver();
		}
            
            super.onDestroy();
    }


//	protected UMSocialService initAcitonBar(Context context,String url
//			) {
//		UMSocialService controller = UMServiceFactory
//				.getUMSocialService(url);

//		if (!controller.getEntity().mInitialized) {
//			controller.setShareContent(url);
//			controller.initEntity(context, new SocializeClientListener() {
//				@Override
//				public void onStart() {
//				}
//
//				@Override
//				public void onComplete(int status, SocializeEntity entity) {
////					matchEntity(mPair, entity);
//					favorites.setText("喜欢：  " + entity.getLikeCount());
//					chat.setText("评论:  " + entity.getCommentCount());
//					share.setText("分享:  " + entity.getShareCount());
//				}
//
//			});
//		} else {
//			
//			favorites.setText("喜欢：  " + controller.getEntity().getLikeCount());
//			chat.setText("评论:  " + controller.getEntity().getCommentCount());
//			share.setText("分享:  " + controller.getEntity().getShareCount());
//		}
//		return controller;
//	}
	
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void onClick(View v) {
//		int id = v.getId();
//	
//		switch (id) {
//		case R.id.favorites:
//			
//
//			mController.likeChange(this,
//					new SocializeClientListener() {
//						@Override
//						public void onStart() {
//						}
//
//						@Override
//						public void onComplete(int status,
//								SocializeEntity entity) {
//							if (entity != null) {
//								LIKESTATUS likestatus  = 
//										 entity.getLikeStatus();
//								
//								if (likestatus == LIKESTATUS.LIKE) {
//									Toast.makeText(getApplicationContext(), "收藏成功", 100).show();
//								}else if(likestatus == LIKESTATUS.UNLIKE){
//									Toast.makeText(getApplicationContext(), "取消收藏", 100).show();
//								}
//								
//								
//								
//							}
//						}
//					});
//
//			
//			break;
//		case R.id.share:
//			mController.openShare(this, false);
//			break;
//			
//			
//		case R.id.chat:
//			mController.openComment(this, false);
//			break;
//		}
//	}
	
}
