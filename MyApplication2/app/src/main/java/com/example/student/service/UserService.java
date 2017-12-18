package com.example.student.service;

/**
 * Created by AndreeaT on 12/12/2017.
 */

import com.example.student.model.ResponseLogin;
import com.example.student.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService{
    @POST("authenticate.php")
    @Headers("Content-Type:application/json")
    Call<ResponseLogin> loginUser(@Body User user);

    @DELETE("logout.php")
    @Headers("Content-Type:application/json")
    Call<Void> logoutUser(@Header("Authorization") String auth);
}
