package com.zuimeng.hughfowl.latee.ec.main.personal.settings;

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
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.PersonalDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.profile.UserProfileDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Rhapsody
 */

public class SizeDelegate extends LatteDelegate {

    @BindView(R2.id.size1)
    TextView mNameText1 = null;
    @BindView(R2.id.size2)
    TextView mNameText2 = null;
    @BindView(R2.id.size3)
    TextView mNameText3 = null;
    @BindView(R2.id.size4)
    TextView mNameText4=null;
    @BindView(R2.id.size5)
    TextView mNameText5=null;
    @BindView(R2.id.btn_size_submit)
    AppCompatButton mNameBtn = null;

    @OnClick(R2.id.btn_size_submit)
    void onClickSubmit() {
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
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
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("user_size");
                final  JSONObject sizeData = marray.getJSONObject(0);
                sizeData.put("my_size","胸围:"+mNameText1.getText()+"\n"+"腰围:"+mNameText2.getText()+"\n"+"臀围:"+mNameText3.getText()+"\n"+"身高:"+mNameText4.getText()+"\n"+"鞋码:"+mNameText5.getText()+"\n");
                marray.set(0,sizeData);
                avObject.put("user_size",marray);
                avObject.saveInBackground();
                getSupportDelegate().replaceFragment(new EcBottomDelegate(),false);
                LatteLoader.stopLoading();
            }
        });
        Toast.makeText(this.getContext(),"添加成功！",Toast.LENGTH_LONG).show();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_size;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
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
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("user_size");
                final  JSONObject sizeData = marray.getJSONObject(0);
                final String size=sizeData.getString("my_size");
                if(size.length()>20) {
                    String[] parts = size.split("\n");
                    String[] data;
                    for (int i = 0; i < parts.length; i++) {
                        data = parts[i].split(":");
                        switch (i) {
                            case 0:
                                if(data.length!=1)
                                mNameText1.setText(data[1]);
                                else
                                    mNameText1.setText("");
                                break;
                            case 1:
                                if(data.length!=1)
                                mNameText2.setText(data[1]);
                                else
                                    mNameText2.setText("");
                                break;
                            case 2:
                                if(data.length!=1)
                                mNameText3.setText(data[1]);
                                else
                                    mNameText3.setText("");
                                break;
                            case 3:
                                if(data.length!=1)
                                mNameText4.setText(data[1]);
                                else
                                    mNameText4.setText("");
                                break;
                            case 4:
                                if(data.length!=1)
                                mNameText5.setText(data[1]);
                                else
                                    mNameText5.setText("");
                                break;
                        }
                    }
                }
                LatteLoader.stopLoading();
            }
        });
    }
}
