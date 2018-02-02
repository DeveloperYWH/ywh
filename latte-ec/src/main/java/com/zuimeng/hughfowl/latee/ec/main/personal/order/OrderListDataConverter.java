package com.zuimeng.hughfowl.latee.ec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latee.ec.main.sort.content.SectionBean;
import com.zuimeng.hughfowl.latee.ec.main.sort.content.SectionContentItemEntity;
import com.zuimeng.hughfowl.latee.ec.main.sort.content.SectionDataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.ItemType;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rhapsody on 2018/1/20.
 */

public class OrderListDataConverter extends DataConverter {

    private List<AVObject> mList = new ArrayList<>();

    public OrderListDataConverter setList(List<AVObject> mList) {
        this.mList.addAll(mList);
        return this;
    }

    @Override
    public void clearData() {
        ENTITIES.clear();
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        List<AVObject> dataArray = new ArrayList<>();
        dataArray.addAll(mList);

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final AVObject data = dataArray.get(i);


            final String Jdata = data.toJSONObject().toString();
            final JSONArray order_list = JSON.parseObject(Jdata).getJSONArray("order_list");
            //订单列表循环
            final int goodSize = order_list.size();
            for (int j = 0;  j <  goodSize; j++) {
                final JSONObject order_data = order_list.getJSONObject(j);

                final int order_Id = order_data.getInteger("order_id");
                final String goods_title = order_data.getString("goods_title");
                final String order_thumb = order_data.getString("order_thumb");
                final Double price = order_data.getDouble("price");
                final String time = order_data.getString("time");

                //获取内容
                final MultipleItemEntity entity =  MultipleItemEntity.builder()
                        .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                        .setField(MultipleFields.ID, order_Id)
                        .setField(MultipleFields.IMAGE_URL, order_thumb)
                        .setField(MultipleFields.TITLE, goods_title)
                        .setField(OrderItemFields.PRICE, price)
                        .setField(OrderItemFields.TIME,time)
                        .build();
                //添加内容
                ENTITIES.add(entity);
            }

        }

        return ENTITIES;
    }



}
