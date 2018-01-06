package com.zuimeng.hughfowl.latee.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywh on 2018/1/6.
 */

public class SectionDataConverter {
    final List<SectionBean> convert(int ID) {
        final List<SectionBean> dataList = new ArrayList<>();

        final int size = 10;
        for (int i = 1; i <= size; i++) {

            final int id = i;//
            final String title = "这是分类"+(ID+1)+"的"+i+"卖场";

            //添加title
            final SectionBean sectionTitleBean = new SectionBean(true, title);
            sectionTitleBean.setId(id);
            sectionTitleBean.setIsMore(true);
            dataList.add(sectionTitleBean);

            //商品内容循环
            final int goodSize = 15;
            for (int j = 1; j <= goodSize; j++) {
                final int goodsId = j+i;
                final String goodsName = "我是"+i+"-"+j;
                final String goodsThumb ="1.jpg";
                //获取内容
                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
                itemEntity.setGoodsId(goodsId);
                itemEntity.setGoodsName(goodsName);
                itemEntity.setGoodsThumb(goodsThumb);
                //添加内容
                dataList.add(new SectionBean(itemEntity));
            }
            //商品内容循环结束
        }
        //Section循环结束

        return dataList;
    }
}
