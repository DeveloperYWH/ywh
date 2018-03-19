package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.avos.avoscloud.AVObject;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/17.
 */

public class CreateMomentsDelegate extends LatteDelegate {
    @BindView(R2.id.exp_mom_edit_text)
    TextInputEditText mText = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_create_moments;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }


    @OnClick(R2.id.exp_mom_commit)
    void OnClickCommit(){
        AVObject content = AVObject.create("Moments");
        content.put("content",mText.getText().toString());

    }

}
