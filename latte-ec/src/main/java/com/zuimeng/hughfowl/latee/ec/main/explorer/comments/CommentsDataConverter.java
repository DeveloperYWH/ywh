package com.zuimeng.hughfowl.latee.ec.main.explorer.comments;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionBean;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionContentItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywh on 2018/1/6.
 */

public class CommentsDataConverter {

    private List<AVObject> mList = new ArrayList<>();


    public CommentsDataConverter setList(List<AVObject> mList) {
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

            final JSONArray goods = JSON.parseObject(Jdata).getJSONArray("comments");
            //商品内容循环
            final int goodSize = goods.size();
            for (int j = 0;  j <  goodSize; j++) {
                final JSONObject content_data = goods.getJSONObject(j);

                final String Id = content_data.getString("id");
                final String goodsName = content_data.getString("content");
                final String uid = content_data.getString("uid");
                //获取内容
                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
                itemEntity.setmMomentId(Id);
                itemEntity.setmMomentContent(goodsName);
                itemEntity.setUid(uid);
                //添加内容
                dataList.add(new SectionBean(itemEntity));
            }
            //商品内容循环结束
        }
        //Section循环结束

        return dataList;
    }
}
