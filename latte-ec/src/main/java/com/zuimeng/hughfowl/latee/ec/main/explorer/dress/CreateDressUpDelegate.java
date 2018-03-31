package com.zuimeng.hughfowl.latee.ec.main.explorer.dress;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
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
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
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

/**
 * Created by hughfowl on 2018/1/22.
 */

public class CreateDressUpDelegate extends LatteDelegate {

    @BindView(R2.id.title)
    AppCompatEditText title=null;
    @BindView(R2.id.dress_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;
    @BindView(R2.id.content)
    AppCompatEditText content=null;
    @BindView(R2.id.btn_info_submit)
    AppCompatButton submit=null;
    int mPosition = 0;
    List<Uri> uri=new ArrayList();

    public void getPosition(int position) {
        mPosition = position;
        Log.d("aaa",String.valueOf(mPosition));
    }


    @OnClick(R2.id.btn_info_submit)
    void onClickSubmit() {
        LatteLoader.showLoading(getContext());
        final List<String> newurl = new ArrayList();
        try {
            for (int i=0;i<uri.size();i++) {
                final AVFile file = AVFile.withAbsoluteLocalPath("dressup.jpg", uri.get(i).getPath());
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        Log.d("whatiget", file.getUrl());//返回一个唯一的 Url 地址
                        newurl.add(file.getUrl());
                        if(newurl.size()==uri.size())
                        {
                            final AVQuery<AVObject> query_name = new AVQuery<>("User_dressup");
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
                                    final  JSONObject sizeData=new JSONObject();
                                    JSONObject item = new JSONObject();
                                    item.put("title",title.getText());
                                    item.put("content", content.getText());
                                    for(int i=0;i<newurl.size();i++)
                                    {
                                        item.put("thumb"+i,newurl.get(i));
                                    }
                                    sizeData.putAll(item);
                                    marray.add(sizeData);
                                    avObject.put("content",marray);
                                    avObject.saveInBackground();
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
        return R.layout.delegate_create_dressup;
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

    }
}
