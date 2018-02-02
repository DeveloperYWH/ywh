package com.zuimeng.hughfowl.latee.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hughfowl on 2018/1/23.
 */

public class AddressDataConverter extends DataConverter {
    private List<AVObject> mList = new ArrayList<>();

    public DataConverter setList(List<AVObject> mList) {
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
            final JSONArray address = JSON.parseObject(Jdata).getJSONArray("user_address");
            //内容循环
            final int addressSize = address.size();
            for (int j = 0;  j <  addressSize; j++) {
                final JSONObject content_data = address.getJSONObject(j);

                final int id = content_data.getInteger("id");
                final String name = content_data.getString("name");
                final String phone = content_data.getString("phone");
                final String maddress = content_data.getString("address");
                final boolean isDefault = content_data.getBoolean("default");

                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(AddressItemType.ITEM_ADDRESS)
                        .setField(MultipleFields.ID, id)
                        .setField(MultipleFields.NAME, name)
                        .setField(MultipleFields.TAG, isDefault)
                        .setField(AddressItemFields.ADDRESS, maddress)
                        .setField(AddressItemFields.PHONE, phone)
                        .build();
                ENTITIES.add(entity);

            }
        }

        return ENTITIES;
    }
}
