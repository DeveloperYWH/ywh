package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rhapsody on 2018/3/18.
 */

public class ShopSectionDataConverter {

    private List<AVObject> mList = new ArrayList<>();
    private boolean mIsMore = false;

    public ShopSectionDataConverter setList(List<AVObject> mList) {
        this.mList.addAll(mList);
        return this;
    }

    public void setIsMore(boolean isMore) {
        mIsMore = isMore;
    }

    final List<ShopSectionBean> convert() {
        final List<ShopSectionBean> dataList = new ArrayList<>();

        List<AVObject> dataArray = new ArrayList<>();
        dataArray.addAll(mList);

        final AVObject data = dataArray.get(0);
        final String Jdata = data.toJSONObject().toString();

        final JSONArray goodsList = JSON.parseObject(Jdata).getJSONArray("displayData");

        for (int i = 0; i < goodsList.size(); i++) {
            JSONObject goods = JSON.parseObject(goodsList.get(i).toString());
            final int id = goods.getInteger("id");
            final String title = goods.getString("name");

            //添加title
            final ShopSectionBean shopSectionBean = new ShopSectionBean(true, title);
            shopSectionBean.setPosition(i);
            shopSectionBean.setId(id);
            shopSectionBean.setIsMore(true);
            dataList.add(shopSectionBean);

            //商品内容循环

            final int goodSize = goods.getJSONArray("goods").size();

            for (int j = 0; j < goodSize; j++) {
                final JSONArray thumbList = goods.getJSONArray("goods");
                final String goodsThumb = thumbList.get(j).toString();

                //获取内容
                final ShopSectionContentEntity itemEntity = new ShopSectionContentEntity();
                itemEntity.setGoodsThumb(goodsThumb);
                itemEntity.setSize(goodSize);
                //添加内容
                dataList.add(new ShopSectionBean(itemEntity));
            }
            //商品内容循环结束
        }
        //Section循环结束

        return dataList;
    }
}
