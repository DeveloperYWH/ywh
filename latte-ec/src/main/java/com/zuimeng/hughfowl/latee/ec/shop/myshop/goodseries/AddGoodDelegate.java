package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries.add_goods.AddNowGoodDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries.add_goods.AddPreGoodDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/24.
 */

public class AddGoodDelegate extends LatteDelegate {

    private String mSeriesId = "";

    @Override
    public Object setLayout() {
        return R.layout.delegate2_add_series_goods;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @OnClick(R2.id.add_good_pre)
    void OnClickAddPre(){
        AddPreGoodDelegate addPreGoodDelegate = new AddPreGoodDelegate();
        addPreGoodDelegate.setSeriesId(mSeriesId);
        getSupportDelegate().start(addPreGoodDelegate);
    }

    @OnClick(R2.id.add_good_now)
    void OnClickAddNow(){
        AddNowGoodDelegate addNowGoodDelegate = new AddNowGoodDelegate();
        addNowGoodDelegate.setSeriesId(mSeriesId);
        getSupportDelegate().start(addNowGoodDelegate);
    }

    public void setSeriesId(String seriesId){
        mSeriesId = seriesId;
    }

}
