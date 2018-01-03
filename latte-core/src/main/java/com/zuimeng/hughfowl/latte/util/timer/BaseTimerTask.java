package com.zuimeng.hughfowl.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by hughfowl on 2017/8/24.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }


    @Override
    public void run() {

        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }


    }
}
