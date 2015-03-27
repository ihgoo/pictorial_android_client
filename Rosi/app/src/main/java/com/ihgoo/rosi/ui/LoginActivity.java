package com.ihgoo.rosi.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihgoo.rosi.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
    @InjectView(R.id.bt_register)
    RelativeLayout btRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }


}
