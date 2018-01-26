package com.zuimeng.hughfowl.latee.ec.main.cart;

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
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();

        List<AVObject> dataArray = new ArrayList<>();
        dataArray.addAll(mList);
        final int size = dataArray.size();//数据总数
        for (int i = 0; i < size; i++) {
            final AVObject data = dataArray.get(i);

            final  int id = (int) data.getNumber("id");
            final String title = data.getString("title");
            final String desc = data.getString("desc");
            final String thumb = data.getString("thumb");
            final  int count = (int) data.getNumber("count");
            final  double price = Double.parseDouble(data.getString("price"));

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
        return dataList;
    }
}
