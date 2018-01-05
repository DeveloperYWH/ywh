package com.zuimeng.hughfowl.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.zuimeng.hughfowl.latte.app.Latte;

/**
 * Created by hughfowl on 2017/8/23.
 */

public class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
