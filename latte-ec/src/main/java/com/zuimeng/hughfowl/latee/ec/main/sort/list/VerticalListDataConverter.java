package com.zuimeng.hughfowl.latee.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.ItemType;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by ywh on 2018/1/5.
 */

public final class VerticalListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();

        final int size = 30;//分类数据总数
        for (int i = 0; i < size; i++) {
            final int id = i;
            final String name = "分类"+(i+1);

            final MultipleItemEntity entiity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .build();

            dataList.add(entiity);
            //设置第一个被选中
            dataList.get(0).setField(MultipleFields.TAG, true);
        }

        return dataList;
    }
}

