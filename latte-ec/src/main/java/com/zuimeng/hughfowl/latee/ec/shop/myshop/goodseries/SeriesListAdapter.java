package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleRecyclerAdapter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

public class SeriesListAdapter extends MultipleRecyclerAdapter {


    public SeriesListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SeriesItemType.SERIES_ITEM_TYPE, R.layout.item2_shop_series);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
                //先取出所有值
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(SeriesListItemFields.TITLE);
                final int count = entity.getField(SeriesListItemFields.COUNT);
                //取出所以控件
                final AppCompatImageView imgThumb = holder.getView(R.id.image2_shop_series);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv2_shop_series_title);
                final AppCompatTextView tvCount = holder.getView(R.id.tv2_shop_list_count);

                //赋值
                tvTitle.setText(title);
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .into(imgThumb);
    }
}
