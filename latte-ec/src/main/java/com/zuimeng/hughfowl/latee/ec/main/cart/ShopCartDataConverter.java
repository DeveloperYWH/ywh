package com.zuimeng.hughfowl.latee.ec.main.cart;

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
 * Created by Rhapsody on 2018/1/5.
 */

public class ShopCartDataConverter extends DataConverter{

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



        final int size = dataArray.size();//数据总数
        for (int i = 0; i < size; i++) {
            final AVObject data = dataArray.get(i);

            final String Jdata = data.toJSONObject().toString();
            final JSONArray cart_list = JSON.parseObject(Jdata).getJSONArray("shop_cart_data");
            final int goodSize = cart_list.size();
            for (int j = 0;  j <  goodSize; j++) {
                final JSONObject cart_data = cart_list.getJSONObject(j);

                final  int id = (int) cart_data.getInteger("id");
                final String title = cart_data.getString("title");
                final String desc = cart_data.getString("desc");
                final String thumb = cart_data.getString("thumb");
                final  int count = (int) cart_data.getInteger("count");
                final  double price = Double.parseDouble(cart_data.getString("price"));

                final MultipleItemEntity entiity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
                        .setField(MultipleFields.ID, id)
                        .setField(MultipleFields.IMAGE_URL, thumb)
                        .setField(ShopCartItemFields.TITLE, title)
                        .setField(ShopCartItemFields.DESC, desc)
                        .setField(ShopCartItemFields.COUNT, count)
                        .setField(ShopCartItemFields.PRICE, price)
                        .setField(ShopCartItemFields.IS_SELECTED, false)
                        .setField(ShopCartItemFields.POSITION, i)
                        .build();

                dataList.add(entiity);
            }


        }
        return dataList;
    }
}
