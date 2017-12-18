package com.example.student.service;

import com.example.student.model.RequestMessage;
import com.example.student.model.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by AndreeaT on 12/12/2017.
 */

public interface MessageInterface {

    @PUT("sendmessage.php")
    @Headers("Content-Type:application/json")
    Call<Void> sendMessage(@Header("Authorization") String auth,@Body RequestMessage message);

    @GET("readmessages.php")
    @Headers("Content-Type:application/json")
    Call<List<ResponseMessage>> receiveMessage(@Header("Authorization") String auth);
}
