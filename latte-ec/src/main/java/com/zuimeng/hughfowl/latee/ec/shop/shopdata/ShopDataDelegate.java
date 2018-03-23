package com.zuimeng.hughfowl.latee.ec.shop.shopdata;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.shop.BottomItemShopDelegate;

/**
 * Created by hughfowl on 2018/3/20.
 */

public class ShopDataDelegate extends BottomItemShopDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_data;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
