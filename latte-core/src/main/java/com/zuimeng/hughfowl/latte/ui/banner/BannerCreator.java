package com.zuimeng.hughfowl.latte.ui.banner;

import android.widget.AdapterView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zuimeng.hughfowl.latte.R;

import java.util.ArrayList;

/**
 * Created by ywh on 2018/1/6.
 */

public class BannerCreator {

    public static void setDefault(ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners,
                                  OnItemClickListener clickListener) {

        convenientBanner
                .setPages(new HolderCreator(), banners)
                //.setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                //.setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);

    }
}

