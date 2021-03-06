package com.zuimeng.hughfowl.latee.ec.main.personal.order;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * Created by hughfowl on 2018/1/22.
 */

public class OrderListClickListener extends SimpleClickListener {

    private final OrderListAllDelegate DELEGATE;

    public OrderListClickListener(OrderListAllDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderCommentDelegate delegate = new OrderCommentDelegate();
        delegate.getPosition(position);
        DELEGATE.getSupportDelegate().start(delegate);
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
