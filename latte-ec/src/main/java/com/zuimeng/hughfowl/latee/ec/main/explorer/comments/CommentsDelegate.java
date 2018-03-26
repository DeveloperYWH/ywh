package com.zuimeng.hughfowl.latee.ec.main.explorer.comments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionBean;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionContentItemEntity;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

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
    private SectionBean item=null;

    @BindView(R2.id.content)
    AppCompatTextView content=null;
    @BindView(R2.id.comments)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_comments_moments;
    }

    private void initData(SectionBean item) {


        final String Id=item.t.getmMomentId();
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
                    Log.d("ywhywh",String.valueOf(mData.size()));
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
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        final List thumb = item.t.getmMomentThumb();
        final String name = item.t.getmMomentContent();
        final String Id = item.t.getmMomentId();
        final SectionContentItemEntity entity = item.t;
        final RecyclerView comment=rootView.findViewById(R.id.comments);
        comment.setLayoutManager(manager);
        LinearLayout sec=rootView.findViewById(R.id.second_line);
        if (thumb.size()<=3)
            sec.setVisibility(View.GONE);
        else
            sec.setVisibility(View.VISIBLE);
        content.setText(name);
        final AppCompatImageView goodsImageView0 = rootView.findViewById(R.id.thumb0);
        if(thumb.size()>=1)
            Glide.with(this)
                    .load(thumb.get(0))
                    .into(goodsImageView0);
        final AppCompatImageView goodsImageView1 = rootView.findViewById(R.id.thumb1);
        if(thumb.size()>=2)
            Glide.with(this)
                    .load(thumb.get(1))
                    .into(goodsImageView1);
        final AppCompatImageView goodsImageView2 = rootView.findViewById(R.id.thumb2);
        if(thumb.size()>=3)
            Glide.with(this)
                    .load(thumb.get(2))
                    .into(goodsImageView2);
        final AppCompatImageView goodsImageView3 = rootView.findViewById(R.id.thumb3);
        if(thumb.size()>=4)
            Glide.with(this)
                    .load(thumb.get(3))
                    .into(goodsImageView3);
        final AppCompatImageView goodsImageView4 = rootView.findViewById(R.id.thumb4);
        if(thumb.size()>=5)
            Glide.with(this)
                    .load(thumb.get(4))
                    .into(goodsImageView4);
        final AppCompatImageView goodsImageView5 = rootView.findViewById(R.id.thumb5);
        if(thumb.size()>=6)
            Glide.with(this)
                    .load(thumb.get(5))
                    .into(goodsImageView5);
        ShineButton collect=rootView.findViewById(R.id.collect);
        final ShineButton like=rootView.findViewById(R.id.like);
        collect.setShapeResource(R.raw.star);
        collect.setBtnColor(Color.GRAY);
        collect.setBtnFillColor(Color.YELLOW);
        collect.setShineCount(8);
        collect.setAllowRandomColor(true);
        like.setShapeResource(R.raw.heart);
        like.setBtnColor(Color.GRAY);
        like.setBtnFillColor(Color.RED);
        like.setShineCount(8);
        like.setAllowRandomColor(true);
        final LinearLayout comment_list=rootView.findViewById(R.id.comment_list);
        final AppCompatTextView content=rootView.findViewById(R.id.content);
        final LinearLayout comment_layout=rootView.findViewById(R.id.comment_layout);
        final AppCompatTextView likeamount=rootView.findViewById(R.id.like_amount);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment_layout.getVisibility()==View.GONE)
                {
                    comment_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    comment_layout.setVisibility(View.GONE);
                }
            }
        });
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
        LatteLoader.showLoading(getContext());
        query_name.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        query_name.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                final AVObject avObject = list.get(0);
                final String Jdata = avObject.toJSONObject().toString();
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                for(int i=0;i<marray.size();i++)
                {
                    if (Id.equals(marray.getJSONObject(i).getString("id")))
                    {
                        like.setChecked(true);
                    }
                }
                LatteLoader.stopLoading();
            }
        });
        final AVQuery<AVObject> query_name1 = new AVQuery<>("User_info");
        LatteLoader.showLoading(getContext());
        query_name1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                int amountright=0;
                for(int j=0;j<list.size();j++)
                {
                    final AVObject avObject = list.get(j);
                    final String Jdata = avObject.toJSONObject().toString();
                    final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                    for(int i=0;i<marray.size();i++)
                    {
                        if (Id.equals(marray.getJSONObject(i).getString("id")))
                        {
                            amountright++;
                        }
                    }

                }
                likeamount.setText(String.valueOf(amountright));
                LatteLoader.stopLoading();
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (like.isChecked())
                {
                    final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
                    LatteLoader.showLoading(getContext());
                    query_name.whereEqualTo("user_id",
                            String.valueOf(DatabaseManager
                                    .getInstance()
                                    .getDao()
                                    .queryBuilder()
                                    .listLazy()
                                    .get(0)
                                    .getUserId()));
                    query_name.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            final AVObject avObject = list.get(0);
                            final String Jdata = avObject.toJSONObject().toString();
                            final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                            JSONObject moment=new JSONObject();
                            moment.put("id",Id);
                            marray.add(moment);
                            avObject.put("like",marray);
                            avObject.saveInBackground();
                            LatteLoader.stopLoading();
                        }
                    });
                }
                else
                {
                    final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
                    LatteLoader.showLoading(getContext());
                    query_name.whereEqualTo("user_id",
                            String.valueOf(DatabaseManager
                                    .getInstance()
                                    .getDao()
                                    .queryBuilder()
                                    .listLazy()
                                    .get(0)
                                    .getUserId()));
                    query_name.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            final AVObject avObject = list.get(0);
                            final String Jdata = avObject.toJSONObject().toString();
                            final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                            for(int i=0;i<marray.size();i++)
                            {
                                if (Id.endsWith(marray.getJSONObject(i).getString("id")))
                                {
                                    marray.remove(i);
                                }
                            }
                            avObject.put("like",marray);
                            avObject.saveInBackground();
                            LatteLoader.stopLoading();
                        }
                    });
                }
            }
        });
            initData(item);


    }
    public void setId(SectionBean data)
    {
        item=data;
    }
}
