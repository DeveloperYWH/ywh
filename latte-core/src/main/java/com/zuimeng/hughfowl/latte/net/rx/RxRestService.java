package com.zuimeng.hughfowl.latte.net.rx;

import java.util.Observable;
import java.util.WeakHashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by hughfowl on 2017/8/23.
 */

public interface RxRestService {

    @GET
    io.reactivex.Observable<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @FormUrlEncoded
    @POST
    io.reactivex.Observable<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @POST
    io.reactivex.Observable<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    io.reactivex.Observable<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @PUT
    io.reactivex.Observable<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    io.reactivex.Observable<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Streaming
    @GET
    io.reactivex.Observable<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Multipart
    @POST
    io.reactivex.Observable<String> upload(@Url String url, @Part MultipartBody.Part file);
}
