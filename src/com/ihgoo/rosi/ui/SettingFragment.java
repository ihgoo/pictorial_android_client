package com.ihgoo.rosi.ui;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.widget.Toast;

import com.ihgoo.rosi.MainActivity;
import com.ihgoo.rosi.R;
import com.ihgoo.rosi.utls.DataCleanManager;

public class SettingFragment extends PreferenceFragment implements OnPreferenceClickListener {

	
	
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {

		public boolean onPreferenceChange(Preference paramPreference,
				Object paramObject) {
			
			String str = paramObject.toString();
			CharSequence localCharSequence;
			
			if ((paramPreference instanceof ListPreference)) {
				
				ListPreference localListPreference = (ListPreference) paramPreference;
				int i = localListPreference.findIndexOfValue(str);
				if (i >= 0) {
					localCharSequence = localListPreference.getEntries()[i];
					paramPreference.setSummary(localCharSequence);
				}
				
			}

//			paramPreference.setSummary(str);
			return true;
		}
	};

	private static void bindPreferenceSummaryToValue(Preference paramPreference) {
		
		paramPreference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
		
		if ((paramPreference instanceof CheckBoxPreference)) {
			
			sBindPreferenceSummaryToValueListener.onPreferenceChange(
					paramPreference, Boolean.valueOf(PreferenceManager
							.getDefaultSharedPreferences(
									paramPreference.getContext()).getBoolean(
									paramPreference.getKey(), false)));
			return;
		}
		
		
		if ((paramPreference instanceof SwitchPreference)) {
			sBindPreferenceSummaryToValueListener.onPreferenceChange(
					paramPreference, Boolean.valueOf(PreferenceManager
							.getDefaultSharedPreferences(
									paramPreference.getContext()).getBoolean(
									paramPreference.getKey(), false)));
			return;
		}
		
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				paramPreference,
				PreferenceManager.getDefaultSharedPreferences(
						paramPreference.getContext()).getString(
						paramPreference.getKey(), ""));
	}
	

	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
		getActivity().getActionBar().setNavigationMode(0);
		getActivity().getActionBar().setTitle("设置");
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			((MainActivity)getActivity()).toggle();
			break;

		}
		
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setHasOptionsMenu(true);
		
		addPreferencesFromResource(R.xml.preference);
		bindPreferenceSummaryToValue(findPreference("PERF_SECNETWORKMETHOD"));
		bindPreferenceSummaryToValue(findPreference("PERF_PREFETCH"));
		bindPreferenceSummaryToValue(findPreference("PERF_BIG_OPTIMIZATION"));
		bindPreferenceSummaryToValue(findPreference("PERF_ENCODEUTF8"));
		
		
		
		Preference cleanPreference = findPreference("PERF_CLEAN_CACHE");
		cleanPreference.setOnPreferenceClickListener(this);
		
	}


	public void onStop() {
		// SettingHelper.getInstance().reload();
		super.onStop();
	}


	@Override
	public boolean onPreferenceClick(Preference preference) {
		Toast.makeText(getActivity(), "清空缓存中...", 50).show();
		DataCleanManager.cleanApplicationData(getActivity());
		Toast.makeText(getActivity(), "缓存清理完成", 100).show();
		return false;
	}

}
