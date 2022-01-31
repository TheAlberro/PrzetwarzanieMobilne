package com.example.aplikacja;

public class friendsModel {

    private String requestorId;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public friendsModel() {}

    public friendsModel(String requestorId) {
        this.requestorId = requestorId;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }
}
