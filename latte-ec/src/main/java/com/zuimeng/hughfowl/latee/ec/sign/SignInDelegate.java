package com.zuimeng.hughfowl.latee.ec.sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.sns.SNS;
import com.avos.sns.SNSBase;
import com.avos.sns.SNSCallback;
import com.avos.sns.SNSException;
import com.avos.sns.SNSType;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
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

    // 1、定义一个 ThirdPartyType 变量
    private SNSType ThirdPartyType;

    final SNSCallback myCallback = new SNSCallback() {
        @Override
        public void done(SNSBase object, SNSException e) {
            if (e == null) {
                SNS.loginWithAuthData(object.userInfo(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        // 5、关联成功，已在 _User 表新增一条用户数据
                        avUser.saveInBackground();
//
//                        UserManage userManage = new UserManage();
//                        userManage.postUser(avUser);
                    }
                });
            } else {
                e.printStackTrace();
            }
        }
    };



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

            final String username = mName.getText().toString();
            final String password = mPassword.getText().toString();
            LatteLoader.showLoading(getContext());
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                       // Log.v("Sign in", "OK!");
                        SignHandler.onSignIn( avUser , mISignListener);

                    } else {
                        Log.v("Sign in", "Fail!");//Dev
                        LatteLoader.stopLoading();
                        //Sign in exception handle here.
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
    @OnClick(R2.id.icon_sign_in_qq)
    void onClickQQ(){
        try {
            ThirdPartyType = SNSType.AVOSCloudSNSQQ;
            SNS.setupPlatform(getContext(), SNSType.AVOSCloudSNSQQ, "1106626403", "ZVAYa4MahNpXkQEd", "https://leancloud.cn/1.1/sns/callback/ydjn335g5op2lzpk");
            SNS.loginWithCallback(getActivity(), SNSType.AVOSCloudSNSQQ, myCallback);
//            getSupportDelegate().start(new PhoneBand());

        } catch (AVException e) {
            e.printStackTrace();
        }


    }
    @OnClick(R2.id.icon_sign_in_weibo)
    void onClickWeibo(){
        try {
            ThirdPartyType = SNSType.AVOSCloudSNSSinaWeibo;
            SNS.setupPlatform(getContext(), SNSType.AVOSCloudSNSSinaWeibo, "4003695468", "17be45202b1bd675f02275e1c7aa9b81", "https://leancloud.cn/1.1/sns/callback/or06i941pmh53uru");
            SNS.loginWithCallback(getActivity(), SNSType.AVOSCloudSNSSinaWeibo, myCallback);
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 4、在页面 activity 回调里填写 ThirdPartyType
        if (resultCode == RESULT_OK) {
            SNS.onActivityResult(requestCode, resultCode, data, ThirdPartyType);
            Toast.makeText(getContext(),SNS.userInfo(SNSType.AVOSCloudSNSQQ).size(),Toast.LENGTH_LONG).show();
        }
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
