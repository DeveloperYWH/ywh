package com.zuimeng.hughfowl.latte.wechat.templates;

import com.zuimeng.hughfowl.latte.activities.ProxyActivity;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.wechat.LatteWeChat;

/**
 * Created by hughfowl on 2017/9/20.
 */

public class WXEntryTemplate extends BaseWXEntryActivity{

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }


    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);

    }
}
