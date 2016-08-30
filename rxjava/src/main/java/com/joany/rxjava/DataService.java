package com.joany.rxjava;

import java.util.List;
import rx.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by joany on 2016/8/30.
 */
public interface DataService {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopData(@Query("start") int start,@Query("count") int count);
}
