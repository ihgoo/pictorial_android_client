package com.ihgoo.rosi.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.persistence.NosqlConstant;
import com.ihgoo.rosi.utils.ToastUtil;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ihgoo on 2015/3/27.
 */
public class SettingFragment extends Fragment {

    @InjectView(R.id.goBack)
    ImageView goBack;
    @InjectView(R.id.mainTitile)
    TextView mainTitile;
    @InjectView(R.id.right_btn)
    ImageView rightBtn;
    @InjectView(R.id.right_tv)
    TextView rightTv;
    @InjectView(R.id.delete_cache)
    ImageView deleteCache;
    @InjectView(R.id.cache_arrow)
    ImageButton cacheArrow;
    @InjectView(R.id.tv_cache_value)
    TextView tvCacheValue;
    @InjectView(R.id.rl_delete_cache)
    RelativeLayout rlDeleteCache;
    @InjectView(R.id.check_update)
    ImageView checkUpdate;
    @InjectView(R.id.check_update_arrow)
    ImageButton checkUpdateArrow;
    @InjectView(R.id.iv_update_redpoint)
    ImageView ivUpdateRedpoint;
    @InjectView(R.id.rl_check_update)
    RelativeLayout rlCheckUpdate;
    @InjectView(R.id.iv_about_us)
    ImageView ivAboutUs;
    @InjectView(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @InjectView(R.id.iv_starme)
    ImageView ivStarme;
    @InjectView(R.id.rl_starme)
    RelativeLayout rlStarme;
    @InjectView(R.id.iv_bind_phone)
    ImageView ivBindPhone;
    @InjectView(R.id.rl_suggestion)
    RelativeLayout rlSuggestion;
    @InjectView(R.id.iv_share)
    ImageView ivShare;
    @InjectView(R.id.rl_share)
    RelativeLayout rlShare;
    @InjectView(R.id.btn_exit)
    Button btnExit;


    private Context mContext = getActivity();
    private double folderSize;
    private final String PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/allinone/";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mainTitile.setText("偏好设置");
        goBack.setVisibility(View.VISIBLE);
        try {
            folderSize = getFolderSize(new File(PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvCacheValue.setText(getFormatSize(folderSize));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_setting, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }



    @OnClick({R.id.goBack,R.id.btn_exit})
    void onClick(View v){
        switch (v.getId()) {
            case R.id.goBack:
                getFragmentManager().popBackStack();
                break;
            case R.id.rl_delete_cache:
                cleanCache();
                break;
            case R.id.rl_share:

//                Intent intent = new Intent(this, InviteActivity.class);
//                startActivity(intent);

                break;

            case R.id.rl_check_update:

//                Animation anim = AnimationUtils.loadAnimation(this,
//                        R.anim.update_roate);
//                LinearInterpolator lir = new LinearInterpolator();
//                anim.setInterpolator(lir);
//                checkUpdate.startAnimation(anim);

//                UmengUpdateAgent.setUpdateAutoPopup(false);
//                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//                    @Override
//                    public void onUpdateReturned(int updateStatus,
//                                                 UpdateResponse updateInfo) {
//                        switch (updateStatus) {
//                            case UpdateStatus.Yes:
//                                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
//                                break;
//                            case UpdateStatus.No:
//                                ToastUtil.showMediumTime(mContext, "已经是最新版本啦~");
//                                break;
//                            case UpdateStatus.NoneWifi:
//                                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
//                                break;
//                            case UpdateStatus.Timeout:
//                                ToastUtil.showMediumTime(mContext, "网络连接异常,请检查网络");
//                                break;
//                            default:
//                                ToastUtil.showMediumTime(mContext, "已经是最新版本啦~");
//                                break;
//                        }
//                    }
//                });
//
//                UmengUpdateAgent.update(this);
                break;
            case R.id.rl_about_us:
//                Intent aboutIntent = new Intent(this, AboutActivity.class);
//                startActivity(aboutIntent);
                break;
            case R.id.btn_exit:

                try {
                    DB db = DBFactory.open(getActivity());
                    db.put(NosqlConstant.IS_FIRST,false);
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }

//                CustomDialog.Builder builder = new CustomDialog.Builder(this);
//                builder.setMessage("确定退出登录吗？");
//                builder.setTitle("提示");
//                builder.setNegativeButton("",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.setPositiveButton("",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                Settings.set("isfirst", "0");
//                                AppManager.getInstance().exit(SettingActivity.this);
//                            }
//                        });
//                builder.create().show();
                break;
            case R.id.rl_suggestion:
//                Intent intent1 = new Intent();
//                intent1.setClass(SettingActivity.this, SuggestionActivity.class);
//                startActivity(intent1);
                break;
        }
    }


    private void cleanCache() {
        if (folderSize <= 0.01) {
            ToastUtil.showShortTime(mContext, "无需清空");
            return;
        }

        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("已占用" + getFormatSize(folderSize) + "内存，您确定要清理缓存吗?")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {
                                    deleteFolderFile(PATH, true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ToastUtil.showShortTime(mContext, "缓存已经清除");
                                tvCacheValue.setText("0.0Byte(s)");
                                folderSize = 0;
                            }
                        }).setNegativeButton(android.R.string.cancel, null)
                .create().show();

    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param filePath
     * @param deleteThisPath
     * @return
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath)
            throws IOException {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);

            if (file.isDirectory()) {// 处理目录
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFolderFile(files[i].getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {// 如果是文件，删除
                    file.delete();
                } else {// 目录
                    if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(java.io.File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }




}
