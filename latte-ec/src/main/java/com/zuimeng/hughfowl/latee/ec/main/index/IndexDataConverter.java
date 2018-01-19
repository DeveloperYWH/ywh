package com.zuimeng.hughfowl.latee.ec.main.index;

import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.ItemType;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hughfowl on 2018/1/18.
 */

public class IndexDataConverter extends DataConverter {

    private List<AVObject> mList = new ArrayList<>();

    public DataConverter setList(List<AVObject> mList) {
         this.mList.addAll(mList);
        return this;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert()
    {
        List<AVObject> dataArray = new ArrayList<>();
        dataArray.addAll(mList);
        //Log.d("datalist",String.valueOf(mObjectData.size()));

        //获取对象个数
        final int size = dataArray.size();


        for (int i = 1; i < size; i++) {

            final AVObject data = dataArray.get(i);
            String imageUrl = null;
            if(data.getAVFile("Image") != null){
                imageUrl = data.getAVFile("Image").getUrl();
            }
            String text = null;
            if( data.getString("Text") != null){
                 text  =  data.getString("Text");
            }

            final int spanSize = (int) data.getNumber("spanSize");
            final int id = (int) data.getNumber("goods_Id");


            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null) {
                type = ItemType.TEXT_IMAGE;
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,text)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .build();

            ENTITIES.add(entity);




        }
        return ENTITIES;
    }



}
