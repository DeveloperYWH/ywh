package com.zuimeng.hughfowl.latee.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

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
import com.joanzapata.iconify.widget.IconTextView;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleRecyclerAdapter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Rhapsody on 2018/1/5.
 */

public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSelectedAll = false;
    private ICartItemListener mCartItemListener = null;
    private double mTotalPrice = 0.00;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();


    ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        for (MultipleItemEntity entity : data) {
            //final int id = entity.getField(MultipleFields.ID);
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            mTotalPrice = mTotalPrice + total;
            BigDecimal fix = new BigDecimal(mTotalPrice);
            mTotalPrice = fix.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        //添加购物测item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }

    public void setCartItemListener(ICartItemListener listener) {
        this.mCartItemListener = listener;
    }

    public double getTotalPrice() {

        return mTotalPrice;
    }


    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);
                //取出所以控件
                final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);

                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);

                //在左侧勾勾渲染之前改变全选与否状态
                entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);


                //根据数据状态显示左侧勾勾
                if (isSelected) {
                    iconIsSelected.setTextColor
                            (ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                } else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加左侧勾勾点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelected) {
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            iconIsSelected.setTextColor
                                    (ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                            entity.setField(ShopCartItemFields.IS_SELECTED, true);
                        }
                    }
                });

                final AVQuery<AVObject> query = new AVQuery<>("Cart_Datas");
                query.whereEqualTo("user_id",
                        String.valueOf(DatabaseManager
                                .getInstance()
                                .getDao()
                                .queryBuilder()
                                .listLazy()
                                .get(0).getUserId()));

                //添加加减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        LatteLoader.showLoading(v.getContext());
                        query.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                final AVObject data = list.get(0);

                                final String Jdata = data.toJSONObject().toString();
                                final JSONArray cart_list = JSON.parseObject(Jdata).getJSONArray("shop_cart_data");

                                final int goodsId = entity.getField(MultipleFields.ID);
                                for (int i = 0; i < cart_list.size(); i++) {
                                    JSONObject goodsInfo = (JSONObject) cart_list.get(i);
                                    if (goodsId == goodsInfo.getInteger("id")) {

                                        if (goodsInfo.getInteger("count") > 1) {
                                            int countNum = goodsInfo.getInteger("count");
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));
                                            goodsInfo.put("count", countNum);
                                            data.put("shop_cart_data", cart_list);
                                            data.saveInBackground();
                                        }
                                    }
                                }
                                if (mCartItemListener != null) {
                                    double totalPrice = 0.00;
                                    for (int i = 0; i < cart_list.size(); i++) {
                                        JSONObject goodsInfo = (JSONObject) cart_list.get(i);
                                        totalPrice = totalPrice + goodsInfo.getInteger("count") *
                                                goodsInfo.getDouble("price");
                                    }
                                    mTotalPrice = totalPrice;
                                    mCartItemListener.onItemClick();
                                    LatteLoader.stopLoading();
                                }
                            }
                        });
                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LatteLoader.showLoading(v.getContext());
                        query.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                final AVObject data = list.get(0);
                                final String Jdata = data.toJSONObject().toString();
                                final JSONArray cart_list = JSON.parseObject(Jdata).getJSONArray("shop_cart_data");

                                final int goodsId = entity.getField(MultipleFields.ID);
                                for (int i = 0; i < cart_list.size(); i++) {
                                    JSONObject goodsInfo = (JSONObject) cart_list.get(i);
                                    if (goodsId == goodsInfo.getInteger("id")) {
                                        int countNum = goodsInfo.getInteger("count");
                                        countNum++;
                                        tvCount.setText(String.valueOf(countNum));
                                        goodsInfo.put("count", countNum);
                                        data.put("shop_cart_data", cart_list);
                                        data.saveInBackground();
                                    }
                                }
                                if (mCartItemListener != null) {
                                    double totalPrice = 0.00;
                                    for (int i = 0; i < cart_list.size(); i++) {
                                        JSONObject goodsInfo = (JSONObject) cart_list.get(i);
                                        totalPrice = totalPrice + goodsInfo.getInteger("count") *
                                                goodsInfo.getDouble("price");
                                    }
                                    mTotalPrice = totalPrice;
                                    mCartItemListener.onItemClick();
                                    LatteLoader.stopLoading();
                                }
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }
    }
}