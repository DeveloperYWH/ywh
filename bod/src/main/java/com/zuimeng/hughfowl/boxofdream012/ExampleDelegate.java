package com.zuimeng.hughfowl.boxofdream012;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

/**
 * Created by hughfowl on 2017/8/23.
 */

public class ExampleDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
