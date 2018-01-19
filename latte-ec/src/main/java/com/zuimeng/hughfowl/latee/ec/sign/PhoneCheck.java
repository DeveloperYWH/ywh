package com.zuimeng.hughfowl.latee.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.usermanage.UserManage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/1/6.
 */

public class PhoneCheck extends LatteDelegate {




    @BindView(R2.id.edit_sign_up_checkword)
    TextInputEditText mCheck = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        }

    private boolean checkForm() {
        final String check = mCheck.getText().toString();
        boolean isPass = true;
        if (check.isEmpty()) {
            mCheck.setError("请填写验证码");
            isPass = false;
        } else {
            mCheck.setError(null);
        }
        return isPass;
    }



   @OnClick(R2.id.phone_check_btn)
    void onClickPostCheck() {
            UserManage userManage = new UserManage();
            AVUser user = new AVUser();
            userManage.getUser(user);
            //请求发送手机验证码
            AVUser.requestMobilePhoneVerifyInBackground(user.getMobilePhoneNumber(), new RequestMobileCodeCallback() {
                @Override
                public void done(AVException e) {
                    if(e == null){
                        // 调用成功
                    } else {
                        Log.d("SMS", "Send failed!");
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    @OnClick(R2.id.check_finish)
    void onClickCheck() {
        if (checkForm()) {

            final String checkword = mCheck.getText().toString();
            AVUser.verifyMobilePhoneInBackground(checkword, new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        // 验证成功启动登录页面
                        getSupportDelegate().start(new SignInDelegate());
                        Toast.makeText(getActivity(), "验证成功(*≧∪≦)", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("SMS", "Verified failed!");
                    }
                }
            });
        }

    }
    @OnClick(R2.id.dirc_signin)
    void onClickSignIn() {
        getSupportDelegate().start(new SignInDelegate());
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_phonecheck;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
