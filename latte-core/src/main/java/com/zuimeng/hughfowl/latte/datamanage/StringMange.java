package com.zuimeng.hughfowl.latte.datamanage;

/**
 * Created by hughfowl on 2018/1/18.
 */

public class StringMange {
    private String  temp;
    public   void postString(String i){
        temp = i;
    }

    public String getString(){
        return temp;
    }
}
