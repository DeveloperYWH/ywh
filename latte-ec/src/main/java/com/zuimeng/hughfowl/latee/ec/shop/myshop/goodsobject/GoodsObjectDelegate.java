package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodsobject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries.AddGoodDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.OnClick;

public class GoodsObjectDelegate extends LatteDelegate {

    @OnClick(R2.id.icon_goods_add)
    void OnClickAdd(){
        getSupportDelegate().start(new AddGoodDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate2_goods_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
