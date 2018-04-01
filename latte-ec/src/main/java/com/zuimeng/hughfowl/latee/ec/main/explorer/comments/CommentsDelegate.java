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
import com.zuimeng.hughfowl.latee.ec.detail.GoodsDetailDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionBean;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionContentItemEntity;
import com.zuimeng.hughfowl.latte.app.Latte;
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
    @BindView(R2.id.goods)
    android.support.v7.widget.LinearLayoutCompat goods=null;
    @BindView(R2.id.tv_item_shop_cart_title)
    AppCompatTextView goodsname=null;
    @BindView(R2.id.tv_item_shop_cart_desc)
    AppCompatTextView des=null;
    @BindView(R2.id.tv_item_shop_cart_price)
    AppCompatTextView price=null;
    @BindView(R2.id.image_item_shop_cart)
    AppCompatImageView thumbpic=null;

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
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        final int GoodsId=item.t.getGoodsId();
        final List thumb = item.t.getmMomentThumb();
        final String name = item.t.getmMomentContent();
        final String Id = item.t.getmMomentId();
        final SectionContentItemEntity entity = item.t;
        final RecyclerView comment=rootView.findViewById(R.id.comments);
        comment.setLayoutManager(manager);
        LinearLayout sec=rootView.findViewById(R.id.second_line);
        if (GoodsId!=0) {
            goods.setVisibility(View.VISIBLE);
            final AVQuery<AVObject> query = new AVQuery<>("goodss_detail");
            query.whereEqualTo("id", GoodsId);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {

                    AVList.addAll(list);
                    final AVObject object = AVList.get(0);
                    final String Jdata = object.toJSONObject().toString();
                    final JSONArray array = JSON.parseObject(Jdata).getJSONArray("data");
                    final JSONObject goodData = array.getJSONObject(0);
                    final String title = goodData.getString("name");
                    final String desc = goodData.getString("des");
                    final String money = goodData.getString("price");
                    final JSONArray thumb = JSON.parseObject(Jdata).getJSONArray("thumb");
                    final JSONObject goodthumb = thumb.getJSONObject(0);
                    final String pic = goodthumb.getString("goods_thumb");
                    goodsname.setText(title);
                    des.setText(desc);
                    price.setText(money);
                    Glide.with(getContext())
                            .load(pic)
                            .into(thumbpic);
                }

            });
            goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(GoodsId);
                    getSupportDelegate().start(delegate);
                }
            });
        }
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
            initData(item);


    }
    public void setId(SectionBean data)
    {
        item=data;
    }
}
