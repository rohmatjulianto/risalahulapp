package com.example.a1183008.Fragments;


import com.example.a1183008.Notifications.MyResponse;
import com.example.a1183008.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAMyvlrlU:APA91bEYjFCY0aFs-d3cPwDPSXOQHsLnBoK5h9UT_0xbg6uT-QS9IR9rMqLWVG69xWA1fqYoL5zwMYO2tjQdhoZF1yMIOjuZNakwv4uU7Yvam5Lcs1ttQbVuBlFFC0SRu0hzorjF3c16 "
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
