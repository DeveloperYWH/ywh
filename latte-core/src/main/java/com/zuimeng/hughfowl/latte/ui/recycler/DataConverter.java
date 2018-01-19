package com.zuimeng.hughfowl.latte.ui.recycler;

import android.util.Log;
import android.view.animation.AnticipateOvershootInterpolator;

import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hughfowl on 2018/1/5.
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private List<AVObject> mObjectData = new ArrayList<>();

    protected DataConverter() {
        mObjectData = null;
    }

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setList(List<AVObject> object) {
        this.mObjectData.addAll(object);
        //Log.d("datalist",String.valueOf(mObjectData.size()));
        return this;
    }

    protected List<AVObject> getList(List<AVObject> object) {
        if (mObjectData == null || mObjectData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mObjectData;
    }

    public void clearData(){
        ENTITIES.clear();
    }
}
