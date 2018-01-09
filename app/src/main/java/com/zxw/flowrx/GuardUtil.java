package com.zxw.flowrx;

import java.util.ArrayList;

/**
 * Created by xiangwuzhu on 17/11/9.
 */

public class GuardUtil {

    private static GuardUtil guardUtil;

    private ArrayList<MyCallback> myCallbackList = new ArrayList<>();

    private GuardUtil() {
    }

    public static GuardUtil getInstance(){
        if (guardUtil == null) {
            guardUtil =  new GuardUtil();
        }
        return guardUtil;
    }

    public void testCallback(MyCallback myCallback){
        final TestCallBack testCallBack = new TestCallBack(myCallback);
        testCallBack.notifyData();
    }

    public void addCallbackListener(MyCallback myCallback){
        myCallbackList.add(myCallback);
    }

    public void notifyAllCallback(){
        for (MyCallback callback : myCallbackList){
            callback.notifyTips();
        }
    }
}
