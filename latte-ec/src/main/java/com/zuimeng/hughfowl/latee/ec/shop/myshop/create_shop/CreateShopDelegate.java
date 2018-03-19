package com.zuimeng.hughfowl.latee.ec.shop.myshop.create_shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
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
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/17.
 */

public class CreateShopDelegate extends LatteDelegate {

    @BindView(R2.id.edit_shop_name)
    TextInputEditText mShopName = null ;
    @BindView(R2.id.edit_shop_name_short)
    TextInputEditText mShopNameShort = null;
    @BindView(R2.id.edit_shop_name_eng)
    TextInputEditText mShopNameEng = null;

    @OnClick(R2.id.btn_create_shop_next_1)
    void OnClickNext(){
        final String ShopName = mShopName.getText().toString();
        final String ShopNameShort = mShopNameShort.getText().toString();
        final String ShopNameEng = mShopNameEng.getText().toString();

        final AVQuery<AVObject> query = new AVQuery<>("Shop_Info");
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
                list.get(0).put("shop_name",ShopName);
                list.get(0).put("shop_name_short",ShopNameShort);
                list.get(0).put("shop_name_eng",ShopNameEng);
                list.get(0).saveInBackground();
            }
        });
        getSupportDelegate().start(new CreateShopDelegate_2());

    }


    @Override
    public Object setLayout() {
        return R.layout.delegate2_create_shop_1;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
