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
        DatabaseManager.getInstance().getDao().insertOrReplace(profile);

        AVObject info = new AVObject("User_info");
        AVObject avater = new AVObject("User_avater");
        AVObject cart_datas = new AVObject("Cart_Datas");
        AVObject address = new AVObject("User_address");
        AVObject order_list = new AVObject("Order_list_test");

        info.put("user_id",userId);
        info.put("user_name",name);
        avater.put("user_id",userId);
        cart_datas.put("user_id",userId);
        address.put("user_id",userId);
        order_list.put("user_id",userId);

        info.saveInBackground();
        avater.saveInBackground();
        cart_datas.saveInBackground();
        address.saveInBackground();
        order_list.saveInBackground();

        //已经注册并登录成功了
        AccountManager.setSignState();
        signListener.onSignInSuccess();
    }

    public static void onQQSignIn( ISignListener signListener) {
//
//        final long userId = Long.parseLong(userprofile.getMobilePhoneNumber());
//        final String name = userprofile.getUsername();
//        final UserProfile profile = new UserProfile(userId, name);
//        DatabaseManager.getInstance().getDao().insertOrReplace(profile);

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
