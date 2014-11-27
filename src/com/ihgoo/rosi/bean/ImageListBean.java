package com.ihgoo.rosi.bean;

import java.util.ArrayList;
import java.util.List;

public class ImageListBean {
	private List<ImageBean> mImageBeans = new ArrayList();
	private int mCount;

	public void add(ImageBean paramDetailBean) {
		this.mImageBeans.add(paramDetailBean);
		this.mCount = (1 + this.mCount);
	}

	public List<ImageBean> getAll() {
		return this.mImageBeans;
	}

	public int getCount() {
		return this.mCount;
	}
}
