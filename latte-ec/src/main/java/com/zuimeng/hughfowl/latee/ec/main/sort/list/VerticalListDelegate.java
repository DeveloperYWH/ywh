package com.zuimeng.hughfowl.latee.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.sort.SortDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.net.RestClient;
import com.zuimeng.hughfowl.latte.net.callback.ISuccess;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ywh on 2018/1/5.
 */

public class VerticalListDelegate extends LatteDelegate {
    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRecyclerView = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        initRecyclerView();
    }
    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        //屏蔽动画效果
        mRecyclerView.setItemAnimator(null);
    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final List<MultipleItemEntity> data =
                new VerticalListDataConverter().convert();
        final SortDelegate delegate = getParentDelegate();
        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, delegate);
        mRecyclerView.setAdapter(adapter);
    }
}
