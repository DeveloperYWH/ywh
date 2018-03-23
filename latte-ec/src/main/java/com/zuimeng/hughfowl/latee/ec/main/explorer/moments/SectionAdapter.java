package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;

import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.zuimeng.hughfowl.latee.ec.R;

import java.util.List;

/**
 * Created by ywh on 2018/1/6.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

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
    protected void convert(BaseViewHolder helper, SectionBean item) {
        //item.t返回SectionBean类型
        final List thumb = item.t.getmMomentThumb();
        final String name = item.t.getmMomentContent();
//        final int goodsId = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;
        helper.setText(R.id.content, name);
        final AppCompatImageView goodsImageView0 = helper.getView(R.id.thumb0);
        if(thumb.size()>=0)
        Glide.with(mContext)
                .load(thumb.get(0))
                .into(goodsImageView0);
        final AppCompatImageView goodsImageView1 = helper.getView(R.id.thumb1);
        if(thumb.size()>=1)
        Glide.with(mContext)
                .load(thumb.get(1))
                .into(goodsImageView1);
        final AppCompatImageView goodsImageView2 = helper.getView(R.id.thumb2);
        if(thumb.size()>=2)
        Glide.with(mContext)
                .load(thumb.get(2))
                .into(goodsImageView2);
        final AppCompatImageView goodsImageView3 = helper.getView(R.id.thumb3);
        if(thumb.size()>=3)
        Glide.with(mContext)
                .load(thumb.get(3))
                .into(goodsImageView3);
        final AppCompatImageView goodsImageView4 = helper.getView(R.id.thumb4);
        if(thumb.size()>=4)
        Glide.with(mContext)
                .load(thumb.get(4))
                .into(goodsImageView4);
        final AppCompatImageView goodsImageView5 = helper.getView(R.id.thumb5);
        if(thumb.size()>=5)
        Glide.with(mContext)
                .load(thumb.get(5))
                .into(goodsImageView5);
        ShineButton collect=helper.getView(R.id.collect);
        ShineButton like=helper.getView(R.id.like);
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
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"shoucang",Toast.LENGTH_LONG).show();
            }
        });
    }
}