package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/24.
 */

public class CreateGoodsSeriesDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate2_create_good_series;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
    @OnClick(R2.id.create_series_btn)
    void OnClickCreateSeries(){
        getSupportDelegate().start(new AddGoodDelegate());
    }
}
