package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries.add_goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

public class AddPreGoodDelegate extends LatteDelegate{


    private String mseriesId = "";

    @BindView(R2.id.edit_good_title)
    TextInputEditText mgood_title = null;
    @BindView(R2.id.edit_good_channel)
    TextInputEditText mgood_channel = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate2_add_pre_good;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
    @OnClick(R2.id.create_good_btn)
    void OnClickCreateGood(){
        String userId = String.valueOf(DatabaseManager
                .getInstance()
                .getDao()
                .queryBuilder()
                .listLazy()
                .get(0)
                .getUserId());

        AVObject goods = AVObject.create("Goods");
        goods.put("user_id", userId);
        goods.put("title",mgood_title.getText());
        goods.put("channel",mgood_channel.getText());
        goods.saveInBackground();

        final AVQuery<AVObject> query_name = new AVQuery<>("Series");
        LatteLoader.showLoading(getContext());
        query_name.whereEqualTo("objectId",mseriesId);
        query_name.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                final AVObject avObject = list.get(0);
                final String Jdata = avObject.toJSONObject().toString();
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("goods");
                marray.add(goods.getObjectId());
                avObject.put("goods",marray);
                avObject.saveInBackground();
                LatteLoader.stopLoading();
                Toast.makeText(getContext(),"创建成功",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setSeriesId(String seriesId){
        mseriesId = seriesId;
    }
}
