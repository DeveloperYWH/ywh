package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywh on 2018/1/6.
 */

public class SectionContentItemEntity {

    private String mMomentId = null;
    private String mMomentContent = null;
    private ArrayList<String> mMomentThumb = null;
    private ArrayList<String> mComments = null;
    private int mCollect = 0;
    private int mLike = 0;

    public String getmMomentId() {
        return mMomentId;
    }

    public void setmMomentId(String GoodsId) {
        this.mMomentId = GoodsId;
    }

    public String getmMomentContent() {
        return mMomentContent;
    }

    public void setmMomentContent(String mMomentContent) {
        this.mMomentContent = mMomentContent;
    }

    public List<String> getmMomentThumb() {
        return mMomentThumb;
    }

    public void setmComments(ArrayList<String> mComments) {
        this.mMomentThumb = mComments;
    }
    public List<String> getmComments() {
        return mComments;
    }

    public void setmMomentThumb(ArrayList<String> mMomentThumb) {
        this.mMomentThumb = mMomentThumb;
    }
    public int getmCollect(){return mCollect;}
    public void setmCollect(int mCollect){this.mCollect=mCollect;}
    public int getmLike(){return mLike;}
    public void setmLike(int mLike){this.mLike=mLike;}
}
