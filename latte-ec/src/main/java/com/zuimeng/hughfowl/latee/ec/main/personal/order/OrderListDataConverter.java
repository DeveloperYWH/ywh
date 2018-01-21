package com.zuimeng.hughfowl.latee.ec.main.personal.order;

import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rhapsody on 2018/1/20.
 */

public class OrderListDataConverter extends DataConverter {
    private List<AVObject> mList = new ArrayList<>();

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        List<AVObject> dataArray = new ArrayList<>();
        dataArray.addAll(mList);
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final AVObject data = dataArray.get(i);
            final String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final int id = (int) data.getNumber("id");
            final double price = data.getDouble("price");
            final String time = data.getString("time");
            return null;
        }
        return null;
    }
}
