package com.zuimeng.hughfowl.latte.datamanage;

import com.avos.avoscloud.AVUser;

/**
 * Created by hughfowl on 2018/1/6.
 */

public class UserManage {


    AVUser mUser = new AVUser();

    public   void postUser(AVUser user){
        mUser = null;
        mUser = user;
    }

    public AVUser getUser(){
        return mUser;
    }

}
