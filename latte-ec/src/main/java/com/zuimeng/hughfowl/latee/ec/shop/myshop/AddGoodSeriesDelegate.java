package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.BottomItemShopDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries.CreateGoodsSeriesDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/24.
 */

public class AddGoodSeriesDelegate extends BottomItemShopDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            getParentDelegate().getParentDelegate().getSupportDelegate().replaceFragment(new EcBottomDelegate(),false);
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出店铺", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate2_add_good_series;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
    @OnClick(R2.id.shop_add_series_btn)
    void OnClickAddSeries(){
       getParentDelegate().getParentDelegate().getSupportDelegate().start(new CreateGoodsSeriesDelegate());
    }
}
