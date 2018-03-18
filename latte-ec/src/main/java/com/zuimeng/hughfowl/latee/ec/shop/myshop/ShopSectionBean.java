package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Rhapsody on 2018/3/17.
 */

public class ShopSectionBean extends SectionEntity<ShopSectionContentEntity> {

    private boolean mIsMore = false;
    private int mId = -1;
    private int mPosition = -1;

    public ShopSectionBean(ShopSectionContentEntity shopSectionContentEntity) {
        super(shopSectionContentEntity);
    }

    public ShopSectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public boolean isMore() {
        return mIsMore;
    }

    public void setIsMore(boolean isMore) {
        this.mIsMore = isMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setPosition(int position){
        mPosition = position;
    }

    public int getPosition(){
        return mPosition;
    }
}
