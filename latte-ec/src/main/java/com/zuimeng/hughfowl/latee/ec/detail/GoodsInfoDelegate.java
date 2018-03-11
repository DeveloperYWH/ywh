package com.zuimeng.hughfowl.latee.ec.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

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
 * Created by ywh on 2018/1/22.
 */

public class GoodsInfoDelegate extends LatteDelegate {

    @BindView(R2.id.tv_goods_info_title)
    AppCompatTextView mGoodsInfoTitle = null;
    @BindView(R2.id.tv_goods_info_desc)
    AppCompatTextView mGoodsInfoDesc = null;
    @BindView(R2.id.tv_goods_info_price)
    AppCompatTextView mGoodsInfoPrice = null;
    @BindView(R2.id.sale_size)
    AppCompatTextView mSaleSize=null;
    @BindView(R2.id.plus)
    AppCompatButton mplus=null;
    @BindView(R2.id.minus)
    AppCompatButton mminus=null;
    @BindView(R2.id.constant_size)
    AppCompatTextView conSize=null;
    @BindView(R2.id.my_size)
    AppCompatTextView userSize=null;

    char mSize='M';
    @OnClick(R2.id.plus)
    void onClickPlus() {
        LatteLoader.showLoading(getContext());
        String salesize;
        switch (mSize)
        {
            case 'M':
                salesize = mData.getString("size_L");
                mSize='L';
                conSize.setText("L");
                mSaleSize.setText(salesize);
                LatteLoader.stopLoading();
                break;
            case 'S':
                salesize = mData.getString("size_M");
                mSize='M';
                conSize.setText("M");
                mSaleSize.setText(salesize);
                LatteLoader.stopLoading();
                break;
            default:
                LatteLoader.stopLoading();
                break;
        }
    }
    @OnClick(R2.id.minus)
    void onClickMinus() {
        LatteLoader.showLoading(getContext());
        String salesize;
        switch (mSize)
        {
            case 'M':
                salesize = mData.getString("size_S");
                mSize='S';
                conSize.setText("S");
                mSaleSize.setText(salesize);
                LatteLoader.stopLoading();
                break;
            case 'L':
                salesize = mData.getString("size_M");
                mSize='M';
                conSize.setText("M");
                mSaleSize.setText(salesize);
                LatteLoader.stopLoading();
                break;
            default:
                LatteLoader.stopLoading();
                break;
        }
    }

    private static final String ARG_GOODS_DATA = "ARG_GOODS_DATA";
    private JSONObject mData = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_info;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        final String goodsData = args.getString(ARG_GOODS_DATA);
        mData = JSON.parseObject(goodsData);
    }

    public static GoodsInfoDelegate create(String goodsInfo) {
        final Bundle args = new Bundle();
        args.putString(ARG_GOODS_DATA, goodsInfo);
        final GoodsInfoDelegate delegate = new GoodsInfoDelegate();
        delegate.setArguments(args);
        return delegate;
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
                final JSONObject goodData = marray.getJSONObject(0);
                String usersize=goodData.getString("my_size");
                Log.d("length",String.valueOf(usersize.length()));
                if (usersize.length()==20)
                    userSize.setText("快去添加你的尺码吧！");
                else
                    userSize.setText(usersize);
                LatteLoader.stopLoading();
            }
        });

        final String name = mData.getString("name");
        final String desc = mData.getString("des");
        final double price = mData.getDouble("price");
        final String salesize = mData.getString("size_M");
        mGoodsInfoTitle.setText(name);
        mGoodsInfoDesc.setText(desc);
        mGoodsInfoPrice.setText(String.valueOf(price));
        mSaleSize.setText(salesize);
        mSize='M';
    }
}


