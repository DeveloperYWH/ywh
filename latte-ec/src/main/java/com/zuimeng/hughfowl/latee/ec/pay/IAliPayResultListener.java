package com.zuimeng.hughfowl.latee.ec.pay;

/**
 * Created by Rhapsody on 2018/3/16.
 */

public interface IAliPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
