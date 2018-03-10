package com.zuimeng.hughfowl.latee.ec.main.explorer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.library.FocusResizeScrollListener;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hughfowl on 2018/3/10.
 */

public class ExplorerDelegate extends BottomItemDelegate {


    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView = null;


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        createCustomAdapter(mRecyclerView, linearLayoutManager);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_explorer;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecyclerView();
    }

    private void createCustomAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        CustomAdapter customAdapter = new CustomAdapter(this, (int) getResources().getDimension(R.dimen.custom_item_height));
        customAdapter.addItems(addItems());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(customAdapter);
            recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(customAdapter, linearLayoutManager));
        }
    }

    private List<CustomObject> addItems() {

        List<CustomObject> items = new ArrayList<>();
        items.add(new CustomObject("Possibility", "The Hill", R.drawable.image01));
        items.add(new CustomObject("Finishing", "The Grid", R.drawable.image02));
        items.add(new CustomObject("Craftsmanship", "Metropolitan Center", R.drawable.image03));
        items.add(new CustomObject("Opportunity", "The Hill", R.drawable.image04));
        items.add(new CustomObject("Starting Over", "The Grid", R.drawable.image05));
        items.add(new CustomObject("Identity", "Metropolitan Center", R.drawable.image06));
        return items;
    }
}
