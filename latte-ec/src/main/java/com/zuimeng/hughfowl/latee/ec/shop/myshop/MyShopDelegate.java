package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.shop.BottomItemShopDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.create_shop.ShopNoDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.customer_service.CustomerServiceDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries.SeriesListDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.profile.ShopProfileDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by hughfowl on 2018/1/28.
 */

public class MyShopDelegate extends BottomItemShopDelegate {



    Number user_right = 0;

    @BindView(R2.id.img_user_avatar_shop)
    CircleImageView mCircleImageView = null;
    @BindView(R2.id.shop_name_text)
    TextView mTextView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate2_my_shop;
    }

    @OnClick(R2.id.img_user_avatar_shop)
    void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new ShopProfileDelegate());
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {

        //获取头像
        final AVQuery<AVObject> logo_query = new AVQuery<>("Shop_Logo");
        LatteLoader.showLoading(getContext());
        logo_query.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        logo_query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, AVException e) {
                URL url;
                try {
                    url = new URL((list.get(0).getAVFile("image").getUrl()));
                    Glide.with(rootView)
                            .load(url)
                            .into(mCircleImageView);
                    LatteLoader.stopLoading();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //获取用户名
        final AVQuery<AVObject> query_name = new AVQuery<>("Shop_Info");
        LatteLoader.showLoading(getContext());
        query_name.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        query_name.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                mTextView.setText(list.get(0).getString("shop_name").toCharArray(),
                        0, list.get(0).getString("shop_name").toCharArray().length);
                LatteLoader.stopLoading();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
        LatteLoader.showLoading(getContext());
        avQuery.whereEqualTo("mobilePhoneNumber",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        avQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                user_right = list.get(0).getNumber("user_type");
                if ((int) user_right == 2) {
                    final ShopNoDelegate noShop = new ShopNoDelegate();
                    getSupportDelegate().loadRootFragment(R.id.shop_list_content, noShop);
                    LatteLoader.stopLoading();
                } else if((int) user_right == 3){
                    getSupportDelegate().loadRootFragment(R.id.shop_list_content, new AddGoodSeriesDelegate());
                }
                else
                {
                    final ShopDisplayDelegate listDelegate = new ShopDisplayDelegate();
                    getSupportDelegate().loadRootFragment(R.id.shop_list_content, listDelegate);
                    LatteLoader.stopLoading();
                }
            }
        });
    }

    @OnClick(R2.id.shop_chat)
    void OnClickChat() {
        getParentDelegate().getSupportDelegate().start(new CustomerServiceDelegate());
    }
    @OnClick(R2.id.shop_series)
    void OnClickSeries(){
        getParentDelegate().getSupportDelegate().start(new SeriesListDelegate());
    }
}


