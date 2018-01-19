package com.zuimeng.hughfowl.latee.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.sort.SortDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.net.RestClient;
import com.zuimeng.hughfowl.latte.net.callback.ISuccess;
import com.zuimeng.hughfowl.latte.ui.recycler.ItemType;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ywh on 2018/1/5.
 */

public class VerticalListDelegate extends LatteDelegate {

    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRecyclerView = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        initRecyclerView();
    }
    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        //屏蔽动画效果
        mRecyclerView.setItemAnimator(null);
    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final AVQuery<AVObject> query = new AVQuery<>("Sort_left");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public  void done(List<AVObject> list, AVException e) {
                for(AVObject obj:list)
                {
                    int id=obj.getInt("id");
                    String name =obj.getString("name");
                    final MultipleItemEntity entity = MultipleItemEntity.builder()
                            .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                            .setField(MultipleFields.ID, id-1)
                            .setField(MultipleFields.TEXT, name)
                            .setField(MultipleFields.TAG, false)
                            .build();
                    dataList.add(id-1,entity);


                }
                    //设置第一个被选中
                    dataList.get(0).setField(MultipleFields.TAG, true);
                    final List<MultipleItemEntity> data =
                            dataList;
                    final SortDelegate delegate = getParentDelegate();
                    final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, delegate);
                    mRecyclerView.setAdapter(adapter);
            }

        });

    }
}
