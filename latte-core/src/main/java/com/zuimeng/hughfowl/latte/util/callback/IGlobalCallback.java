package com.zuimeng.hughfowl.latte.util.callback;

import android.support.annotation.Nullable;

/**
 * Created by Rhapsody
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
