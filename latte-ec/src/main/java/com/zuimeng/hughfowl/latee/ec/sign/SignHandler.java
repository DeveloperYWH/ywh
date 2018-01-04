package com.zuimeng.hughfowl.latee.ec.sign;

import com.avos.avoscloud.AVUser;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.database.UserProfile;
import com.zuimeng.hughfowl.latte.app.AccountManager;

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
