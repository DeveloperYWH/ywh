package com.zuimeng.hughfowl.latee.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.widget.AutoPhotoLayout;
import com.zuimeng.hughfowl.latte.ui.widget.StarLayout;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;
import com.zuimeng.hughfowl.latte.util.callback.IGlobalCallback;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/1/22.
 */

public class OrderCommentDelegate extends LatteDelegate {

    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout = null;
    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;
    @BindView(R2.id.img_order_comment)
    AppCompatImageView mAppCompatImageView = null;
    int mPosition = 0;

    public void getPosition(int position) {
        mPosition = position;
        Log.d("aaa",String.valueOf(mPosition));
    }


    @OnClick(R2.id.top_tv_comment_commit)
    void onClickSubmit() {
        Toast.makeText(getContext(), "评分： " + mStarLayout.getStarCount(), Toast.LENGTH_LONG).show();
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        mAutoPhotoLayout.onCropTarget(args);
                    }
                });

        final AVQuery<AVObject> query = new AVQuery<>("Order_list_test");
        LatteLoader.showLoading(getContext());
        query.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                String orderList = list.get(0).toJSONObject().toString();
                JSONArray orderInfoList = JSON.parseObject(orderList).getJSONArray("order_list");
                for (int i = 0;i<orderInfoList.size();i++){
                    JSONObject orderInfo = orderInfoList.getJSONObject(i);
                    if (i == mPosition){
                        URL url = null;
                        try {
                            url = new URL(orderInfo.get("order_thumb").toString());
                        }catch (IOException exp){
                            exp.printStackTrace();
                        }
                        Glide.with(rootView)
                                .load(url)
                                .into(mAppCompatImageView);
                    }
                }
            }
        });
        LatteLoader.stopLoading();
    }
}
