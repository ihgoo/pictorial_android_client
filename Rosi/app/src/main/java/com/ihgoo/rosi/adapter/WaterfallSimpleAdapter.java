package com.ihgoo.rosi.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ihgoo.rosi.ui.MainPageActivity;
import com.ihgoo.rosi.R;
import com.ihgoo.rosi.bean.ImageSimpleBean;
import com.ihgoo.rosi.ui.PhotoDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WaterfallSimpleAdapter extends BaseAdapter {


    private List<ImageSimpleBean> list;
    private Context mContext;
    private int mScreenWidth;
    private String TAG;
    private String mUrl;


    public WaterfallSimpleAdapter(List<ImageSimpleBean> urls, Context context, String url) {
        this.list = urls;
        this.mContext = context;
        this.mUrl = url;
        mScreenWidth = ((MainPageActivity) mContext).screenWidth;
        TAG = this.getClass().getSimpleName();
    }


    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup group) {
        final ImageSimpleBean imageSimpleBean = list.get(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.image_waterfall_item, null);
        }

        final ViewHolder holder = new ViewHolder(view);
        Log.i(TAG, "outer width is " + imageSimpleBean.getWidth() + " height is " + imageSimpleBean.getHeight());

        ImageLoader.getInstance().displayImage(imageSimpleBean.getUrl(), holder.rowIcon,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.pbLoad.setVisibility(View.VISIBLE);
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
                        holder.pbLoad.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {

                        int height = loadedImage.getHeight();
                        int width = loadedImage.getWidth();
                        double scale = (double) (mScreenWidth / 2) / width;
                        int currentWidth = (int) (width * scale);
                        int currentHeight = (int) (height * scale);
                        LayoutParams lp = (LayoutParams) view.getLayoutParams();
                        lp.width = currentWidth;
                        lp.height = currentHeight;
                        view.setLayoutParams(lp);
                        holder.pbLoad.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onLoadingCancelled(String paramString,
                                                   View paramView) {
                        holder.pbLoad.setVisibility(View.INVISIBLE);
                    }
                });


        holder.rowIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("info", (Serializable) list);
                intent.putExtra("url", mUrl);
                intent.setClass(mContext, PhotoDetailActivity.class);
                mContext.startActivity(intent);
                Log.i("TAG", "image click");
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

