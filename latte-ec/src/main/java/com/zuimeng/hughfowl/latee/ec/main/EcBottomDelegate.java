package com.zuimeng.hughfowl.latee.ec.main;

import android.graphics.Color;

import com.zuimeng.hughfowl.latee.ec.main.cart.ShopCartDelegate;
import com.zuimeng.hughfowl.latee.ec.main.index.IndexDelegate;
import com.zuimeng.hughfowl.latee.ec.main.sort.SortDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BaseBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomTabBean;
import com.zuimeng.hughfowl.latte.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

/**
 * Created by hughfowl on 2017/9/21.
 */

public class EcBottomDelegate extends BaseBottomDelegate {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
