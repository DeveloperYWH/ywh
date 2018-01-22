package com.zuimeng.hughfowl.latee.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.ItemType;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywh on 2018/1/5.
 */

public final class VerticalListDataConverter extends DataConverter {

    private List<AVObject> mList = new ArrayList<>();

    public DataConverter setList(List<AVObject> mList) {
        this.mList.addAll(mList);
        return this;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        List<AVObject> dataArray = new ArrayList<>();
        dataArray.addAll(mList);

        final int size = dataArray.size();//分类数据总数
        for (int i = 0; i < size; i++) {
            final AVObject data = dataArray.get(i);
            final  int id = (int) data.getNumber("id");
            final String name = data.getString("name");

            final MultipleItemEntity entiity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id-1)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .build();

            dataList.add(id-1,entiity);
            //设置第一个被选中
            dataList.get(0).setField(MultipleFields.TAG, true);
        }

        return dataList;
    }
}

