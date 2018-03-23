package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywh on 2018/1/6.
 */

public class SectionDataConverter {

    private List<AVObject> mList = new ArrayList<>();


    public SectionDataConverter setList(List<AVObject> mList) {
        this.mList.addAll(mList);
        return this;
    }


    final List<SectionBean> convert() {
        final List<SectionBean> dataList = new ArrayList<>();

        List<AVObject> dataArray = new ArrayList<>();
        dataArray.addAll(mList);

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final AVObject data = dataArray.get(i);

            final String Jdata = data.toJSONObject().toString();

            final JSONArray goods = JSON.parseObject(Jdata).getJSONArray("content");
            //商品内容循环
            final int goodSize = goods.size();
            for (int j = 0;  j <  goodSize; j++) {
                final JSONObject content_data = goods.getJSONObject(j);

//                final int goodsId = content_data.getInteger("goods_id");
                final String goodsName = content_data.getString("content");
                String goodsThumb=null;
                final ArrayList<String> thumbList=new ArrayList<>();
                for(int k=0;k<6/*6是上传图片的最大值*/;k++)
                {
                    goodsThumb = content_data.getString("thumb"+k);
                    thumbList.add(goodsThumb);

                }
                //获取内容
                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
//                itemEntity.setGoodsId(goodsId-1);
                itemEntity.setmMomentContent(goodsName);
                itemEntity.setmMomentThumb(thumbList);
                //添加内容
                dataList.add(new SectionBean(itemEntity));
            }
            //商品内容循环结束
        }
        //Section循环结束

        return dataList;
    }
}
