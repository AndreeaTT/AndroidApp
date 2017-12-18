package com.example.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AndreeaT on 12/12/2017.
 */

public class RequestMessage {

    @SerializedName("message")
    private String message;

    public RequestMessage(){}

    public RequestMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
