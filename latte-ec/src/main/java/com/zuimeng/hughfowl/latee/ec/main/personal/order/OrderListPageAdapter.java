package com.zuimeng.hughfowl.latee.ec.main.personal.order;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import java.util.List;

public class OrderListPageAdapter extends FragmentPagerAdapter {

    private List<LatteDelegate> mlatteDelegates;

    public OrderListPageAdapter(FragmentManager fm, List<LatteDelegate> latteDelegates) {
        super(fm);
        mlatteDelegates = latteDelegates;
    }

    @Override
    public void destroyItem(final View container, final int position, final Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return mlatteDelegates.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mlatteDelegates.get(position);
    }
}
