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
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        BEAN.setTotal(100);
        //首页计数
//        AVQuery<AVObject> avQuery = new AVQuery<>("Index_Datas");
//        avQuery.whereGreaterThanOrEqualTo("goods_Id",0);
//        avQuery.countInBackground(new CountCallback() {
//            @Override
//            public void done(final int i, AVException e) {
//                if (e == null) {
//                    //Log.d("datalist",String.valueOf(i));
//                    // 查询成功，输出计数
//
//                }
//                else {
//                    // 查询失败
//                }
//            }
//
//        });
        AVQuery<AVObject> avQuery_1 = new AVQuery<>("Index_Datas");
        avQuery_1.whereGreaterThanOrEqualTo("goods_Id",0);
        avQuery_1.findInBackground(new FindCallback<AVObject>() {

            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    AVList.addAll(list);
                    BEAN.setPageSize(6);
                    //设置Adapter
                    mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setList(AVList));
//                    Log.d("fuck","第一次有了"+String.valueOf(mAdapter.getData().size()));
                    mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                    RECYCLERVIEW.setAdapter(mAdapter);
                    BEAN.setCurrentCount(BEAN.getCurrentCount()+list.size());
                    BEAN.addIndex();


                } else {
                    e.printStackTrace();
                }




            }
        } );
    }

    private void paging() {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            Latte.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final AVQuery<AVObject> query = new AVQuery<>("Index_page"+(index+1));
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null){
                                CONVERTER.clearData();
                                int current_size = BEAN.getCurrentCount()+list.size();
                                List<MultipleItemEntity> multipleItemEntity = CONVERTER.setList(list).convert();
                                for (int i = 0;i < current_size;i++){
                                    mAdapter.setData(i,multipleItemEntity.get(i));
                                }
                                //累加数量
                                BEAN.setCurrentCount(mAdapter.getData().size());
                                mAdapter.loadMoreComplete();
                                BEAN.addIndex();
                            }
                        }
                    });
                }
            }, 1000);
        }
    }



    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        paging();
    }
}
