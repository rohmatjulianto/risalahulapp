package com.example.a1183008.Model;

public class ModelMessages {
    String key;
    String message;
    String senderId;
    String receiverId;
    String dibuka;
    long timeStamp;

    public ModelMessages(){

    }

    public ModelMessages(String message, String senderId, String receiverId, String dibuka, long timeStamp) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.dibuka = dibuka;
        this.timeStamp = timeStamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getDibuka() {
        return dibuka;
    }

    public void setDibuka(String dibuka) {
        this.dibuka = dibuka;
    }
}
