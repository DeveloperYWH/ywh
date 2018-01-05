package com.zuimeng.hughfowl.latee.ec.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.joanzapata.iconify.widget.IconTextView;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.ui.refresh.RefreshHandler;

import butterknife.BindView;

/**
 * Created by hughfowl on 2017/10/24.
 */

public class IndexDelegate extends BottomItemDelegate{


    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    //@BindView(R2.id.icon_index_scan)
    //IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;


    @BindView(R2.id.relative_layout)
    RelativeLayout relativeLayout = null;

    @BindView(R2.id.index_recyc)
    RecyclerView recyclerView1 = null;


    //@BindView(R2.id.banner_slider)
    //SliderLayout sliderShow = null;
    //@BindView(R2.id.banner_custom_indicator)
    //PagerIndicator custom_indicator = null;


    private RefreshHandler mRefreshHandler = null;



    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = new RefreshHandler(mRefreshLayout);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
       // setBannerView();
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onStop() {
    //    sliderShow.stopAutoCycle();
        super.onStop();
    }

    /* public void setBannerView(){


        TextSliderView banner = new TextSliderView(getContext());
        for (int i=0;i<=3 ;i++) {
            banner
                    .description("this is "+i+" image")
                    .image("https://b-ssl.duitang.com/uploads/item/201506/18/20150618144020_TNa3i.jpeg")
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            //点击事件的监听方法
                        }
                    });
            sliderShow.addSlider(banner);
        }

        //设置转场动画
        //下面代码是设置图片中的小员点的位置，下面的效果就是设置小员点的位置为左上角,如果想简单一些也可以 直接使用下面代码，然后把布局文件里面的。
        //com.daimajia.slider.library.Indicators.PagerIndicat去掉，把本文件里关于com.daimajia.slider.library.Indicators.PagerIndicat的配置去掉
        //sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Left_Top);

        //我们也可以使用自定义小员点，方法就是在布局中添加com.daimajia.slider.library.Indicators.PagerIndicat

        sliderShow.setPresetTransformer(SliderLayout.Transformer.Default);//转场动画风格
        sliderShow.setCustomIndicator(custom_indicator);//添加自定义样式的小员点
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        sliderShow.setDuration(300);//转场动画的时间

        //设置滚动监听方法
        sliderShow.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置点击(事件)监听方法


    }
   */





}
