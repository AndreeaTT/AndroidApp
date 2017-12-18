package com.example.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AndreeaT on 12/12/2017.
 */

public class ResponseMessage {

    @SerializedName("sender")
    private String sender;

    @SerializedName("text")
    private String text;

    @SerializedName("timestamp")
    private String timestamp;

    public ResponseMessage(){}

    public ResponseMessage(String sender, String text, String timestamp) {
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
