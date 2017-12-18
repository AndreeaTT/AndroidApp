package com.example.student.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Student on 05.12.2017.
 */

public class Message implements Serializable{

    private String username;
    private String msg;
    private String date;
    private Boolean favourite;

    public Message(String username, String msg, String date) {
        this.username = username;
        this.msg = msg;
        this.date = date;
        this.favourite = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }
}
