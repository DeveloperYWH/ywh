package com.zuimeng.hughfowl.latee.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
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
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Rhapsody
 */

public class AddressAddDelegate extends LatteDelegate {

    @BindView(R2.id.info1)
    TextView mNameText1 = null;
    @BindView(R2.id.info2)
    TextView mNameText2 = null;
    @BindView(R2.id.info3)
    TextView mNameText3 = null;
    @BindView(R2.id.btn_info_submit)
    AppCompatButton mNameBtn = null;

    @OnClick(R2.id.btn_info_submit)
    void onClickSubmit() {
        final AVQuery<AVObject> query_name = new AVQuery<>("User_address");
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
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("user_address");
                if (marray !=null) {
                    final int id = marray.size() + 1;
                    final JSONObject sizeData = new JSONObject();
                    JSONObject item = new JSONObject();
                    item.put("name:", mNameText1.getText());
                    item.put("phone", mNameText2.getText());
                    item.put("address", mNameText3.getText());
                    item.put("id", id);
                    item.put("default", "false");
                    sizeData.putAll(item);
                    marray.add(sizeData);
                    avObject.put("user_address", marray);
                    avObject.saveInBackground();
                    getSupportDelegate().start(new AddressDelegate(),2);
                    LatteLoader.stopLoading();
                }
                else {
                    Toast.makeText(getContext(),"添加失败",Toast.LENGTH_LONG).show();
                }
            }
        });
        Toast.makeText(this.getContext(), "添加成功！", Toast.LENGTH_LONG).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_addressadd;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }
}
