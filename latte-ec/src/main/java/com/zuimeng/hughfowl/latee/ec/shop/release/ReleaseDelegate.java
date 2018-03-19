package com.zuimeng.hughfowl.latee.ec.shop.release;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.shop.BottomItemShopDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;

/**
 * Created by hughfowl on 2018/1/28.
 */

public class ReleaseDelegate extends BottomItemShopDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_release;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
