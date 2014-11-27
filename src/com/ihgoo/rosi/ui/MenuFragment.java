package com.ihgoo.rosi.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ihgoo.rosi.MainActivity;
import com.ihgoo.rosi.R;
import com.ihgoo.rosi.adapter.LeftAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class MenuFragment extends Fragment {

	@ViewInject(R.id.left_drawer)
	private ListView left_drawer;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LeftAdapter adapter = new LeftAdapter(getActivity(), R.layout.item_drawer, getResources().getStringArray(R.array.left_menu));
		left_drawer.setAdapter(adapter);
		
	}

	@OnItemClick(R.id.left_drawer)
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong){
		System.out.println(paramInt);
		switch (paramInt) {

		case 0:
			((MainActivity)getActivity()).cleanStack();
			ContentFragment contentFragment0 = new ContentFragment();
			Bundle bundle0 = new Bundle();
			bundle0.putInt("mode", ContentFragment.MODE_IMAGELIST);
			bundle0.putString("url", "http://www.dirosi.com/page/");
			bundle0.putString("page", "1");
			
			contentFragment0.setArguments(bundle0);
			((MainActivity)getActivity()).switchFragment(contentFragment0);
			break;
		
		
		case 1:
			((MainActivity)getActivity()).cleanStack();
			ContentFragment contentFragment = new ContentFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("mode", ContentFragment.MODE_IMAGELIST);
			bundle.putString("url", "http://www.dirosi.com/category/rosi/page/");
			bundle.putString("page", "1");
			
			contentFragment.setArguments(bundle);
			((MainActivity)getActivity()).switchFragment(contentFragment);
			break;
		case 2:
			((MainActivity)getActivity()).cleanStack();
			ContentFragment contentFragment1 = new ContentFragment();
			Bundle bundle1 = new Bundle();
			bundle1.putInt("mode", ContentFragment.MODE_IMAGELIST);
			bundle1.putString("url", "http://www.dirosi.com/category/disi/page/");
			bundle1.putString("page", "1");
			
			contentFragment1.setArguments(bundle1);
			((MainActivity)getActivity()).switchFragment(contentFragment1);
			break;
		case 3:
			((MainActivity)getActivity()).cleanStack();
			ContentFragment contentFragment2 = new ContentFragment();
			Bundle bundle2 = new Bundle();
			bundle2.putInt("mode", ContentFragment.MODE_IMAGELIST);
			bundle2.putString("url", "http://www.dirosi.com/category/disi/page/");
			bundle2.putString("page", "1");
			
			contentFragment2.setArguments(bundle2);
			((MainActivity)getActivity()).switchFragment(contentFragment2);
			break;
		case 4:
			Toast.makeText(getActivity(), "敬请期待...", 100).show();
			break;
		case 5:
			Toast.makeText(getActivity(), "敬请期待...", 100).show();
			break;
		case 6:
//			((MainActivity)getActivity()).switchFragment(new SettingFragment());
			((MainActivity)getActivity()).addFragment(new SettingFragment());
			((MainActivity)getActivity()).toggle();
			break;
		case 7:
			
			
			SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
			boolean isdark = sp.getBoolean("isdark", false);

			Editor edit = sp.edit();

			if (isdark) {
				edit.putBoolean("isdark", false).commit();
				Theme.changeToTheme(getActivity(),false);
			}else {
				edit.putBoolean("isdark", true).commit();
				Theme.changeToTheme(getActivity(),true);
			}
			
			
			
			
			
			break;
		
		}
		
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_left_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}

	

	

	
	
	

}
