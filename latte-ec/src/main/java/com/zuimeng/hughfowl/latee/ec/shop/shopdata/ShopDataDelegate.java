package com.zuimeng.hughfowl.latee.ec.shop.shopdata;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.shop.BottomItemShopDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/3/20.
 */

public class ShopDataDelegate extends BottomItemShopDelegate {
    @BindView(R2.id.shop_data_vp)
    ViewPager data_vp = null;
    @BindView(R2.id.shop_data_nts_tab)
    NavigationTabStrip data_nts = null;

    private List<LatteDelegate> latteDelegates = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_data;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        SetUI();
    }

    private void SetUI(){
        latteDelegates.add(new GeneralDataDelegate());
        latteDelegates.add(new GoodDataSelectListDelegate());

        data_vp.setAdapter(new ShopDataPageAdapter(getFragmentManager(),latteDelegates));

        data_nts.setViewPager(data_vp,0);
        data_nts.setTitleSize(30);

    }
}
