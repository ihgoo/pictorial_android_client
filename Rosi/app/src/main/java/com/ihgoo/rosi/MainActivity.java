package com.ihgoo.rosi;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ihgoo.rosi.api.API;
import com.ihgoo.rosi.bean.SettingHelper;
import com.ihgoo.rosi.net.ImageLoaderHelper;
import com.ihgoo.rosi.net.VolleyHelper;
import com.ihgoo.rosi.ui.ContentFragment;
import com.ihgoo.rosi.ui.MenuFragment;
import com.ihgoo.rosi.ui.Theme;
import com.ihgoo.rosi.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import net.youmi.android.AdManager;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;

	private long mQuit = 0;
	/**
	 * 屏幕适配长宽
	 */
	public static int screenWidth;
	public static int screenHeight;
	private ActionBar actionBar;
	private int mode = 0;

	private FrameLayout mMenuLayout;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Theme.onActivityCreateSetTheme(this);

		SettingHelper.getInstance().init(this);

		// init AD
		AdManager.getInstance(this).init("ac1e4ac779a3a292", "7d1590fe98d483d6", false);
		AdManager.getInstance(this).setUserDataCollect(true);

		// SpotManager.getInstance(this).checkPermission(this);

		// init umeng
		MobclickAgent.updateOnlineConfig(this);
		MobclickAgent.setDebugMode(true);

		setContentView(R.layout.activity_main);

		// 初始化屏幕数据
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();

		// init volley
		VolleyHelper.getInstance().init(getApplicationContext());

		// init imageloader
		ImageLoaderHelper.getInstance().init(getApplicationContext());

		// init slidingMenu
		initView();

		if (savedInstanceState == null) {
			selectItem(0);
		}

		MenuFragment menuFragment = new MenuFragment();

		getFragmentManager().beginTransaction().replace(R.id.left_drawer_frame, menuFragment).commit();

	}

	private void initView() {

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mMenuLayout = (FrameLayout) findViewById(R.id.left_drawer_frame);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment contentFragment = new ContentFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("mode", ContentFragment.MODE_IMAGELIST);
		bundle.putString("url", API.URL_PAGE);
		bundle.putString("page", "1");

		contentFragment.setArguments(bundle);
		addFragment(contentFragment);

		// update selected item and title, then close the drawer
		// mDrawerList.setItemChecked(position, true);
		setTitle("首页");
		mDrawerLayout.closeDrawer(mMenuLayout);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	// 改变actionbar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// switch (mode) {
		// case 0:
		// inflater.inflate(R.xml.actionbar_main, menu);
		// break;
		// case 1:
		// inflater.inflate(R.xml.actionbar_setting, menu);
		// break;
		// case 2:
		//
		// break;
		// case 3:
		//
		// break;
		// default:
		// break;
		// }
		return super.onCreateOptionsMenu(menu);
	}

	// //响应actionbar的点击事件
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// Log.i("MainActivity", "Menu item :"+item.getItemId());
	//
	// switch (item.getItemId()) {
	// case R.id.action_settings:
	//
	// break;
	// case R.id.action_refresh:
	// switch (ContentFragment.mode) {
	// case ContentFragment.MODE_IMAGELIST:
	//
	// break;
	// case ContentFragment.MODE_IMAGEDETAILLIST:
	//
	// break;
	//
	// }
	// break;
	// }
	//
	// return super.onOptionsItemSelected(item);
	// }

	public void toggle() {
		if (mDrawerLayout.isDrawerOpen(mMenuLayout)) {
			mDrawerLayout.closeDrawer(mMenuLayout);
		}else {
			mDrawerLayout.openDrawer(mMenuLayout);
		}
	}

	/**
	 * 选择fragment，并自动关闭滑动菜单
	 * 
	 * @param fragment
	 */
	public void switchFragment(Fragment fragment) {
		getFragmentManager().beginTransaction().remove(fragment);

		getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		// 设置滑动菜单的开关
		mDrawerLayout.closeDrawer(mMenuLayout);
	}

	/**
	 * 打开一个fragment，并添加到fragment栈
	 * 
	 * @param fragment
	 */
	public void addFragment(Fragment fragment) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.content_frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/**
	 * 退栈
	 */
	public void onBackPressed() {

		if (getFragmentManager().getBackStackEntryCount() == 1 || getFragmentManager().getBackStackEntryCount() == 0) {

			if (mQuit != 0 && System.currentTimeMillis() - mQuit < 5000) {
				finish();
			} else {
				mQuit = System.currentTimeMillis();

                ToastUtil.showMediumTime(this, getString(R.string.exit_tip));
			}
		} else {
			getFragmentManager().popBackStack();
		}

	}

	public void cleanStack() {
		getFragmentManager().popBackStack();
		getFragmentManager().popBackStack();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen"); // 统计页面
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		MobclickAgent.onPageEnd("SplashScreen");
	}

}
