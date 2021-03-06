package com.zuimeng.hughfowl.boxofdream012;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.PushServiceParcel;
import com.fm.openinstall.OpenInstall;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.launcher.LauncherDelegate;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latee.ec.sign.ISignListener;
import com.zuimeng.hughfowl.latee.ec.sign.SignUpDelegate;
import com.zuimeng.hughfowl.latte.activities.ProxyActivity;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.launcher.ILauncherListener;
import com.zuimeng.hughfowl.latte.ui.launcher.OnLauncherFinshTag;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);

        //开启推送
        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, ExampleActivity.class);
        PushService.setDefaultChannelId(this,"");
        AVInstallation.getCurrentInstallation().saveInBackground();


        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"WrycWyNhrQIQpAzPkPgxSfLS-gzGzoHsz","yrg5tlPipRT4L7dm9Rkcmpns");
        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });
        //渠道分析
        OpenInstall.init(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //JPushInterface.onResume(this);
    }


    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        LatteLoader.stopLoading();
        Toast.makeText(this, "登录成功ヾ(=･ω･=)o", Toast.LENGTH_LONG).show();
        getSupportDelegate().start(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {

        String userId = String.valueOf(DatabaseManager
                .getInstance()
                .getDao()
                .queryBuilder()
                .listLazy()
                .get(0)
                .getUserId());
        String name = String.valueOf(DatabaseManager
                .getInstance()
                .getDao()
                .queryBuilder()
                .listLazy()
                .get(0)
                .getName());
        String ShopName = "我的店铺(编辑更改)";

        AVObject info = AVObject.create("User_info");
        info.put("user_id", userId);
        info.put("user_name", name);
        info.saveInBackground();

        AVObject cart_datas = AVObject.create("Cart_Datas");
        cart_datas.put("user_id", userId);
        cart_datas.saveInBackground();

        final AVObject avater = AVObject.create("User_avater");
        avater.put("user_id", userId);


        AVQuery<AVObject> query = new AVQuery<>("Image_File");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                AVFile image = list.get(0).getAVFile("image");
                avater.put("image", image);
                avater.saveInBackground();
            }
        });


        AVObject address = AVObject.create("User_address");
        address.put("user_id", userId);
        address.saveInBackground();

        AVObject order_list = AVObject.create("Order_list_test");
        order_list.put("user_id", userId);
        order_list.saveInBackground();

        AVObject moments = AVObject.create("User_moments");
        moments.put("user_id", userId);
        moments.saveInBackground();


        AVObject shop_info = AVObject.create("Shop_Info");
        shop_info.put("user_id", userId);
        shop_info.put("shop_name", ShopName);
        shop_info.saveInBackground();

        final AVObject shop_logo = AVObject.create("Shop_Logo");
        shop_logo.put("user_id", userId);
        shop_logo.saveInBackground();

        AVQuery<AVObject> shop_query = new AVQuery<>("Image_File");
        shop_query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                AVFile image = list.get(0).getAVFile("image");
                shop_logo.put("image", image);
                shop_logo.saveInBackground();
            }
        });

        AVObject shop_list = AVObject.create("shop_display");
        shop_list.put("userId", userId);
        shop_list.saveInBackground();

        AVObject shop_series = AVObject.create("Shop_series");
        shop_series.put("user_id", userId);
        shop_series.saveInBackground();

        LatteLoader.stopLoading();
        Toast.makeText(this, "注册成功ヾ(=･ω･=)o", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinsh(OnLauncherFinshTag tag) {

        switch (tag) {
            case SIGNED:
                // Toast.makeText(this, "启动结束，账户已登录", Toast.LENGTH_LONG).show();
                LatteLoader.stopLoading();
                getSupportDelegate().replaceFragment(new EcBottomDelegate(), false);
                break;
            case NOT_SIGNED:
                // Toast.makeText(this, "启动结束，账户未登录", Toast.LENGTH_LONG).show();
                LatteLoader.stopLoading();
                getSupportDelegate().replaceFragment(new SignUpDelegate(), false);
                break;
            default:
                break;
        }


    }
}
