package com.example.aplikacja;

public class Model {
    String userId;
    String id, title, desc;
    String  isPublic;
    String status;
    String date;
    String hour;
    String minute;
    public Model(){}

    public Model(String id,  String date)
    {
        this.id=id;
        this.date=date;

    }
    public Model(String id, String title, String desc, String date)
    {
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.date=date;

    }
    public Model(String userId, String id, String title, String desc, String date)
    {
        this.userId=userId;
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.date=date;

    }
    public Model(String id, String title, String desc, String date, String isPublic, String status)
    {
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.isPublic=isPublic;
        this.status= status;
        this.date=date;
    }
    public Model(String userId, String id, String title, String desc, String date, String isPublic, String status, String hour, String minute)
    {
        this.userId=userId;
        this.id=id;
        this.title=title;
        this.desc=desc;
        this.isPublic=isPublic;
        this.status= status;
        this.hour=hour;
        this.date=date;
        this.minute=minute;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId=userId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}