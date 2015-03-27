package com.ihgoo.rosi.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.api.API;
import com.ihgoo.rosi.bean.SettingHelper;
import com.ihgoo.rosi.net.VolleyHelper;
import com.ihgoo.rosi.utils.ToastUtil;

public class MainPageActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private long mQuit = 0;
	public static int screenWidth;
	public static int screenHeight;
	private int mode = 0;
	private FrameLayout mMenuLayout;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		SettingHelper.getInstance().init(this);
		setContentView(R.layout.activity_main);

		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();

		VolleyHelper.getInstance().init(getApplicationContext());

		initView();

		if (savedInstanceState == null) {
			selectItem(0);
		}

		MenuFragment menuFragment = new MenuFragment();
		getFragmentManager().beginTransaction().replace(R.id.left_drawer_frame, menuFragment).commit();
	}

	private void initView() {

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mMenuLayout = (FrameLayout) findViewById(R.id.left_drawer_frame);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);


//		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
//		mDrawerLayout, /* DrawerLayout object */
//		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
//		R.string.drawer_open, /* "open drawer" description for accessibility */
//		R.string.drawer_close /* "close drawer" description for accessibility */
//		) {
//			public void onDrawerClosed(View view) {
//				getActionBar().setTitle(mTitle);
//				invalidateOptionsMenu(); // creates call to
//											// onPrepareOptionsMenu()
//			}
//
//			public void onDrawerOpened(View drawerView) {
//				getActionBar().setTitle(mDrawerTitle);
//				invalidateOptionsMenu(); // creates call to
//											// onPrepareOptionsMenu()
//			}
//		};
	}

	private void selectItem(int position) {
		Fragment contentFragment = new CoverFragment();

		Bundle bundle = new Bundle();
		bundle.putInt("mode", CoverFragment.MODE_IMAGELIST);
		bundle.putString("url", API.URL_PAGE);
		bundle.putString("page", "1");

		contentFragment.setArguments(bundle);
		addFragment(contentFragment);

		mDrawerLayout.closeDrawer(mMenuLayout);
	}


	public void toggle() {
		if (mDrawerLayout.isDrawerOpen(mMenuLayout)) {
			mDrawerLayout.closeDrawer(mMenuLayout);
		}else {
			mDrawerLayout.openDrawer(mMenuLayout);
		}
	}

	public void switchFragment(Fragment fragment) {
		getFragmentManager().beginTransaction().remove(fragment);
		getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		// 设置滑动菜单的开关
		mDrawerLayout.closeDrawer(mMenuLayout);
	}

	public void addFragment(Fragment fragment) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.content_frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

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
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        for (int i =0;i>backStackEntryCount;i++){
            getFragmentManager().popBackStack();
        }
    }

}
