package com.example.uthsav.Activities.Modal;

import java.util.ArrayList;

public class User
{
    private String userId;
    private String userMail;
    private String userName;
    private String userImage;
    private String userPhoneNumber;
    private String userCollageName;
    private String userRegisterNumber;
    private ArrayList<String> userParticipatedEvents;

    public User() {
    }

    public User(String userId, String userMail, String userName, String userImage, String userPhoneNumber, String userCollageName, String userRegisterNumber, ArrayList<String> userParticipatedEvents)
    {
        this.userId = userId;
        this.userMail = userMail;
        this.userName = userName;
        this.userImage = userImage;
        this.userPhoneNumber = userPhoneNumber;
        this.userCollageName = userCollageName;
        this.userRegisterNumber = userRegisterNumber;
        this.userParticipatedEvents = userParticipatedEvents;
    }

    public void addEventToUserList(String id)
    {
        this.userParticipatedEvents.add(id);
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserCollageName() {
        return userCollageName;
    }

    public void setUserCollageName(String userCollageName) {
        this.userCollageName = userCollageName;
    }

    public String getUserRegisterNumber() {
        return userRegisterNumber;
    }

    public void setUserRegisterNumber(String userRegisterNumber) {
        this.userRegisterNumber = userRegisterNumber;
    }

    public ArrayList<String> getUserParticipatedEvents() {
        return userParticipatedEvents;
    }

    public void setUserParticipatedEvents(ArrayList<String> userParticipatedEvents) {
        this.userParticipatedEvents = userParticipatedEvents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
