package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import com.avos.avoscloud.AVObject;

import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

public class SeriesListConverter extends DataConverter {

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

        for (int i = 0; i < mList.size(); i++) {
            AVObject data = mList.get(i);

            final String title = data.getString("name");
            final String thumb = data.getAVFile("image").getUrl();
            final int count = data.getJSONArray("goods").length();

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, SeriesItemType.SERIES_ITEM_TYPE)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(SeriesListItemFields.TITLE, title)
                    .setField(SeriesListItemFields.COUNT, count)
                    .setField(SeriesListItemFields.POSITION, i)
                    .build();

            dataList.add(entity);
        }

        return dataList;
    }
}
