package com.example.tanthinh.local4fun.models;

public class InstantMessage {
    private String message;
    private String sender;
    private String receiver;

    public InstantMessage(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
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
}
