package com.zuimeng.hughfowl.latee.ec.sign;

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
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.datamanage.UserManage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/1/26.
 */

public class PhoneBand extends LatteDelegate {

    @BindView(R2.id.edit_sign_up_checkword_band)
    TextInputEditText mCheck = null;



    private ISignListener mISignListener = null;


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



    @OnClick(R2.id.phone_check_btn_band)
    void onClickPostCheck() {
        UserManage userManage = new UserManage();
        AVUser user = userManage.getUser();
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

    @OnClick(R2.id.check_finish_band)
    void onClickCheck() {
        if (checkForm()) {

            final String checkword = mCheck.getText().toString();
            AVUser.verifyMobilePhoneInBackground(checkword, new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        UserManage userManage = new UserManage();

                        AVUser user = userManage.getUser();
                        final String phone = user.getMobilePhoneNumber();
                        user.setMobilePhoneNumber(phone);
                        // 验证成功启动首页
                        SignHandler.onQQSignIn( mISignListener);
                        getSupportDelegate().start(new EcBottomDelegate());
                        Toast.makeText(getActivity(), "绑定成功(*≧∪≦)", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("SMS", "Verified failed!");
                    }
                }
            });
        }

    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_phoneband;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
