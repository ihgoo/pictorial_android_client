package com.ihgoo.rosi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihgoo.rosi.R;
import com.ihgoo.rosi.bean.User;
import com.ihgoo.rosi.persistence.NosqlConstant;
import com.ihgoo.rosi.utils.ToastUtil;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by ihgoo on 2015/3/27.
 */
public class RegisterActivity extends Activity {


    String phone;
    String password;

    @InjectView(R.id.goBack)
    ImageView goBack;
    @InjectView(R.id.mainTitile)
    TextView mainTitile;
    @InjectView(R.id.right_btn)
    ImageView rightBtn;
    @InjectView(R.id.right_tv)
    TextView rightTv;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.et_re_password)
    EditText etRePassword;
    @InjectView(R.id.bt_register)
    RelativeLayout btRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        initView();
        SMSSDK.initSDK(this, "6668a01f151a", "0b31e89ddde819acc068fb2899851716");
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    phone = (String) phoneMap.get("phone");
                    tvPhone.setText("您的手机号：  "+phone);
                }
            }
        });
        registerPage.show(this);
    }

    private void initView() {
        mainTitile.setText("注册");
    }

    @OnClick(R.id.bt_register)
    void register(View v) {

        password = etRePassword.getText().toString().trim();
        User user = new User();
        user.setUsn(phone);
        user.setPassword(password);
        user.save(this, new SaveListener() {
            @Override
            public void onSuccess() {

                try {
                    DB snappydb = DBFactory.open(RegisterActivity.this);
                    snappydb.put(NosqlConstant.USN,phone);
                    snappydb.put(NosqlConstant.PASSWORD,password);
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtil.showMediumTime(RegisterActivity.this, "注册失败~");
            }
        });
    }


}