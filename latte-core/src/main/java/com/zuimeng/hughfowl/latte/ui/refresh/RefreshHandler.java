package com.zuimeng.hughfowl.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hughfowl on 2018/1/5.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN ;
    private final RecyclerView RECYCLERVIEW ;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private List<AVObject> AVList = new ArrayList<>();

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                          RecyclerView recyclerView,
                          DataConverter converter,
                          PagingBean bean
                          )
    {
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;

        this.REFRESH_LAYOUT = swipeRefreshLayout;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout,
                                  recyclerView,
                                  converter,
                                  new PagingBean());
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

    public void firstPage() {
        BEAN.setDelayed(1000);
        AVList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Index_Datas");
        avQuery.whereGreaterThanOrEqualTo("goods_Id",0);
        avQuery.countInBackground(new CountCallback() {
            @Override
            public void done(final int i, AVException e) {
                if (e == null) {
                    //Log.d("datalist",String.valueOf(i));
                    // 查询成功，输出计数
                    BEAN.setTotal(i);
                }
                else {
                    // 查询失败
                }
            }

        });
        AVQuery<AVObject> avQuery_1 = new AVQuery<>("Index_Datas");
        avQuery_1.whereGreaterThanOrEqualTo("goods_Id",0);
        avQuery_1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    AVList.addAll(list);
                    BEAN.setPageSize(6);
                    //Log.d("datalist",String.valueOf(list.size()));
                    //Log.d("datalist",String.valueOf(AVList.size()));
                    //设置Adapter
                    mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setList(AVList));
                    mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                    RECYCLERVIEW.setAdapter(mAdapter);
                    BEAN.addIndex();


                } else {
                    e.printStackTrace();
                }




            }
        });









    }


    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
