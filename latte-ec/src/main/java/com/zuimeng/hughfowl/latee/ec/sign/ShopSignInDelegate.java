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
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/1/28.
 */

public class ShopSignInDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_in_phone_shop)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_in_password_shop)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_in_shop)
    void onClickSignIn() {
        if (checkForm()) {

            final String username = mName.getText().toString();
            final String password = mPassword.getText().toString();

            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e == null) {
                                // Log.v("Sign in", "OK!");
                                SignHandler.onSignIn( avUser , mISignListener);

                            } else {
                                Log.v("Sign in", "Fail!");//Dev
                                //Sign in exception handle here.
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

        }
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
        return R.layout.delegate_shop_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
