package com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.owner_connection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.OnClick;

/**
 * Created by Rhapsody on 2018/3/20.
 */

public class OwnerConnectionDelegate extends LatteDelegate {


    @OnClick(R2.id.owner1_info)
    void onClick1() {
        getSupportDelegate().start(new OwnerInfoDelegate1());
    }

    @OnClick(R2.id.owner2_info)
    void onClick2() {
        getSupportDelegate().start(new OwnerInfoDelegate2());
    }

    @OnClick(R2.id.owner3_info)
    void onClick3() {
        getSupportDelegate().start(new OwnerInfoDelegate3());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate2_owner_info_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
