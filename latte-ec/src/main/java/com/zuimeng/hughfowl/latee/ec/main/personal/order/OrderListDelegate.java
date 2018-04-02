package com.zuimeng.hughfowl.latee.ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.personal.order.order_list.OrderListBuDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.order.order_list.OrderListChuDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.order.order_list.OrderListCommDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.order.order_list.OrderListPayDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.order.order_list.OrderListRecDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderListDelegate extends LatteDelegate{

    private int mpage = 0;

    @BindView(R2.id.order_list_vp)
    ViewPager viewPager = null;
    @BindView(R2.id.order_list_nts)
    NavigationTabStrip topbar = null;

    private List<LatteDelegate> latteDelegates = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        SetUI();

    }
    public void SetPage(int page){
        mpage = page;
    }

    private void SetUI(){
        latteDelegates.add(new OrderListAllDelegate());
        latteDelegates.add(new OrderListPayDelegate());
        latteDelegates.add(new OrderListBuDelegate());
        latteDelegates.add(new OrderListChuDelegate());
        latteDelegates.add(new OrderListRecDelegate());
        latteDelegates.add(new OrderListCommDelegate());

        viewPager.setAdapter(new OrderListPageAdapter(getFragmentManager(),latteDelegates));

        topbar.setViewPager(viewPager,mpage);
        topbar.setTitleSize(35);

    }
}
