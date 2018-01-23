package com.zuimeng.hughfowl.latee.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

/**
 * Created by hughfowl on 2018/1/24.
 */

public class AboutDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
          //网络请求 关于梦之匣
    }
}
