package com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.shop_name;

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
 * Created by Rhapsody on 2018/3/20.
 */

public class FullNameDelegate extends LatteDelegate {


    @BindView(R2.id.text_shop_full_name)
    TextView mNameText = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate2_shop_full_name;
    }

    @OnClick(R2.id.btn_shop_full_name_submit)
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
                if(mNameText.getText().length()>=2){

                    avObject.put("shop_name",mNameText.getText());
                    avObject.put("mustEditName", true);
                    avObject.saveInBackground();
                    Toast.makeText(getContext(),"修改成功！",Toast.LENGTH_LONG).show();
                    getSupportDelegate().start(new ShopProfileDelegate());
                }
                else {
                    mNameText.setError("最少输入两个字符");
                }
                LatteLoader.stopLoading();
            }
        });
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
                String Jdata=avObject.toJSONObject().toString();
                final String marray = JSON.parseObject(Jdata).getString("shop_name");
                mNameText.setText(marray);
                LatteLoader.stopLoading();
            }
        });
    }
}
