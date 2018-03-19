package com.zuimeng.hughfowl.latee.ec.main.explorer.dress;

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
import com.example.library.FocusResizeScrollListener;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.explorer.CustomAdapter;
import com.zuimeng.hughfowl.latee.ec.main.explorer.CustomObject;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/3/19.
 */

public class DressListDelegate extends LatteDelegate {

    @BindView(R2.id.dress_recycler_view)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_dressup_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        createCustomAdapter(mRecyclerView, linearLayoutManager);
    }


    private void createCustomAdapter(final RecyclerView recyclerView, final LinearLayoutManager linearLayoutManager) {
        final CustomAdapter customAdapter = new CustomAdapter(this, (int) getResources().getDimension(R.dimen.custom_item_height));
        final List<CustomObject> items = new ArrayList<>();
        final AVQuery<AVObject> query = new AVQuery<>("Explorer_Squer");
        LatteLoader.showLoading(getContext());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                if (e  ==null) {
                    for (int i = 0;i<list.size();i++) {
                        items.add(new CustomObject(list.get(i).getString("title"), list.get(i).getString("user_name"), list.get(i).getString("image")));
                    }
                    customAdapter.addItems(items);
                    if (recyclerView != null) {
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(customAdapter);
                        recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(customAdapter, linearLayoutManager));
                    }
                    LatteLoader.stopLoading();
                }
            }

        });
    }


}
