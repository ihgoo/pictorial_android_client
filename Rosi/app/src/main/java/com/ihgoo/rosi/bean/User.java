package com.ihgoo.rosi.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by ihgoo on 2015/3/27.
 */
public class User extends BmobObject {

    private String usn;
    private String password;

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
