package com.zxw.flowrx.myretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xiangwuzhu on 18/1/8.
 */

public interface MovieService {

//    https://api.douban.com/v2/movie/top250?start=0&count=10


    @GET("/top250")
    Call<MovieEntity> getTopMovie(@Query("start")int start, @Query("count")int count);

}
