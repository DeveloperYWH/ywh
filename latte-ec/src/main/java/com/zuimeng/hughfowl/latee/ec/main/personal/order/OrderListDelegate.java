package com.zuimeng.hughfowl.latee.ec.main.personal.order;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DaoSession;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.database.UserProfile;
import com.zuimeng.hughfowl.latee.ec.database.UserProfileDao;
import com.zuimeng.hughfowl.latee.ec.main.personal.PersonalDelegate;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/1/22.
 */

public class OrderListDelegate extends LatteDelegate {


    private String mType = null;
    private List<AVObject> AVList = new ArrayList<>();

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mType = args.getString(PersonalDelegate.ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


        final AVQuery<AVObject> query = new AVQuery<>("Order_list_test");
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
            public  void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    list.get(0).put("type", mType);
                    AVList.addAll(list);
                    final List<MultipleItemEntity> data =
                            new OrderListDataConverter().setList(AVList).convert();
                    final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(manager);

                    final OrderListAdapter adapter = new OrderListAdapter(data);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));
                    LatteLoader.stopLoading();
                }
                else {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}
