package com.ihgoo.rosi.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ihgoo.rosi.MainActivity;
import com.ihgoo.rosi.R;
import com.ihgoo.rosi.bean.ImageBean;
import com.ihgoo.rosi.bean.ImageListBean;
import com.ihgoo.rosi.bean.SettingHelper;
import com.ihgoo.rosi.ui.ContentFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WaterfallAdapter extends ArrayAdapter<ImageBean> {
    private OnBottonListener mOnBottonListener;
    private List<ImageBean> list;
    private Context mContext;
    private int mScreenWidth;
    private SparseArray<ImageListBean> mCache = new SparseArray();
    private boolean cacheFound;

    /**
     * 监听是否到了底部
     */
    public interface OnBottonListener {
        void loadMore();
    }

    public void setOnBottonListener(OnBottonListener onBottonListener) {
        mOnBottonListener = onBottonListener;
    }

    public WaterfallAdapter(Context context, int resource, List<ImageBean> list) {
        super(context, resource, list);
        this.list = list;
        this.mContext = context;
        mScreenWidth = ((MainActivity) mContext).screenWidth;
    }


    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }


    @Override
    public long getItemId(int arg0) {
        return 0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup group) {

        if (mOnBottonListener != null) {
            int addPosition = 0;
            if (SettingHelper.getInstance().isPrfetch()) {
                addPosition = position + 9;
            } else {
                addPosition = position + 2;
            }

            if (list != null && list.size() < addPosition) {
                mOnBottonListener.loadMore();
            }
        }

        final ImageBean imageBean = list.get(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.image_item, null);
        }


        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rowIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentFragment fragment = new ContentFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("mode", ContentFragment.MODE_IMAGEDETAILLIST);
                bundle.putString("url", imageBean.getDetailurl());
                fragment.setArguments(bundle);
                ((MainActivity) mContext).addFragment(fragment);
            }
        });


        ImageLoader.getInstance().displayImage(imageBean.getImgurl(), viewHolder.rowIcon, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                viewHolder.pbLoad.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "can not be decoding";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "内存不足";
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
                                .show();
                        break;
                }
                viewHolder.pbLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view,
                                          Bitmap loadedImage) {
                viewHolder.pbLoad.setVisibility(View.INVISIBLE);
                int height = loadedImage.getHeight();
                int width = loadedImage.getWidth();
                double scale = (double) (mScreenWidth / 2) / width;
                int currentWidth = (int) (width * scale);
                int currentHeight = (int) (height * scale);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                lp.width = currentWidth;
                lp.height = currentHeight;
                view.setLayoutParams(lp);
            }


            @Override
            public void onLoadingCancelled(String paramString,
                                           View paramView) {
                viewHolder.pbLoad.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'image_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.row_icon)
        ImageView rowIcon;
        @InjectView(R.id.pb_load)
        ProgressBar pbLoad;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

