package com.zuimeng.hughfowl.latte.datamanage;

/**
 * Created by hughfowl on 2018/1/18.
 */

public class DataManage {
    int temp;
    public   void postData(int i){
        temp = i;
    }

    public void getData(int i){
        i = temp;
    }
}
