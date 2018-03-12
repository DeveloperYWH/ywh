package com.zuimeng.hughfowl.latee.ec.main.explorer;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hughfowl on 2018/3/10.
 */

public class CustomObject {

    String title;
    String subTitle;
    String drawable;

    public CustomObject(String title, String subTitle, String drawable) {
        this.title = title;
        this.subTitle = subTitle;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDrawable() {
        return drawable;
    }

    public void setDrawable(String drawable) {
        this.drawable = drawable;
    }

}
