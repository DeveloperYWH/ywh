package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;
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

import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ywh on 2018/1/5.
 */

public class CommentsDelegate extends LatteDelegate {


    private List<AVObject> AVList = new ArrayList<>();
    private List<SectionBean> mData = null;
    private CommentsDataConverter CommentsDataConverter = null;
    private String Id=null;


    @BindView(R2.id.comments)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.item_comments;
    }

    private void initData(String id) {

        final String Id=id;
        final AVQuery<AVObject> query1 = new AVQuery<>("User_comments");
        query1.whereEqualTo("moments_id",Id);
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done( List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size()==0)
                    {
                        AVObject newMoments=new AVObject("User_comments");
                        newMoments.put("moments_id",Id);
                        newMoments.saveInBackground();
                    }
                    AVList.addAll(list);
                    CommentsDataConverter = new CommentsDataConverter().setList(list);

                    mData = CommentsDataConverter.convert();
                    CommentsAdapter sectionAdapter = new CommentsAdapter(R.layout.item_comments,
                            R.layout.item_section_header, mData);
                    if (mRecyclerView!=null)
                        mRecyclerView.setAdapter(sectionAdapter);
                }
                else
                {
                }
            }
        });

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
            initData(Id);


    }
    public void setId(String id)
    {
        Id=id;
    }
}
