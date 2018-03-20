package com.zuimeng.hughfowl.latee.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latte.ui.recycler.RgbValue;

/**
 * Created by hughfowl on 2018/1/19.
 */

@SuppressWarnings("unused")
public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar>{

    //顶部距离
    private int mDistanceY = 0;
    //颜色变化速度
    private static final int SHOW_SPEED = 5;
    //定义变化的颜色
    private final RgbValue RGB_VALUE = RgbValue.create(241, 185, 175);

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        //增加滑动距离
        mDistanceY += dyConsumed;
        //toolbar的高度
        final int targetHeight = child.getBottom();

        //当滑动时，并且距离小于 toolbar 高度的时候，调整渐变色
        if (mDistanceY > 0 && mDistanceY <= targetHeight) {
            final float scale = (float) mDistanceY / targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(), RGB_VALUE.blue()));
        }
    }

}
