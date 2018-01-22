package com.zuimeng.hughfowl.latee.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/1/23.
 */

public class AddressDelegate extends LatteDelegate{

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final AVQuery<AVObject> query = new AVQuery<>("User_address");
        LatteLoader.showLoading(getContext());
        query.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0).getUserId()));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    LatteLoader.stopLoading();
                    final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(manager);
                    final List<MultipleItemEntity> data =
                            new AddressDataConverter().setList(list).convert();
                    final AddressAdapter addressAdapter = new AddressAdapter(data);
                    mRecyclerView.setAdapter(addressAdapter);

                }
            }
        });

    }
}
