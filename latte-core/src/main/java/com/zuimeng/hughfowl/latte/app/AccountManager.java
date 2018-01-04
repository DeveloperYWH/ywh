package com.zuimeng.hughfowl.latte.app;

import com.zuimeng.hughfowl.latte.util.storage.LattePreference;

/**
 * Created by hughfowl on 2017/9/14.
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState() {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), true);
    }

    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }

}
