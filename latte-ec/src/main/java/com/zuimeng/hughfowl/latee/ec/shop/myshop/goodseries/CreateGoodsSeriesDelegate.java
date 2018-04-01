package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
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
 * Created by hughfowl on 2018/3/24.
 */

public class CreateGoodsSeriesDelegate extends LatteDelegate {


    @BindView(R2.id.edit_series_name)
    TextInputEditText mseries_name = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate2_create_good_series;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
    @OnClick(R2.id.create_series_btn)
    void OnClickCreateSeries(){

        String userId = String.valueOf(DatabaseManager
                .getInstance()
                .getDao()
                .queryBuilder()
                .listLazy()
                .get(0)
                .getUserId());

        AVObject series = AVObject.create("Series");
        series.put("user_id", userId);
        series.put("name",mseries_name.getText());
        series.saveInBackground();

        final AVQuery<AVObject> query_name = new AVQuery<>("Shop_series");
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

                final AVObject avObject = list.get(0);
                final String Jdata = avObject.toJSONObject().toString();
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("good_series");
                marray.add(series.getObjectId());
                avObject.put("good_series",marray);
                avObject.saveInBackground();


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
                        list.get(0).put("user_type",4);
                        list.get(0).saveInBackground();
                    }
                });
                getSupportDelegate().start(new AddGoodDelegate());
                LatteLoader.stopLoading();
            }
        });

    }
}
