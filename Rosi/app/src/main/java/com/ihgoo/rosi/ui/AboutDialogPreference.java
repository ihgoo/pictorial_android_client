package com.ihgoo.rosi.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutDialogPreference extends DialogFragment {
//	private final String LOG_TAG = getClass().getSimpleName();
//
//	public AboutDialogPreference(Context paramContext,
//			AttributeSet paramAttributeSet) {
//		super(paramContext, paramAttributeSet);
//	}
//
//	protected void onBindDialogView(View paramView) {
//		Log.v(this.LOG_TAG, "onBindDialogView");
//		((WebView) paramView).loadUrl("file:///android_asset/html/about.html");
//	}
//
//	public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//		Log.v(this.LOG_TAG, "onClick");
//	}
//
//	protected View onCreateDialogView() {
//		Log.v(this.LOG_TAG, "onCreateDialogView");
//		return ((LayoutInflater) getContext().getSystemService(
//				"layout_inflater")).inflate(R.layout.dialog_about, null);
//	}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
