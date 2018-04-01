package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zuimeng.hughfowl.latee.ec.R;

import java.util.List;

/**
 * Created by Rhapsody on 2018/3/17.
 */

public class ShopDisplayAdapter extends BaseSectionQuickAdapter<ShopSectionBean, BaseViewHolder> {

    private boolean mIsMore = false;
    private BaseViewHolder mContentHolder = null;

    public ShopDisplayAdapter(int layoutResId, int sectionHeadResId, List<ShopSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(final BaseViewHolder helper, ShopSectionBean item) {
        helper.setText(R.id.shop_header, item.header);
        helper.setVisible(R.id.shop_more, item.isMore());
//        helper.addOnClickListener(R.id.shop_more);
        helper.getView(R.id.shop_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopSectionBean item) {
        //item.t返回SectionBean类型
        final String thumb = item.t.getGoodsThumb();
        final ShopSectionContentEntity entity = item.t;
        final AppCompatImageView goodsImageView = helper.getView(R.id.iv_shop);
        Glide.with(mContext)
                .load(thumb)
                .into(goodsImageView);
    }

}

