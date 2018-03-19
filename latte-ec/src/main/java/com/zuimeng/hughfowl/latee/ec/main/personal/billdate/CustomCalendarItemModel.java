package com.zuimeng.hughfowl.latee.ec.main.personal.billdate;

import com.zuimeng.hughfowl.latte.ui.billdate.BaseCalendarItemModel;

/**
 * Created by kelin on 16-7-20.
 */
public class CustomCalendarItemModel extends BaseCalendarItemModel{
    private int ordersCount;
    private boolean isFav;

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
