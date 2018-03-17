package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.shop.BottomItemShopDelegate;


import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by hughfowl on 2018/1/28.
 */

public class MyShopDelegate extends BottomItemShopDelegate {

    @BindView(R2.id.img_user_avatar_shop)
    CircleImageView mCircleImageView = null;
    @BindView(R2.id.shop_name_text)
    TextView mTextView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate2_my_shop;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {

    }

    @OnClick(R2.id.shop_creat)
    void OnClickCreateShop(){
        getParentDelegate().getSupportDelegate().start(new CreatShopDelegate());
    }


}


