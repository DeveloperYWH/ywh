package com.zuimeng.hughfowl.latee.ec.main.explorer;

import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.library.FocusResizeScrollListener;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.explorer.dress.CreateDressUpDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.CreateMomentsDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.MomentsDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/10.
 */

public class ExplorerDelegate extends BottomItemDelegate {





//    @BindView(R2.id.recycler_view)
//    RecyclerView mRecyclerView = null;


//    private void initRecyclerView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        createCustomAdapter(mRecyclerView, linearLayoutManager);
//    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_explorer;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
//        initRecyclerView();
    }
    @OnClick(R2.id.exp_mom_switch)
    void OnClickSwitchMoments(){
        getSupportDelegate().replaceFragment(new MomentsDelegate(),true);
    }

    @OnClick(R2.id.create_moments)
    void OnClickCreateMoments(){
        getParentDelegate().getSupportDelegate().start(new CreateMomentsDelegate());
    }

    @OnClick(R2.id.create_dress)
    void OnClickCreateDress(){
        getParentDelegate().getSupportDelegate().start(new CreateDressUpDelegate());
    }


//
//    private void createCustomAdapter(final RecyclerView recyclerView, final LinearLayoutManager linearLayoutManager) {
//        final CustomAdapter customAdapter = new CustomAdapter(this, (int) getResources().getDimension(R.dimen.custom_item_height));
//        final List<CustomObject> items = new ArrayList<>();
//        final AVQuery<AVObject> query = new AVQuery<>("Explorer_Squer");
//        LatteLoader.showLoading(getContext());
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//
//                if (e  ==null) {
//                    for (int i = 0;i<list.size();i++) {
//                        items.add(new CustomObject(list.get(i).getString("title"), list.get(i).getString("user_name"), list.get(i).getString("image")));
//                    }
//                    customAdapter.addItems(items);
//                    if (recyclerView != null) {
//                        recyclerView.setLayoutManager(linearLayoutManager);
//                        recyclerView.setHasFixedSize(true);
//                        recyclerView.setAdapter(customAdapter);
//                        recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(customAdapter, linearLayoutManager));
//                    }
//                    LatteLoader.stopLoading();
//                }
//            }
//
//        });
//    }

}
