package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.detail.GoodsInfoDelegate;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.widget.AutoPhotoLayout;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;
import com.zuimeng.hughfowl.latte.util.callback.IGlobalCallback;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Line;

/**
 * Created by hughfowl on 2018/1/22.
 */

public class CreateMomentsDelegate extends LatteDelegate {
    
    @BindView(R2.id.dress_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;
    @BindView(R2.id.content)
    AppCompatEditText content=null;
    @BindView(R2.id.btn_info_submit)
    AppCompatButton submit=null;
    @BindView(R2.id.goods)
    android.support.v7.widget.LinearLayoutCompat goods=null;
    @BindView(R2.id.tv_item_shop_cart_title)
    AppCompatTextView name=null;
    @BindView(R2.id.tv_item_shop_cart_desc)
    AppCompatTextView des=null;
    @BindView(R2.id.tv_item_shop_cart_price)
    AppCompatTextView price=null;
    @BindView(R2.id.image_item_shop_cart)
    AppCompatImageView thumbpic=null;

    int mPosition = 0;
    int GoodsId=0;
    List<Uri> uri=new ArrayList();
    private List<AVObject> AVList = new ArrayList<>();

    @OnClick(R2.id.delete)
    void onDelete(){
        goods.setVisibility(View.GONE);
    }

    @OnClick(R2.id.btn_info_submit)
    void onClickSubmit()  {
        LatteLoader.showLoading(getContext());
        final List<String> newurl=new ArrayList();
        try {
            for (int i=0;i<uri.size();i++) {
                final AVFile file = AVFile.withAbsoluteLocalPath("moments.jpg", uri.get(i).getPath());
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        Log.d("whatiget", file.getUrl());//返回一个唯一的 Url 地址
                        newurl.add(file.getUrl());
                        if(newurl.size()==uri.size())
                        {
                            final AVQuery<AVObject> query_name = new AVQuery<>("User_moments");
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
                                    final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("content");
                                    final String uid=JSON.parseObject(Jdata).getString("user_id");
                                    final  JSONObject sizeData=new JSONObject();
                                    JSONObject item = new JSONObject();
                                    item.put("content", content.getText());
                                    item.put("id",uid+marray.size());
                                    for(int i=0;i<newurl.size();i++)
                                    {
                                        item.put("thumb"+i,newurl.get(i));
                                    }
                                    sizeData.putAll(item);
                                    marray.add(sizeData);
                                    avObject.put("content",marray);
                                    avObject.saveInBackground();
                                    final AVQuery<AVObject> query_name1 = new AVQuery<>("User_comments");
                                    query_name1.whereEqualTo("moments_id",uid+marray.size());
                                    if (list.size()==0)
                                    {
                                        AVObject newMoments=new AVObject("User_comments");
                                        newMoments.put("moments_id",uid+marray.size());
                                        newMoments.saveInBackground();
                                    }
                                    getSupportDelegate().start(new EcBottomDelegate());
                                    LatteLoader.stopLoading();
                                    Toast.makeText(getContext(),"上传成功！",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"上传失败！",Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_create_moments;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        mAutoPhotoLayout.onCropTarget(args);
                        uri.add(args);
                    }
                });
        if (GoodsId!=0)
        {
            goods.setVisibility(View.VISIBLE);
            final AVQuery<AVObject> query = new AVQuery<>("goodss_detail");
            query.whereEqualTo("id", GoodsId);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {

                    AVList.addAll(list);
                    initGoodsInfo();
                }

            });
        }

    }

    public void setGoodsId(int goodsId)
    {
        GoodsId=goodsId;
    }
    private void initGoodsInfo() {
        final AVObject object = AVList.get(0);
        final String Jdata = object.toJSONObject().toString();
        final JSONArray array = JSON.parseObject(Jdata).getJSONArray("data");
        final JSONObject goodData = array.getJSONObject(0);
        final String title=goodData.getString("name");
        final String desc=goodData.getString("des");
        final String money=goodData.getString("price");
        final JSONArray thumb = JSON.parseObject(Jdata).getJSONArray("thumb");
        final JSONObject goodthumb = thumb.getJSONObject(0);
        final String pic=goodthumb.getString("goods_thumb");
        name.setText(title);
        des.setText(desc);
        price.setText(money);
        Glide.with(getContext())
                .load(pic)
                .into(thumbpic);
    }
}
