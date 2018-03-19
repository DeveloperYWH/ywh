package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

import java.util.List;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/3/19.
 */

public class ShopDisplayDelegate extends LatteDelegate {


    private List<ShopSectionBean> mData = null;
    private ShopSectionDataConverter mShopSectionDataConverter = null;

    @BindView(R2.id.shop_display)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_display_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData();

    }

    public void initData() {
        final AVQuery<AVObject> query = new AVQuery<>("shop_display");
        LatteLoader.showLoading(getContext());
        query.whereEqualTo("userId",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0).getUserId()));

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    LatteLoader.stopLoading();
                    mShopSectionDataConverter = new ShopSectionDataConverter().setList(list);

                    mData = mShopSectionDataConverter.convert();
                    final ShopDisplayAdapter shopDisplayAdapter =
                            new ShopDisplayAdapter(R.layout.item2_myshop_content,
                                    R.layout.item2_shop_section_header,mData);
                    if(mRecyclerView!=null)
                        mRecyclerView.setAdapter(shopDisplayAdapter);
                }
            }
        });
    }

}
