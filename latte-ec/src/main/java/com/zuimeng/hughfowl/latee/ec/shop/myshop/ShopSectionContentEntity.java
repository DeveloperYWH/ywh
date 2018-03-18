package com.zuimeng.hughfowl.latee.ec.shop.myshop;

/**
 * Created by Rhapsody on 2018/3/17.
 */

public class ShopSectionContentEntity {

    private int mGoodsId = 0;
    private String mGoodsName = null;
    private String mGoodsThumb = null;
    private int mSize = 0;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        this.mGoodsId = goodsId;
    }

    public String getGoodsThumb() {
        return mGoodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.mGoodsThumb = goodsThumb;
    }

    public void setSize(int size){
        this.mSize = size;
    }
    public int getSize(){
        return mSize;
    }
}
