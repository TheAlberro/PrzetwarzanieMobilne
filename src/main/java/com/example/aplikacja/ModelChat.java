package com.example.aplikacja;

public class ModelChat {
    String id, message;
    String  senderId;
    String receiverId;
    String sender;
    String receiver;
    String minute;
    String hour;
    String date;
    String datetime;
    public ModelChat(){}

    public ModelChat(String id, String date)
    {
        this.id=id;
        this.date=date;

    }
    public ModelChat(String id, String title, String desc, String date)
    {
        this.id=id;

        this.date=date;

    }

    public ModelChat( String id,  String date, String message, String receiverId, String senderId, String hour, String minute)
    {
        this.id=id;
        this.message=message;
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.hour=hour;
        this.date=date;
        this.minute=minute;
    }


    public ModelChat( String id,  String datetime, String message, String receiverId, String senderId)
    {
        this.id=id;
        this.message=message;
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.datetime=datetime;

    }

    public String getMessage() {
        return message;
    }
    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId=senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String senderId) {
        this.receiverId=receiverId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour= hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String hour) {
        this.minute= minute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}