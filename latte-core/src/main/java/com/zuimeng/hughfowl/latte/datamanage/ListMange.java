package com.zuimeng.hughfowl.latte.datamanage;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by hughfowl on 2018/1/19.
 */

public class ListMange {
    private List<AVObject> temp;
    public   void postList(List<AVObject> i){
        temp = i;
    }

    public List<AVObject> getList(){
        return temp;
    }
}
