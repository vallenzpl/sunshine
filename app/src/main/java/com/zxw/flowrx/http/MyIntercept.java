package com.zxw.flowrx.http;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiangwuzhu on 17/12/5.
 */

public class MyIntercept implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

}
