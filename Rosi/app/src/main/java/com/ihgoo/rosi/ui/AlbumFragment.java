package com.ihgoo.rosi.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AbsListView;
import com.ihgoo.rosi.R;
import com.ihgoo.rosi.adapter.WaterfallAdapter;
import com.ihgoo.rosi.adapter.WaterfallSimpleAdapter;
import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageListBean;
import com.ihgoo.rosi.bean.ImageSimpleBean;
import com.ihgoo.rosi.net.ImageDetailListLoader;
import com.ihgoo.rosi.net.ImageListLoader;
import com.ihgoo.rosi.utils.Misc;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ihgoo on 2015/3/26.
 */
public class AlbumFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PLA_AbsListView.OnScrollListener, View.OnTouchListener {


    @InjectView(R.id.list)
    MultiColumnListView waterfallView;//可以把它当成一个listView
    @InjectView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @InjectView(R.id.goBack)
    ImageView goBack;
    @InjectView(R.id.mainTitile)
    TextView mainTitile;
    @InjectView(R.id.right_btn)
    ImageView rightBtn;
    @InjectView(R.id.right_tv)
    TextView rightTv;
    @InjectView(R.id.titlebar)
    RelativeLayout titlebar;

    private ImageDetailListLoaderCallbacks mImageDetailListLoaderCallbacks;
    private final int isLoading = 1;
    private final int isParsering = 2;
    private String mUrl;
    private List<ImageSimpleBean> mUrlList;
    private List<ImageBean> mImageBeans;
    private boolean isReadMore = false;
    private String mPage;


    public WaterfallSimpleAdapter madapter;


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int i = msg.what;
            Log.i("ContentFragment", "message what is " + i);
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


    List<ImageBean> imageBeanList =  new ArrayList<ImageBean>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        mUrl = (String)bundle.get("url");

        mImageDetailListLoaderCallbacks = new ImageDetailListLoaderCallbacks();
        getLoaderManager().initLoader(0, new Bundle(), this.mImageDetailListLoaderCallbacks);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.blue);
        initView();
        refresh();
    }

    private void initView() {
        mainTitile.setText("专辑");
        waterfallView.setOnScrollListener(this);
        waterfallView.setOnTouchListener(this);
        View view = new View(getActivity());
        int headerHeight = Misc.dip2px(48f);
        PLA_AbsListView.LayoutParams lp = new PLA_AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,headerHeight);
        view.setLayoutParams(lp);
        waterfallView.addHeaderView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
            view = inflater.inflate(R.layout.layout_content_small, null);

        if (view == null) {
            try {
                throw new Exception("Mode error,are you chose a correct mode?");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ButterKnife.inject(this, view);
        return view;
    }

    public void refresh() {
        getLoaderManager().restartLoader(0, null, mImageDetailListLoaderCallbacks).forceLoad();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {
        switch (scrollState) {
            case 0:
                scrollFlag = false;
                break;
            case 1:
                scrollFlag = true;
                break;
            case 2:
                scrollFlag = true;
                break;
        }

    }


    @Override
    public void onScroll(PLA_AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (scrollFlag) {
            if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
                titlebar.setVisibility(View.INVISIBLE);
            } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
                titlebar.setVisibility(View.VISIBLE);
            } else {
                return;
            }
            lastVisibleItemPosition = firstVisibleItem;
        }

    }

    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    private int lastItemPosition = 0; // 标记上次停止时位置
    private int transparentValue = 255; //透明度
    private int lastX; //标记上次X坐标位置
    private final int offset = 30;


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
//        if (lastX < x) {
//            transparentValue = transparentValue - offset < 0 ? 0 : transparentValue - offset;
//            titlebar.getBackground().setAlpha(transparentValue);
//        } else if (lastX > x) {
//            transparentValue = transparentValue + offset > 255 ? 255 : transparentValue + offset;
//            titlebar.getBackground().setAlpha(transparentValue);
//        }
//
//        lastX = x;
//
//        int action = motionEvent.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_UP:
//                if (transparentValue < 255 / 2) {
//                    titlebar.getBackground().setAlpha(transparentValue);
//                } else if (transparentValue > 255 / 2) {
//                    titlebar.getBackground().setAlpha(transparentValue);
//                }
//
//                break;
//        }


        return false;
    }




    public class ImageDetailListLoaderCallbacks implements
            LoaderManager.LoaderCallbacks<List<ImageSimpleBean>> {


        @Override
        public Loader<List<ImageSimpleBean>> onCreateLoader(int arg0, Bundle arg1) {
            return new ImageDetailListLoader(getActivity(), mHandler, mUrl);
        }

        @Override
        public void onLoadFinished(Loader<List<ImageSimpleBean>> arg0,
                                   List<ImageSimpleBean> urList) {
            mUrlList = urList;
            madapter = new WaterfallSimpleAdapter(mUrlList, getActivity(), mUrl);
            waterfallView.setAdapter(madapter);
        }

        @Override
        public void onLoaderReset(Loader<List<ImageSimpleBean>> arg0) {
        }
    }




}
