package com.zxw.flowrx.http;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xiangwuzhu on 17/11/30.
 */

public class TestHttp {


    private void requesByGet(boolean isSync){
        final OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new MyIntercept())
                .build();//创建OkHttpClient对象
        final Request request = new Request.Builder()
                .url("http://www.baidu.com")//请求接口。如果需要传参拼接到接口后面。
                .build();//创建Request 对象

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());


        if (isSync){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response response = null;
                        response = client.newCall(request).execute();//得到Response 对象
                        if (response.isSuccessful()) {
                            System.out.println(response.code());
                            System.out.println("response.code()=="+response.code());
                            System.out.println("response.message()=="+response.message());
                            System.out.println("res=="+response.body().string());
                            //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        System.out.println(response.code());
                        System.out.println("response.code()=="+response.code());
                        System.out.println("response.message()=="+response.message());
                        System.out.println("res=="+response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                }
            });
        }

    }

    private void requesByPost(){
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。

        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("username","zhangsan");//传递键值对参数
        Request request = new Request.Builder()//创建Request 对象。
                .url("http://www.baidu.com")
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(response.code());
                    System.out.println("response.code()=="+response.code());
                    System.out.println("response.message()=="+response.message());
                    System.out.println("res=="+response.body().string());
                    //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                }
            }
        });

    }

    public static void main(String[] args){
        TestHttp testHttp = new TestHttp();
        testHttp.requesByGet(false);



    }

}
