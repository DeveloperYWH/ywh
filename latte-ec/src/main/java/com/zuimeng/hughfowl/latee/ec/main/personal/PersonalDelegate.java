package com.zuimeng.hughfowl.latee.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;

/**
 * Created by Rhapsody on 2018/1/6.
 */

public class PersonalDelegate extends BottomItemDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
