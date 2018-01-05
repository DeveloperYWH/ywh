package com.zuimeng.hughfowl.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.zuimeng.hughfowl.latte.app.Latte;

/**
 * Created by hughfowl on 2018/1/5.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout refresh_layout) {
        this.REFRESH_LAYOUT = refresh_layout;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
                //下一行放入请求成功回调
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 1000);
    }


    @Override
    public void onRefresh() {
        refresh();
    }
}