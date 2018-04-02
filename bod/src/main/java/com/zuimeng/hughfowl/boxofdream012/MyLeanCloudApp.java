package com.zuimeng.hughfowl.boxofdream012;

import android.app.Application;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.zuimeng.hughfowl.boxofdream012.chat_kit.CustomUserProvider;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.icon.FontECModule;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.net.interceptors.DebugInterceptor;

import cn.leancloud.chatkit.LCChatKit;

public class MyLeanCloudApp extends Application {

    private final String APP_ID = "WrycWyNhrQIQpAzPkPgxSfLS-gzGzoHsz";
    private final String APP_KEY = "yrg5tlPipRT4L7dm9Rkcmpns";

    MyLeanCloudApp(){
    }

    @Override
     public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontECModule())
                //.withApiHost("http://114.67.235.114/RestServer/api/")
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withWeChatAppId("wx5c5541b353bdeebf")
                .withWeChatAppSecret("f53da789f707a831e447f4859adaded8")
                .configure();

        //初始化greendao数据库
        DatabaseManager.getInstance().init(this);

        // 调试日志开启，发布前关闭，放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        //AVOSCloud.setDebugLogEnabled(true);

        LCChatKit.getInstance().init(getApplicationContext(),APP_ID,APP_KEY);
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());



    }


}
