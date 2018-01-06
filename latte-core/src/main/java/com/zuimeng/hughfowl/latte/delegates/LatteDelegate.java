package com.zuimeng.hughfowl.latte.delegates;

/**
 * Created by hughfowl on 2017/8/23.
 */

public abstract class LatteDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}

