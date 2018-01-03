package com.zuimeng.hughfowl.latee.ec.launcher;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;


import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.sign.SignInDelegate;
import com.zuimeng.hughfowl.latte.app.AccountManager;
import com.zuimeng.hughfowl.latte.app.IUserChecker;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.launcher.ILauncherListener;
import com.zuimeng.hughfowl.latte.ui.launcher.LauncherHolderCreator;
import com.zuimeng.hughfowl.latte.ui.launcher.OnLauncherFinshTag;
import com.zuimeng.hughfowl.latte.ui.launcher.ScrollLauncherTap;
import com.zuimeng.hughfowl.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by hughfowl on 2017/8/24.
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener{


    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private ILauncherListener mILauncherListener = null;

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);


    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();

    }


    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个
        if (position == INTEGERS.size()-1 ) {
            LattePreference.setAppFlag(ScrollLauncherTap.HAS_FIRST_LAUNCHER_APP.name(), true);

            AccountManager.checkAccount(new IUserChecker() {
                  @Override
                public void onSignIn() {
                    if (mILauncherListener != null){
                        mILauncherListener.onLauncherFinsh(OnLauncherFinshTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null){
                        mILauncherListener.onLauncherFinsh(OnLauncherFinshTag.NOT_SIGNED);

                    }

                }
            });
            getSupportDelegate().startWithPop(new SignInDelegate());







        }
    }
}
