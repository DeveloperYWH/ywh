package com.zuimeng.hughfowl.latee.ec.shop.myshop.customer_service;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TableLayout;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import java.util.Arrays;

import butterknife.BindView;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;

/**
 * Created by hughfowl on 2018/3/24.
 */

public class CustomerServiceDelegate extends LatteDelegate {
    @BindView(R2.id.tab_layout_chat)
    TabLayout tabLayout = null;
    @BindView(R2.id.chat_view_pager)
    ViewPager viewPager = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate2_customer_serviece;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        String[] tabList = new String[]{"会话", "联系人"};
        final Fragment[] fragmentList = new Fragment[] {new LCIMConversationListFragment(),
                new ContactDelegate()};

        TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager(),
                Arrays.asList(fragmentList), Arrays.asList(tabList));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
//          EventBus.getDefault().post(new ConversationFragmentUpdateEvent());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }


}
