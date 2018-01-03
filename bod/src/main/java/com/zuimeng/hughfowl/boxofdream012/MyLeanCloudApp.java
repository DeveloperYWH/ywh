package com.zuimeng.hughfowl.boxofdream012;

import android.app.Application;

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
                .withApiHost("http://114.67.235.114/RestServer/api/")
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withWeChatAppId("微信AppKey")
                .withWeChatAppSecret("微信AppSecret")
                .configure();
        DatabaseManager.getInstance().init(this);

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"ybUwpgld5HUfBiAIqD8LyJDN-gzGzoHsz","IaBx4RBu8Psh2t5E0WhlH2hE");

        // 调试日志开启，发布前关闭，放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);




    }


}
