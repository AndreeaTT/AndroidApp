package com.example.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AndreeaT on 12/12/2017.
 */

public class ResponseLogin {

    @SerializedName("id")
    public String id;

    @SerializedName("token")
    public String token;

    @SerializedName("display")
    public String display;

    public ResponseLogin(){}

    public ResponseLogin(String id, String token, String display) {
        this.id = id;
        this.token = token;
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
