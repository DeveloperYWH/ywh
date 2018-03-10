package com.zuimeng.hughfowl.latee.ec.main;

import android.graphics.Color;

import com.zuimeng.hughfowl.latee.ec.main.cart.ShopCartDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.ExplorerDelegate;
import com.zuimeng.hughfowl.latee.ec.main.index.IndexDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.PersonalDelegate;
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
        items.put(new BottomTabBean("{icon-home-page}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{icon-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{icon-find-1}", "发现"), new ExplorerDelegate());
        items.put(new BottomTabBean("{icon-shop-cart}", "购物车"), new ShopCartDelegate());
        items.put(new BottomTabBean("{icon-myself}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#b82328");
    }
}
