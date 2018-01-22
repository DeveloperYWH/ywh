package com.zuimeng.hughfowl.boxofdream012;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.Toast;

import com.zuimeng.hughfowl.latee.ec.launcher.LauncherDelegate;
import com.zuimeng.hughfowl.latee.ec.launcher.LauncherScrollDelegate;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latee.ec.sign.ISignListener;
import com.zuimeng.hughfowl.latee.ec.sign.SignInDelegate;
import com.zuimeng.hughfowl.latee.ec.sign.SignUpDelegate;
import com.zuimeng.hughfowl.latte.activities.ProxyActivity;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.launcher.ILauncherListener;
import com.zuimeng.hughfowl.latte.ui.launcher.OnLauncherFinshTag;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.AVException;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener
    {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this,true);

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });

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
            Toast.makeText(this, "登录成功ヾ(=･ω･=)o", Toast.LENGTH_LONG).show();
            getSupportDelegate().start(new EcBottomDelegate());
        }

        @Override
        public void onSignUpSuccess() {
            Toast.makeText(this, "注册成功ヾ(=･ω･=)o", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLauncherFinsh(OnLauncherFinshTag tag) {

            switch (tag) {
                case SIGNED:
                    // Toast.makeText(this, "启动结束，账户已登录", Toast.LENGTH_LONG).show();
                    getSupportDelegate().startWithPop(new EcBottomDelegate());
                    break;
                case NOT_SIGNED:
                    // Toast.makeText(this, "启动结束，账户未登录", Toast.LENGTH_LONG).show();
                    getSupportDelegate().startWithPop(new SignUpDelegate());
                    break;
                default:
                    break;
            }


        }
    }
