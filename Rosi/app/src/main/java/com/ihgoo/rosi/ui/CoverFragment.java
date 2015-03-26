package com.ihgoo.rosi.ui;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huewu.pla.lib.MultiColumnListView;
import com.ihgoo.rosi.R;
import com.ihgoo.rosi.adapter.WaterfallAdapter;
import com.ihgoo.rosi.adapter.WaterfallAdapter.OnBottonListener;
import com.ihgoo.rosi.adapter.WaterfallSimpleAdapter;
import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageListBean;
import com.ihgoo.rosi.bean.ImageSimpleBean;
import com.ihgoo.rosi.net.ImageDetailListLoader;
import com.ihgoo.rosi.net.ImageListLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CoverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	
	@InjectView(R.id.list)
	MultiColumnListView waterfallView;//可以把它当成一个listView

    @InjectView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;


	private SparseArray<ImageListBean> mCache = new SparseArray();
	private ImageListLoaderCallbacks mImageListLoaderCallbacks;
	private ImageDetailListLoaderCallbacks mImageDetailListLoaderCallbacks;
	public static int mode = 0;
	public final static int MODE_IMAGELIST = 0;
	public final static int MODE_IMAGEDETAILLIST = 1;
	private final int isLoading = 1;
	private final int isParsering = 2;
	private String mUrl;
	private List<ImageSimpleBean> mUrlList;
	private List<ImageBean> mImageBeans;
	private boolean isReadMore = false;
	private String mPage;



	
	private WaterfallAdapter adapter;
	public WaterfallSimpleAdapter madapter;
	
	
	
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			int i = msg.what;
			Log.i("ContentFragment", "message what is "+i);
			switch (i) {
			case isLoading:
//				waterfallView.setVisibility(View.GONE);
//				loading.setVisibility(View.VISIBLE);
				break;
			case isParsering:
//				waterfallView.setVisibility(View.VISIBLE);
//				loading.setVisibility(View.GONE);
				break;
			}
		}
		
	};


	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Bundle bundle = this.getArguments();
		int mode = swithMode(bundle);

		
		switch (mode) {
		case MODE_IMAGEDETAILLIST:
			mImageDetailListLoaderCallbacks = new ImageDetailListLoaderCallbacks();
			getLoaderManager().initLoader(0, new Bundle(), this.mImageDetailListLoaderCallbacks);
			break;
		case MODE_IMAGELIST:
			mImageListLoaderCallbacks = new ImageListLoaderCallbacks();
			getLoaderManager().initLoader(0, new Bundle(),this.mImageListLoaderCallbacks);
			adapter = new WaterfallAdapter(getActivity(), R.layout.image_item,new ArrayList<ImageBean>());
			adapter.setOnBottonListener(new OnBottonListener() {
				
				@Override
				public void loadMore() {
					if (!isReadMore) {
						isReadMore = true;
						Log.i("ContentFragment", "滚到底部了......第"+mPage+"页");
						if (mPage==null ) {
							mPage = "0";
						}
						mPage = Integer.parseInt(mPage)+1+"";
						readMore();
					}
				}
			});
			
			
			break;
		}
		
	}
	

		

	private void readMore(){
		Bundle localBundle1 = new Bundle();
	    localBundle1.putString("page", mPage);
	    getLoaderManager().restartLoader(0, localBundle1, mImageListLoaderCallbacks).forceLoad();
	}
	
	private int swithMode(Bundle bundle){
		if (bundle!=null) {
			mode =  bundle.getInt("mode");
			mUrl = bundle.getString("url");
			mPage = bundle.getString("page");
		}
		return mode;
	}
	
	
	@Override
	public void onDetach() {
		if (mode == MODE_IMAGEDETAILLIST) {
			mode = MODE_IMAGELIST;
		}
		super.onDetach();
	}
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.blue);
		refresh();
	    switch (mode) {
		case MODE_IMAGEDETAILLIST:
			break;
		case MODE_IMAGELIST:
			View view = View.inflate(getActivity(), R.layout.loading, null);
			waterfallView.addFooterView(view);
			waterfallView.setAdapter(adapter);
			break;
	    }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null ;
		if (mode == MODE_IMAGELIST) {
			view = inflater.inflate(R.layout.layout_content, null);
		}else if (mode == MODE_IMAGEDETAILLIST) {
			view = inflater.inflate(R.layout.layout_content_small, null);
		}
		
		if (view == null) {
			try {
				throw new Exception("Mode error,are you chose a correct mode?");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        ButterKnife.inject(this,view);
		return view;
	}
	
	public void refresh(){
		switch (mode) {
		case MODE_IMAGEDETAILLIST:
			getLoaderManager().restartLoader(0, null, mImageDetailListLoaderCallbacks).forceLoad();
			break;
		case MODE_IMAGELIST:
			getLoaderManager().restartLoader(0, null, mImageListLoaderCallbacks).forceLoad();
			break;
		}
	}

    @Override
    public void onRefresh() {
        refresh();
    }


    public class ImageListLoaderCallbacks implements
			LoaderManager.LoaderCallbacks<ImageListBean> {

		@Override
		public Loader<ImageListBean> onCreateLoader(int arg0, Bundle bundle) {
			return new ImageListLoader(getActivity(), mHandler,mUrl, bundle!=null? bundle.getString("page"):mPage  );
		}

		@Override
		public void onLoadFinished(Loader<ImageListBean> arg0,
				ImageListBean imageBeans) {
            swipeLayout.setRefreshing(false);
			mCache.put(Integer.parseInt(mPage) , imageBeans);
			adapter.addAll(mCache.get(Integer.parseInt(mPage)).getAll());
			adapter.notifyDataSetChanged();
			isReadMore = false;
		}

		@Override
		public void onLoaderReset(Loader<ImageListBean> arg0) {
		}

	}
	
	
	
	public class ImageDetailListLoaderCallbacks implements
			LoaderManager.LoaderCallbacks<List<ImageSimpleBean>> {
		

		@Override
		public Loader<List<ImageSimpleBean>> onCreateLoader(int arg0, Bundle arg1) {
			return new ImageDetailListLoader(getActivity(), mHandler,mUrl);
		}

		@Override
		public void onLoadFinished(Loader<List<ImageSimpleBean>> arg0,
				List<ImageSimpleBean> urList) {
			mUrlList = urList;
			madapter = new WaterfallSimpleAdapter(mUrlList, getActivity(),mUrl);
			waterfallView.setAdapter(madapter);
		}

		@Override
		public void onLoaderReset(Loader<List<ImageSimpleBean>> arg0) {
		}
	}

}
