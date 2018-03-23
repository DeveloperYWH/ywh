package com.zuimeng.hughfowl.latee.ec.shop.myshop.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.shop.profile.ShopProfileDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Rhapsody on 2018/3/23.
 */

public class ShopStyleDelegate extends LatteDelegate {
    @BindView(R2.id.text_shop_style)
    TextView mTextView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_style;
    }

    @OnClick(R2.id.btn_shop_style_submit)
    void onClickSubmit() {
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
                final AVObject avObject = list.get(0);
                avObject.put("shop_style", mTextView.getText());
                avObject.saveInBackground();
                getSupportDelegate().start(new ShopProfileDelegate());
                LatteLoader.stopLoading();
            }
        });
        Toast.makeText(this.getContext(), "修改成功！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
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
                final AVObject avObject = list.get(0);
                String Jdata = avObject.toJSONObject().toString();
                final String marray = JSON.parseObject(Jdata).getString("shop_style");
                mTextView.setText(marray);
                LatteLoader.stopLoading();
            }
        });
    }
}
