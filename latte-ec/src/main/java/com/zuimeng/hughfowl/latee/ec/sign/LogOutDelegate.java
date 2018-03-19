package com.zuimeng.hughfowl.latee.ec.sign;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

/**
 * Created by hughfowl on 2018/3/19.
 */

public class LogOutDelegate extends LatteDelegate {

    private ISignListener mISignListener = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_log_out;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVUser.logOut();// 清除缓存用户对象
        AVUser currentUser = AVUser.getCurrentUser();
        DatabaseManager.getInstance().getDao().deleteAll();
        SignHandler.onLogOut(mISignListener);
        getSupportDelegate().replaceFragment(new SignInDelegate(),false);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
