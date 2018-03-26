package com.zuimeng.hughfowl.latee.ec.shop.shopdata;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import java.util.List;

/**
 * Created by hughfowl on 2018/3/24.
 */

public class ShopDataPageAdapter extends FragmentPagerAdapter {

    private List<LatteDelegate> mlatteDelegates;

    public ShopDataPageAdapter(FragmentManager fm, List<LatteDelegate> latteDelegates) {
        super(fm);
        mlatteDelegates = latteDelegates;
    }

    @Override
    public Fragment getItem(int position) {
        return mlatteDelegates.get(position);
    }

    @Override
    public int getCount() {
        return mlatteDelegates.size();
    }
}
