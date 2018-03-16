package com.zuimeng.hughfowl.latee.ec.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.net.RestClient;
import com.zuimeng.hughfowl.latte.net.callback.ISuccess;
import com.zuimeng.hughfowl.latte.util.log.LatteLogger;

/**
 * Created by Rhapsody on 2018/3/16.
 */

public class FastPay implements View.OnClickListener {
    //设置支付回调监听
    private IAliPayResultListener mIAliPayResultListener = null;
    private Activity mActivity = null;
    private AlertDialog mDialog = null;
    private int mOrderID = -1;


    private FastPay(LatteDelegate delegate) {
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static FastPay creat(LatteDelegate delegate) {
        return new FastPay(delegate);
    }

    public void beginDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

    public FastPay setPayResultListener(IAliPayResultListener listener) {
        this.mIAliPayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int orderId) {
        this.mOrderID = orderId;
        return this;
    }

    public final void alipay(int orderId){
        final String signUrl = "你的服务端支付地址" + orderId;
        //获取签名字符串
        RestClient.builder()
                .url(signUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        LatteLogger.d("PAY_SIGN", paySign);
                        //必须是异步的调用客户端支付接口
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mIAliPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_dialog_pay_alipay) {
            alipay(mOrderID);
            mDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_wechat) {
            //weChatPay(mOrderID);
            mDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_cancel) {
            mDialog.cancel();
        }
    }
}
