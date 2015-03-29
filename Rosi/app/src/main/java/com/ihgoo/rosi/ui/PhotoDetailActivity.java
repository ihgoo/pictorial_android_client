package com.ihgoo.rosi.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.adapter.PagerAdapter;
import com.ihgoo.rosi.bean.ImageSimpleBean;
import com.ihgoo.rosi.utils.LogUtils;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 图片详情页面
 */
public class PhotoDetailActivity extends Activity implements ViewPager.OnPageChangeListener {
	
	@InjectView(R.id.pager)
	ViewPager mViewPager;

	@InjectView(R.id.favorites)
    ImageView favorites;

	@InjectView(R.id.share)
    ImageView share;

    @InjectView(R.id.page)
    TextView page;
	

    private int mIndex;

    List<ImageSimpleBean> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        list = (List<ImageSimpleBean>) getIntent().getSerializableExtra("info");
		String url = getIntent().getStringExtra("url");
        mIndex = getIntent().getIntExtra("position",0);
		setContentView(R.layout.layout_detail);
		ButterKnife.inject(this);
        PagerAdapter pagerAdapter = new PagerAdapter(this, list, getLayoutInflater());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(mIndex);
        mViewPager.setOnPageChangeListener(this);
        page.setText((mIndex+1)+"/"+list.size());

	}


    @OnClick({R.id.favorites,R.id.share})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.favorites:
                favorites.setBackgroundResource(R.drawable.ab_fav_active);
                break;
            case R.id.share:
                break;

        }

    }


    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        page.setText((i+1)+"/"+list.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
