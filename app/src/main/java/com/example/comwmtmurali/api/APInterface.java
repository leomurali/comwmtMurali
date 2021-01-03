package com.example.comwmtmurali.api;

import com.example.comwmtmurali.bean.PostUser;
import com.example.comwmtmurali.bean.users.Data;
import com.example.comwmtmurali.bean.users.GetUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.example.comwmtmurali.CommonConstants.HEADER_NAME;
import static com.example.comwmtmurali.CommonConstants.HEADER_VALUE;

public interface APInterface {

    @Headers({"Content-Type: application/json", HEADER_NAME+":"+ HEADER_VALUE})
    @POST("users/register")
    Call<PostUser> postUserInfo( @Body String bean );

//    @Headers({"Content-Type: application/json"})
    @GET("users/list")
    Call<Data>getUser(@Header("Authorization")String token);

}
