package com.zuimeng.hughfowl.latee.ec.main.index;

import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latte.ui.recycler.DataConverter;
import com.zuimeng.hughfowl.latte.ui.recycler.ItemType;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hughfowl on 2018/1/18.
 */

public class IndexDataConverter extends DataConverter {

    private List<AVObject> mList = new ArrayList<>();

    public DataConverter setList(List<AVObject> mList) {
         this.mList.addAll(this.mList.size(),mList);
        return this;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert(){

        //获取对象个数
        final int size = mList.size();

//        Log.d("fuck",String.valueOf(i)+"dc");
        for (int i = 0; i < size; i++) {

            final AVObject data = mList.get(i);
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
            final JSONArray banners = data.getJSONArray("banner");
            final ArrayList<String> bannerImages = new ArrayList<>();

            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null) {
                type = ItemType.TEXT_IMAGE;
            }else if (banners != null) {
                type = ItemType.BANNER;
                //Banner的初始化
                final int bannerSize = banners.length();
                for (int j = 0; j < bannerSize; j++) {
                    final String banner;
                    try {
                        banner = banners.getString(j);
                        bannerImages.add(banner);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,text)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .setField(MultipleFields.BANNERS,bannerImages)
                    .build();
            ENTITIES.add(entity);

        }
//        Log.d("fuck","返回了"+String.valueOf(ENTITIES.size()));
        return ENTITIES;
    }



}
