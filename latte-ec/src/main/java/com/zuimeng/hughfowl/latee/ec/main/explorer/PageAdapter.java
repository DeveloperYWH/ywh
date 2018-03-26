package com.zuimeng.hughfowl.latee.ec.main.explorer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hughfowl on 2018/3/23.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private List<LatteDelegate> mlatteDelegates;

    public PageAdapter(FragmentManager fm, List<LatteDelegate> latteDelegates) {
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
