package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;

import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.profile.UserProfileDelegate;
import com.zuimeng.hughfowl.latee.ec.main.sort.content.ContentDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by ywh on 2018/1/6.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    private List<AVObject> AVList = new ArrayList<>();
    private List<SectionBean> mData = null;
    private CommentsDataConverter CommentsDataConverter = null;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(final BaseViewHolder helper, SectionBean item) {
        //item.t返回SectionBean类型
        final List thumb = item.t.getmMomentThumb();
        final String name = item.t.getmMomentContent();
        final String Id = item.t.getmMomentId();
        final SectionContentItemEntity entity = item.t;
        final RecyclerView comment=helper.getView(R.id.comments);
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        comment.setLayoutManager(manager);
        LinearLayout sec=helper.getView(R.id.second_line);
        if (thumb.size()<=3)
            sec.setVisibility(View.GONE);
        else
            sec.setVisibility(View.VISIBLE);
        helper.setText(R.id.content, name);
        final AppCompatImageView goodsImageView0 = helper.getView(R.id.thumb0);
        if(thumb.size()>=1)
        Glide.with(mContext)
                .load(thumb.get(0))
                .into(goodsImageView0);
        final AppCompatImageView goodsImageView1 = helper.getView(R.id.thumb1);
        if(thumb.size()>=2)
        Glide.with(mContext)
                .load(thumb.get(1))
                .into(goodsImageView1);
        final AppCompatImageView goodsImageView2 = helper.getView(R.id.thumb2);
        if(thumb.size()>=3)
        Glide.with(mContext)
                .load(thumb.get(2))
                .into(goodsImageView2);
        final AppCompatImageView goodsImageView3 = helper.getView(R.id.thumb3);
        if(thumb.size()>=4)
        Glide.with(mContext)
                .load(thumb.get(3))
                .into(goodsImageView3);
        final AppCompatImageView goodsImageView4 = helper.getView(R.id.thumb4);
        if(thumb.size()>=5)
        Glide.with(mContext)
                .load(thumb.get(4))
                .into(goodsImageView4);
        final AppCompatImageView goodsImageView5 = helper.getView(R.id.thumb5);
        if(thumb.size()>=6)
        Glide.with(mContext)
                .load(thumb.get(5))
                .into(goodsImageView5);
        ShineButton collect=helper.getView(R.id.collect);
        final ShineButton like=helper.getView(R.id.like);
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
        final LinearLayout comment_list=helper.getView(R.id.comment_list);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout comment_layout=helper.getView(R.id.comment_layout);
                if(comment_layout.getVisibility()==View.GONE)
                {
                    comment_layout.setVisibility(View.VISIBLE);
                    comment_list.setVisibility(View.VISIBLE);
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
                                if (comment!=null)
                                    comment.setAdapter(sectionAdapter);
                            }
                            else
                            {
                            }
                        }
                    });
                }
                else
                {
                    comment_layout.setVisibility(View.GONE);
                    comment_list.setVisibility(View.GONE);
                }
            }
        });
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
        LatteLoader.showLoading(mContext);
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
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (like.isChecked())
                {
                    final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
                    LatteLoader.showLoading(mContext);
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
                    LatteLoader.showLoading(mContext);
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
    }
}