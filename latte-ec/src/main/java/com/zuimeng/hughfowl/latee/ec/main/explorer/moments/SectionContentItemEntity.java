package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;

/**
 * Created by ywh on 2018/1/6.
 */

public class SectionContentItemEntity {

    private int mMomentId = 0;
    private String mMomentContent = null;
    private String mMomentThumb = null;
    private int mCollect = 0;
    private int mLike = 0;

    public int getmMomentId() {
        return mMomentId;
    }

    public void setmMomentId(int GoodsId) {
        this.mMomentId = GoodsId;
    }

    public String getmMomentContent() {
        return mMomentContent;
    }

    public void setmMomentContent(String mMomentContent) {
        this.mMomentContent = mMomentContent;
    }

    public String getmMomentThumb() {
        return mMomentThumb;
    }

    public void setmMomentThumb(String mMomentThumb) {
        this.mMomentThumb = mMomentThumb;
    }
    public int getmCollect(){return mCollect;}
    public void setmCollect(int mCollect){this.mCollect=mCollect;}
    public int getmLike(){return mLike;}
    public void setmLike(int mLike){this.mLike=mLike;}
}