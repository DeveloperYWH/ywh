package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.widget.AutoPhotoLayout;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;
import com.zuimeng.hughfowl.latte.util.callback.IGlobalCallback;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/24.
 */

public class CreateGoodsSeriesDelegate extends LatteDelegate {


    @BindView(R2.id.edit_series_name)
    TextInputEditText mSeriesName = null;
    @BindView(R2.id.create_series_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;
    AVFile mAvFile = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate2_create_good_series;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mAutoPhotoLayout.setMaxNum(1);
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) throws FileNotFoundException {
                        mAutoPhotoLayout.onCropTarget(args);
                        mAvFile = AVFile.withAbsoluteLocalPath("seriesImg", args.getPath());
                    }
                });
    }


    @OnClick(R2.id.create_series_btn)
    void OnClickCreateSeries() {

        String userId = String.valueOf(DatabaseManager
                .getInstance()
                .getDao()
                .queryBuilder()
                .listLazy()
                .get(0)
                .getUserId());


        AVObject series = AVObject.create("Series");
        series.put("user_id", userId);

        if (mSeriesName.length() == 0 || mAutoPhotoLayout.getIsDeleted())
            mSeriesName.setError("请输入系列名称以及添加图片");

        else {
            LatteLoader.showLoading(getContext());

            final AVQuery<AVObject> query = new AVQuery<>("Series");
            query.whereEqualTo("user_id", userId);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {

                    boolean isRepeat = false;
                    for (int i = 0; i < list.size(); i++) {
                        AVObject data = list.get(i);
                        isRepeat = mSeriesName.getText().toString().equals(data.getString("name"));
                        if (isRepeat)
                            break;
                    }
                    if (!isRepeat) {
                        series.put("image", mAvFile);
                        series.put("name", mSeriesName.getText());
                        series.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {

                                final AVQuery<AVObject> query_name = new AVQuery<>("Shop_series");
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
                                        avObject.put("good_series", marray);
                                        avObject.saveInBackground();

                                        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
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
                                                list.get(0).put("user_type", 4);
                                                list.get(0).saveInBackground();
                                            }
                                        });
                                        getSupportDelegate().start(new SeriesListDelegate());
                                    }
                                });
                            }
                        });
                    } else mSeriesName.setError("商品系列名称不能重复");
                    LatteLoader.stopLoading();

                }
            });
        }
    }
}
