package com.example.tanthinh.local4fun.models;

public class InstantMessage {
    private String message;
    private String sender;
    private String receiver;
    private String imgPath;

    public InstantMessage(String message, String sender, String receiver, String imgPath) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.imgPath = imgPath;
    }

    public InstantMessage() {

    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getreceiver() {
        return receiver;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
