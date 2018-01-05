package com.zuimeng.hughfowl.latee.ec.sign;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.database.UserProfile;
import com.zuimeng.hughfowl.latte.app.AccountManager;

import java.util.List;

/**
 * Created by hughfowl on 2017/9/14.
 */

public class SignHandler {

    public static void onSignIn(AVUser userprofile, ISignListener signListener) {

        final long userId = Long.parseLong(userprofile.getMobilePhoneNumber());
        final String name = userprofile.getUsername();
        final UserProfile profile = new UserProfile(userId, name);
        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登录成功了
        AccountManager.setSignState();
        signListener.onSignInSuccess();
    }


    public static void onSignUp(AVUser userprofile, ISignListener signListener) {

        final long userId = Long.parseLong(userprofile.getMobilePhoneNumber());
        final String name = userprofile.getUsername();
        final UserProfile profile = new UserProfile(userId, name);
        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登录成功了
        AccountManager.setSignState();
        signListener.onSignUpSuccess();
    }


}
