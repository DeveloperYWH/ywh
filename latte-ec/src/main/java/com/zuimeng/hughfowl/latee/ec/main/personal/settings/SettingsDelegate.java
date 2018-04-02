package com.zuimeng.hughfowl.latee.ec.main.personal.settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.personal.address.AddressDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListAdapter;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListItemType;
import com.zuimeng.hughfowl.latee.ec.sign.LogOutDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/1/23.
 */

public class SettingsDelegate extends LatteDelegate {

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(getContext(), "打开关注推送", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), "关闭关注推送", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setText("消息推送")
                .build();

        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new AboutDelegate())
                .setText("关于")
                .build();

        final ListBean logout = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setDelegate(new LogOutDelegate())
                .setText("注销")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);
        data.add(logout);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));

    }
}
