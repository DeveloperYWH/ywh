package com.zuimeng.hughfowl.latte.util.callback;

import android.support.annotation.Nullable;

import java.io.FileNotFoundException;

/**
 * Created by Rhapsody
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args) throws FileNotFoundException;
}
