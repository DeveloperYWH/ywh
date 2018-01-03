package com.zuimeng.hughfowl.latte.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.support.v7.widget.ContentFrameLayout;

import com.zuimeng.hughfowl.latte.R;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

/**
 * Created by hughfowl on 2017/8/22.
 */

public abstract class ProxyActivity extends me.yokeyword.fragmentation.SupportActivity {

    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);


    }
    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);

        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container,setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
