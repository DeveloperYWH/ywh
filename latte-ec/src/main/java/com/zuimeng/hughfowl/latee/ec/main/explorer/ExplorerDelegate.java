package com.zuimeng.hughfowl.latee.ec.main.explorer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.explorer.dress.CreateDressUpDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.dress.DressListDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.CreateMomentsDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.MomentsDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;

import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/10.
 */

public class ExplorerDelegate extends BottomItemDelegate {


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
        final DressListDelegate listDelegate = new DressListDelegate();
        getSupportDelegate().loadRootFragment(R.id.exp_list_content, listDelegate);

    }

    @OnClick(R2.id.exp_mom_switch)
    void OnClickSwitchMoments(){
        getSupportDelegate().replaceFragment(new MomentsDelegate(),true);
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
