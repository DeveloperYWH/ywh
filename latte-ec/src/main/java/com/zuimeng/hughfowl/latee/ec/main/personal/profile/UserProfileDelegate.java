package com.zuimeng.hughfowl.latee.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.database.UserProfile;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListAdapter;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListItemType;
import com.zuimeng.hughfowl.latee.ec.main.personal.settings.NameDelegate;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Rhapsody
 */

public class UserProfileDelegate extends LatteDelegate {

    private String avatar_imageUrl = null;

    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {


        final AVQuery<AVObject> query = new AVQuery<>("User_avater");
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

                avatar_imageUrl = list.get(0).getAVFile("image").getUrl();

                final ListBean image = new ListBean.Builder()
                        .setItemType(ListItemType.ITEM_AVATAR)
                        .setId(1)
                        .setImageUrl(avatar_imageUrl)
                        .build();
                final AVQuery<AVObject> info_query = new AVQuery<>("User_info");
                LatteLoader.showLoading(getContext());
                info_query.whereEqualTo("user_id",
                        String.valueOf(DatabaseManager
                                .getInstance()
                                .getDao()
                                .queryBuilder()
                                .listLazy()
                                .get(0)
                                .getUserId()));
                info_query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {

                        final ListBean name = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(2)
                                .setText("姓名")
                                .setDelegate(new NameDelegate())
                                .setValue(list.get(0).getString("user_name"))
                                .build();
                        final ListBean gender = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(3)
                                .setText("性别")
                                .setValue(list.get(0).getString("user_gender"))
                                .build();

                        final ListBean birth = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setId(4)
                                .setText("生日")
                                .setValue("未设置生日")
                                .build();

                        final List<ListBean> data = new ArrayList<>();

                        data.add(image);
                        data.add(name);
                        data.add(gender);
                        data.add(birth);

                        //设置RecyclerView
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final ListAdapter adapter = new ListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(UserProfileDelegate.this));
                        LatteLoader.stopLoading();
                    }
                });



            }
        });


    }
}
