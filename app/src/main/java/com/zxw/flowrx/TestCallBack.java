package com.zxw.flowrx;

/**
 * Created by xiangwuzhu on 17/11/9.
 */

public class TestCallBack {

    private MyCallback myCallback;

    public TestCallBack(MyCallback myCallback) {
        this.myCallback = myCallback;
    }

    public void notifyData() {
        if (myCallback != null)
            myCallback.notifyTips();

    }
}
