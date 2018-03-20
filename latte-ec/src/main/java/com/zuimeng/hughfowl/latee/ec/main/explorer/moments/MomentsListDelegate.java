package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ywh on 2018/1/5.
 */

public class MomentsDelegate extends LatteDelegate {


    private List<AVObject> AVList = new ArrayList<>();
    private List<SectionBean> mData = null;
    private SectionDataConverter sectionDataConverter = null;


    @BindView(R2.id.moments_recycler_view)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_moments_list;
    }

    private void initData() {


        final AVQuery<AVObject> query1 = new AVQuery<>("User_moments");
        LatteLoader.showLoading(getContext());
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done( List<AVObject> list, AVException e) {
                if (e == null) {
                    AVList.addAll(list);
                    sectionDataConverter = new SectionDataConverter().setList(list);

                    mData = sectionDataConverter.convert();

                    final SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_moments,
                            R.layout.item_section_header, mData);
                    if(mRecyclerView!=null)
                    mRecyclerView.setAdapter(sectionAdapter);
                    LatteLoader.stopLoading();
                }
            }
        });

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
            initData();


    }
}
