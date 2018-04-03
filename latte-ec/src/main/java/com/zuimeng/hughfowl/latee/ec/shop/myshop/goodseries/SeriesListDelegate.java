package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SeriesListDelegate extends LatteDelegate implements ISeriesItemListener{

    @BindView(R2.id.rv_series_list)
    RecyclerView mRecyclerView = null;

    private SeriesListAdapter mAdapter = null;
    private ISeriesItemListener mSeriesItemListener = null;
    final LinearLayoutManager manager = new LinearLayoutManager(getContext());


    @OnClick(R2.id.icon_series_add)
    void OnClickAdd(){
        getSupportDelegate().start(new CreateGoodsSeriesDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_series_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    public void setCartItemListener(ISeriesItemListener listener) {
        this.mSeriesItemListener = listener;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState){
        super.onLazyInitView(savedInstanceState);

        final AVQuery<AVObject> query = new AVQuery<>("Series");
        LatteLoader.showLoading(getContext());
        query.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0).getUserId()));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                final ArrayList<MultipleItemEntity> data =
                        new SeriesListConverter()
                                .setList(list)
                                .convert();

                mAdapter = new SeriesListAdapter(data);
                mAdapter.setSeriesItemListener(SeriesListDelegate.this);
                if(mRecyclerView != null) {
                    mRecyclerView.setLayoutManager(manager);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.addOnItemTouchListener(new SeriesListClickListener(SeriesListDelegate.this));
                    mAdapter.SetSeriesListDelegate(SeriesListDelegate.this);
                }
                LatteLoader.stopLoading();
            }
        });
    }

    @Override
    public void onItemClick() {

    }
}
