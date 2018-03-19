package com.zuimeng.hughfowl.latee.ec.main.explorer;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.library.FocusResizeScrollListener;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.explorer.dress.CreateDressUpDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.dress.DressListDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.CreateMomentsDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.MomentsDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
