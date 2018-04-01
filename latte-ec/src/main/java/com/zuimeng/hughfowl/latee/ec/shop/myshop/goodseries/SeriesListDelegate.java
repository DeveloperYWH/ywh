package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.BindView;
import butterknife.OnClick;


public class SeriesListDelegate extends LatteDelegate {

    @BindView(R2.id.rv_series_list)
    RecyclerView series_list = null;

    @OnClick(R2.id.icon_series_add)
    void OnClickAdd(){
        getSupportDelegate().start(new CreateGoodsSeriesDelegate());
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_series_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
