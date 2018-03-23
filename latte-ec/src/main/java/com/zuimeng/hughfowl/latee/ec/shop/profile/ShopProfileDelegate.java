package com.zuimeng.hughfowl.latee.ec.shop.profile;

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
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListAdapter;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListItemType;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.ShopGoodsCountsDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.ShopStyleDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.ShopSummaryDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.owner_connection.OwnerConnectionDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.shop_name.EnglishNameDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.shop_name.FullNameDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.shop_name.ShortNameDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Rhapsody on 2018/3/20.
 */

public class ShopProfileDelegate extends LatteDelegate {

    private String avatar_imageUrl = null;

    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final AVQuery<AVObject> query = new AVQuery<>("Shop_Logo");
        LatteLoader.showLoading(getContext());
        query.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                avatar_imageUrl = list.get(0).getAVFile("image").getUrl();

                final ListBean image = new ListBean.Builder()
                        .setItemType(ListItemType.ITEM_AVATAR)
                        .setId(1)
                        .setImageUrl(avatar_imageUrl)
                        .build();
                final AVQuery<AVObject> info_query = new AVQuery<>("Shop_Info");
                LatteLoader.showLoading(getContext());
                info_query.whereEqualTo("user_id",
                        String.valueOf(DatabaseManager
                                .getInstance()
                                .getDao()
                                .queryBuilder()
                                .listLazy()
                                .get(0)
                                .getUserId()));
                info_query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {

                        final ListBean fullName = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(2)
                                .setText("店铺全称")
                                .setDelegate(new FullNameDelegate())
                                .setValue(list.get(0).getString("shop_name"))
                                .build();
                        final ListBean shortName = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(3)
                                .setText("店铺简称")
                                .setDelegate(new ShortNameDelegate())
                                .setValue(list.get(0).getString("shop_name_short"))
                                .build();

                        final ListBean englishName = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(4)
                                .setText("店铺英文名称(可不填)")
                                .setDelegate(new EnglishNameDelegate())
                                .setValue(list.get(0).getString("shop_name_eng"))
                                .build();

                        final ListBean openDate = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(5)
                                .setText("开店日期")
                                .setValue(list.get(0).getString("shop_open_date"))
                                .build();

                        final ListBean ShopStyleDelegate = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(6)
                                .setText("店铺风格")
                                .setDelegate(new ShopStyleDelegate())
                                .setValue(list.get(0).getString("shop_style"))
                                .build();

                        final ListBean ShopGoodsCountsDelegate = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(7)
                                .setText("入驻前已出品的商品款式数量")
                                .setDelegate(new ShopGoodsCountsDelegate())
                                .setValue(list.get(0).getString("shop_goods_item_counts"))
                                .build();

                        final ListBean summaryDelegate = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(8)
                                .setText("店铺简介(可不填)")
                                .setDelegate(new ShopSummaryDelegate())
                                .setValue(list.get(0).getString("shop_summary"))
                                .build();

                        final ListBean ownerConnection = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(9)
                                .setText("店铺负责人联系方式")
                                .setDelegate(new OwnerConnectionDelegate())
                                .setValue(list.get(0).getString("shop_owner_connection"))
                                .build();


                        final List<ListBean> data = new ArrayList<>();

                        data.add(image);
                        data.add(fullName);
                        data.add(shortName);
                        data.add(englishName);
                        data.add(openDate);
                        data.add(ownerConnection);
                        data.add(ShopStyleDelegate);
                        data.add(ShopGoodsCountsDelegate);
                        data.add(summaryDelegate);
                        data.add(ownerConnection);

                        //设置RecyclerView
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final ListAdapter adapter = new ListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new ShopProfileClickListener(ShopProfileDelegate.this));
                        LatteLoader.stopLoading();
                    }
                });

            }
        });
    }
}
