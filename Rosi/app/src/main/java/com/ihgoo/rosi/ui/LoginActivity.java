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

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ihgoo on 2015/3/27.
 */
public class LoginActivity extends Activity {


    @InjectView(R.id.goBack)
    ImageView goBack;
    @InjectView(R.id.mainTitile)
    TextView mainTitile;
    @InjectView(R.id.right_btn)
    ImageView rightBtn;
    @InjectView(R.id.right_tv)
    TextView rightTv;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.bt_login)
    RelativeLayout btLogin;

    @InjectView(R.id.bt_register)
    RelativeLayout btRegister;


    String usn;
    String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mainTitile.setText("登录");

    }

    @OnClick(R.id.bt_login)
    public void login(View view){

        final BmobUser user = new BmobUser();
        usn = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        user.setUsername(usn);
        user.setPassword(password);
        user.login(this,new SaveListener() {
            @Override
            public void onSuccess() {

                try {
                    DB snappydb = DBFactory.open(LoginActivity.this);
                    snappydb.put(NosqlConstant.USN, usn);
                    snappydb.put(NosqlConstant.PASSWORD,password);
                    snappydb.put(NosqlConstant.IS_FIRST,true);
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }

                Intent intent= new Intent(LoginActivity.this,MainPageActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.showLongTime(LoginActivity.this, "登陆失败");
                etPassword.setText("");
            }
        });

    }

    @OnClick(R.id.bt_register)
    public void register(View view){

        Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);

    }


}
