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
import com.avos.avoscloud.SignUpCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.datamanage.UserManage;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2017/9/14.
 */

public class SignUpDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;

        }

    }


    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        //final String check = mCheck.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

       /* if (check.isEmpty()) {
            mCheck.setError("请填写验证码");
            isPass = false;
        } else {
            mCheck.setError(null);
        }*/

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }


    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
//            RestClient.builder()
//                    .url("http://114.67.235.114/RestServer/api/user_profile.php")
//                    .params("name", mName.getText().toString())
//                    .params("email", mEmail.getText().toString())
//                    .params("phone", mPhone.getText().toString())
//                    .params("password", mPassword.getText().toString())
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//                            LatteLogger.json("USER_PROFILE", response);
//                            SignHandler.onSignUp(response, mISignListener);
//                        }
//                    })
//                    .build()
//                    .post();
            final String username = mName.getText().toString();
            final String password = mPassword.getText().toString();
            final String email = mEmail.getText().toString();
            final String phone = mPhone.getText().toString();
            LatteLoader.showLoading(getContext());
            final AVUser user = new AVUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setMobilePhoneNumber(phone);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
//                        Log.v("Sign up", "OK!");//Dev
                        //注册成功状态回调本地状态
                        SignHandler.onSignUp( user , mISignListener);
                        //启动验证页面
                        UserManage userManage = new UserManage();
                        userManage.postUser(user);

                        getSupportDelegate().start(new PhoneCheck());
                    } else {
//                        Log.v("Sign up", "Fail!");//Dev
                        LatteLoader.stopLoading();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }


    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        getSupportDelegate().start(new SignInDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
