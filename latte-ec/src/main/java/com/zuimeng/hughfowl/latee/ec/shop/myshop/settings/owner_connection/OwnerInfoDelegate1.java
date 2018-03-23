package com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.owner_connection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

public class OwnerInfoDelegate1 extends LatteDelegate {

    @BindView(R2.id.text_shop_owner1_qq)
    TextView mQqText = null;
    @BindView(R2.id.text_shop_owner1_wx)
    TextView mWxText = null;
    @BindView(R2.id.text_shop_owner1_phone)
    TextView mPhoneText = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate2_owner1_info;
    }

    @OnClick(R2.id.btn_shop_owner1_submit)
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
                final AVObject data = list.get(0);
                final JSONObject ownerInfoObj = new JSONObject();
                final JSONArray ownerInfoArr = new JSONArray();
                ownerInfoObj.put("QQ", mQqText.getText());
                ownerInfoObj.put("WeChat", mWxText.getText());
                ownerInfoObj.put("PhoneNum", mPhoneText.getText());
                ownerInfoArr.add(ownerInfoObj);
                data.put("owner_phone1",ownerInfoArr);
                data.saveInBackground();
                ownerInfoObj.clear();
                ownerInfoArr.clear();
                getSupportDelegate().start(new ShopProfileDelegate(),2);
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
                if (list.get(0).toJSONObject().toString() != null) {
                    String ownerInfoStr = list.get(0).toJSONObject().toString();
                    JSONArray ownerInfoList = JSON.parseObject(ownerInfoStr).getJSONArray("owner_phone1");
                    if (ownerInfoList!=null) {
                        JSONObject ownerInfo = (JSONObject) ownerInfoList.get(0);
                        String qq = ownerInfo.getString("QQ");
                        String weChat = ownerInfo.getString("WeChat");
                        String Phone = ownerInfo.getString("PhoneNum");
                        mQqText.setText(qq);
                        mWxText.setText(weChat);
                        mPhoneText.setText(Phone);
                    }
                    LatteLoader.stopLoading();
                }
            }
        });
    }
}
