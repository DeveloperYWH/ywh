package com.zuimeng.hughfowl.boxofdream012;

import android.app.Application;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import com.zuimeng.hughfowl.latee.ec.database.DaoMaster;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.icon.FontECModule;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.net.interceptors.DebugInterceptor;
import com.avos.avoscloud.AVOSCloud;

public class MyLeanCloudApp extends Application {

    @Override
     public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                //.withApiHost("http://114.67.235.114/RestServer/api/")
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withWeChatAppId("wx5c5541b353bdeebf")
                .withWeChatAppSecret("f53da789f707a831e447f4859adaded8")
                .configure();

        //初始化greendao数据库
        DatabaseManager.getInstance().init(this);


        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"WrycWyNhrQIQpAzPkPgxSfLS-gzGzoHsz","yrg5tlPipRT4L7dm9Rkcmpns");
        AVInstallation.getCurrentInstallation().saveInBackground();
        // 调试日志开启，发布前关闭，放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        //AVOSCloud.setDebugLogEnabled(true);




    }


}
