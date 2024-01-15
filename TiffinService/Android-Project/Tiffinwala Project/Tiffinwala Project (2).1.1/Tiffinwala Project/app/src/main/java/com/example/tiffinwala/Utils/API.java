package com.example.tiffinwala.Utils;

import com.example.tiffinwala.entity.Order;
import com.example.tiffinwala.entity.User;
import com.example.tiffinwala.entity.subscriped_user;
import com.example.tiffinwala.entity.update_user;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    String BASE_URL ="http://192.168.33.119:4000";

    @POST("/User/register")
    Call<JsonObject>register(@Body User user1);

    @POST("/User/login")
    Call<JsonObject> ValidateUser(@Body User user1);

    @GET("/sububscription/checkAlreadySubscribeOrNot/{userId}")
    Call<JsonObject> checkAlreadySubscribeOrNot(@Path("userId") int userId);

    @POST("/sububscription/insert_user")
    Call<JsonObject> SubscribedUser(@Body subscriped_user user1);

    @GET("/sububscription/{userId}")
    Call<JsonObject> getSubscriptionId(@Path("userId") int userId);

    @POST("/Order/insert_order")
    Call<JsonObject> insert_order(@Body Order order1);

    @GET("/User/all/{userId}")
    Call<JsonObject> getUser(@Path("userId")int userId);


    @POST("/User/update")
    Call<JsonObject> insert_updated_user(@Body update_user user);

    @GET("/User/details/{userId}")
    Call<JsonObject> DetailsOfUser(@Path("userId")int userId);

    @GET("/User/getPhoneNumber/{userId}")
    Call<JsonObject> GetMobileNumber(@Path("userId")int userId);
}
