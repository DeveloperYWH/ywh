package com.zuimeng.hughfowl.latee.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.net.RestClient;
import com.zuimeng.hughfowl.latte.net.callback.ISuccess;
import com.zuimeng.hughfowl.latte.util.log.LatteLogger;
import com.zuimeng.hughfowl.latte.wechat.LatteWeChat;
import com.zuimeng.hughfowl.latte.wechat.callbacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2017/9/14.
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }



    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            //RestClient.builder()
            //        .url("https://leancloud.cn:443/1.1/login")
            //        .params("email", mName.getText().toString())
            //        .params("password", mPassword.getText().toString())
            //        .success(new ISuccess() {
            //            @Override
            //            public void onSuccess(String response) {
            //                LatteLogger.json("USER_PROFILE", response);
            //                SignHandler.onSignIn(response, mISignListener);
            //            }
            //        })
            //        .build()
            //        .post();

            final String username = mName.getText().toString();
            final String password = mPassword.getText().toString();

            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        Log.v("Sign in", "OK!");//Dev
                        //Sign in callback here.
                        SignHandler.onSignIn( avUser , mISignListener);
                    } else {
                        Log.v("Sign in", "Fail!");//Dev
                        //Sign in exception handle here.
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            );

        }
    }

     @OnClick(R2.id.icon_sign_in_wechat)
     void onClickWeChat() {
        LatteWeChat
                .getInstance()
                .onSignSuccess(new IWeChatSignInCallback() {
                    @Override
                    public void onSignInSuccess(String userInfo) {
                        Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
                    }
                })
                .signIn();

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }
    @OnClick(R2.id.tv_phone_sign_up)
    void onClickPhoneSignIn() {
        getSupportDelegate().start(new PhoneSignInDelegate());
    }



    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入用户名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }



}
