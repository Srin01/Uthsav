package com.example.uthsav.Activities.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService
{
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAg_uAMRQ:APA91bE7zgbzwOmpoGWqOvdYQ7x5jwzjqkdioQurbCpImCn8RzN1ABOOErWvd9ucXiUT_n975un5mzYGHWbejBTapcFP1W7sk4jL_ZEopg26gwDY-dvkjxoisIx8pDz1FRjGgC7axRoQ"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
