package com.zuimeng.hughfowl.latee.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
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

public class ContentDelegate extends LatteDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = 0;

    private List<AVObject> AVList = new ArrayList<>();
    private List<SectionBean> mData = null;
    private SectionDataConverter sectionDataConverter = null;


    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    private void initData() {


        //String id="1";

        final AVQuery<AVObject> query1 = new AVQuery<>("Sort_right"+(mContentId+1));
        LatteLoader.showLoading(getContext());
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done( List<AVObject> list, AVException e) {
                if (e == null) {
                    AVList.addAll(list);
                    sectionDataConverter = new SectionDataConverter().setList(list);

                    mData = sectionDataConverter.convert();

                    final SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content,
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
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

            initData();

    }
}
