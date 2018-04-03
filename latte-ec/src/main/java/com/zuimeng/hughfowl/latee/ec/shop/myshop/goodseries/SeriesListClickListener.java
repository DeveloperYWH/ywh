package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.goodsobject.GoodsObjectDelegate;

public class SeriesListClickListener  extends SimpleClickListener {

    private final SeriesListDelegate DELEGATE;

    public SeriesListClickListener(SeriesListDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DELEGATE.getSupportDelegate().start(new GoodsObjectDelegate());
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
