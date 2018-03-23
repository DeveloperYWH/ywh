package com.zuimeng.hughfowl.latee.ec.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/23.
 */

public class CheckDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate2_check_right;
    }

    @OnClick(R2.id.check_shop_button)
    void OnClick(){

        getSupportDelegate().start(new ShopBottomDelegate());
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

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
                Number user_right;
                user_right = list.get(0).getNumber("user_type");
                if ((int)user_right == 1){
                    list.get(0).put("user_type",2);
                    list.get(0).saveInBackground();
                    LatteLoader.stopLoading();
                }
            }
        });
    }
}
