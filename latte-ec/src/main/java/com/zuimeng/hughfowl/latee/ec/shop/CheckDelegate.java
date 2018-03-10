package com.zuimeng.hughfowl.latee.ec.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

/**
 * Created by hughfowl on 2018/3/10.
 */

public class CheckDelegate extends LatteDelegate {


    Number user_right = 0;

    @Override
    public Object setLayout() {
        return R.layout.delegate_check_right;
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
                user_right = list.get(0).getNumber("user_type");
                if ((int)user_right == 1){
                    Toast.makeText(getContext(), "店铺申请未开放" , Toast.LENGTH_SHORT).show();
                    LatteLoader.stopLoading();
                    getSupportDelegate().replaceFragment(new EcBottomDelegate(),false);
                }
                else {
                    LatteLoader.stopLoading();
                    getSupportDelegate().replaceFragment(new ShopBottomDelegate(),false);
                }
            }
        });


    }
}
