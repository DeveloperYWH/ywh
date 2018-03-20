package com.zuimeng.hughfowl.latee.ec.shop;

import android.graphics.Color;

import com.zuimeng.hughfowl.latee.ec.shop.myshop.MyShopDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.release.ReleaseDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BaseBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomTabBean;
import com.zuimeng.hughfowl.latte.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

/**
 * Created by hughfowl on 2018/1/28.
 */

public class ShopBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();

        items.put(new BottomTabBean("{icon-sort}", "店铺"), new MyShopDelegate());
        items.put(new BottomTabBean("{icon-home-page}", "发布"), new ReleaseDelegate());
        items.put(new BottomTabBean("{icon-find-1}", "数据"), new MyShopDelegate());
        items.put(new BottomTabBean("{icon-shop-cart}", "广告"), new MyShopDelegate());
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
