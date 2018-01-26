package com.zuimeng.hughfowl.latee.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.joanzapata.iconify.widget.IconTextView;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Rhapsody on 2018/1/5.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ICartItemListener {

    private List<AVObject> AVList = new ArrayList<>();

    private ShopCartAdapter mAdapter = null;
    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private double mTotalPrice = 0.00;


    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;
    @BindView((R2.id.tv_top_shop_cart_clear))
    AppCompatTextView mButton = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll.setTextColor
                    (ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView的显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        LatteLoader.showLoading(getContext());
        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        final List<Integer> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(data.indexOf(entity));
            }
        }
        int i = 0;
        for (int entity : deleteEntities) {
            AVList.remove((entity - i));
            i++;
        }
        final ArrayList<MultipleItemEntity> mdata =
                new ShopCartDataConverter().setList(AVList).convert();


        mAdapter = new ShopCartAdapter(mdata);
        mAdapter.setCartItemListener(ShopCartDelegate.this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        if (mAdapter.getItemCount() != 0) {
            checkItemCount();
        }
        LatteLoader.stopLoading();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        AVList.clear();
        final ArrayList<MultipleItemEntity> mdata =
                new ShopCartDataConverter().setList(AVList).convert();


        mAdapter = new ShopCartAdapter(mdata);
        mAdapter.setCartItemListener(ShopCartDelegate.this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        //if (mAdapter.getItemCount() != 0) {
        checkItemCount();
        mButton.setClickable(false);
        //}
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {
        //createOrder();
    }


    //创建订单，注意，和支付是没有关系的
    private void createOrder() {

        final AVObject mOrder = new AVObject("Order_test");

        //添加订单参数
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                mOrder.put("user_id", list.get(0).getMobilePhoneNumber());
            }
        });
        mOrder.put("amount", 0.01);
        mOrder.put("comment", "测试支付");
        mOrder.put("type", 1);
        mOrder.put("order_type", 0);
        mOrder.put("is_anonymous", true);
        mOrder.saveInBackground();


        /*
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                        LatteLogger.d("ORDER", response);
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();

               */

    }


    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {

            final View stubView = mStubNoItem.inflate();

            final AppCompatTextView tvToBuy =
                    (AppCompatTextView) stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "你该购物啦！", Toast.LENGTH_SHORT).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mIconSelectAll.setTag(0);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


        final AVQuery<AVObject> query = new AVQuery<>("Cart_Data_test");
        LatteLoader.showLoading(getContext());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                AVList.addAll(list);
                final ArrayList<MultipleItemEntity> data =
                        new ShopCartDataConverter().setList(AVList).convert();


                mAdapter = new ShopCartAdapter(data);
                mAdapter.setCartItemListener(ShopCartDelegate.this);
                final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(mAdapter);
                mTotalPrice = mAdapter.getTotalPrice();
                mTvTotalPrice.setText(String.valueOf(mTotalPrice));
                checkItemCount();
                if(mAdapter.getItemCount()!=0){
                    mButton.setClickable(true);
                }
                LatteLoader.stopLoading();


            }

        });
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        BigDecimal fix = new BigDecimal(price);
        double fix_price = fix.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mTvTotalPrice.setText(String.valueOf(fix_price));
    }
}
