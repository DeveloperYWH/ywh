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
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/1/6.
 */

public class PhoneSignInDelegate extends LatteDelegate {


    @BindView(R2.id.edit_sign_in_phone)
    TextInputEditText mPhone = null;
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




    private boolean checkForm() {
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();


        boolean isPass = true;

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }


    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {

            final String userphone = mPhone.getText().toString();
            final String password = mPassword.getText().toString();
            AVUser.loginByMobilePhoneNumberInBackground(userphone, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        Log.v("Sign in by phone", "OK!");//Dev
                        //Sign in callback here.
                        SignHandler.onSignIn( avUser , mISignListener);
                    } else {
                        Log.v("Sign in by phone", "Fail!");//Dev
                        //Sign in exception handle here.
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            getSupportDelegate().startWithPop(new EcBottomDelegate());
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_phone_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
