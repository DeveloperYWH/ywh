package com.zuimeng.hughfowl.latee.ec.main.explorer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.explorer.ask.AskListDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.dress.CreateDressUpDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.dress.DressListDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.CreateMomentsDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.MomentsDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/10.
 */

public class ExplorerDelegate extends BottomItemDelegate {

    @BindView(R2.id.exp_vp)
    ViewPager viewPager = null;
    @BindView(R2.id.exp_nts)
    NavigationTabStrip topbar = null;

    private List<LatteDelegate> latteDelegates = new ArrayList<>();



    @Override
    public Object setLayout() {
        return R.layout.delegate_explorer;
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
        latteDelegates.add(new AskListDelegate());
        latteDelegates.add(new DressListDelegate());
        latteDelegates.add(new MomentsDelegate());

        viewPager.setAdapter(new PageAdapter(getFragmentManager(),latteDelegates));

        topbar.setViewPager(viewPager,1);
        topbar.setTitleSize(50);

    }

    @OnClick(R2.id.create_moments)
    void OnClickCreateMoments(){
        getParentDelegate().getSupportDelegate().start(new CreateMomentsDelegate());
    }

    @OnClick(R2.id.create_dress)
    void OnClickCreateDress(){
        getParentDelegate().getSupportDelegate().start(new CreateDressUpDelegate());
    }




}
