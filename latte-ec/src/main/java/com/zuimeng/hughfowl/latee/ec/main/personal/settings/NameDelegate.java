package com.zuimeng.hughfowl.latee.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.BindView;

/**
 * Created by Rhapsody
 */

public class NameDelegate extends LatteDelegate {

    @BindView(R2.id.text_name_submit)
    TextView mNameText = null;
    @BindView(R2.id.btn_name_submit)
    AppCompatButton mNameBtn = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
