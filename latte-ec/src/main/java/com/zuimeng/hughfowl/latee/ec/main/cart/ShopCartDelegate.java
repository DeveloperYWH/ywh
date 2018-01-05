package com.zuimeng.hughfowl.latee.ec.main.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.net.callback.ISuccess;

import butterknife.BindView;

/**
 * Created by Rhapsody on 2018/1/5.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess{

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    /*
    ***数据转化 （未完成）

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder();
    }
    */
    @Override
    public void onSuccess(String response) {

    }
}
