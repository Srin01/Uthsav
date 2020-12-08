package com.example.uthsav.Activities.Modal;

import java.util.List;

public class Event
{
    private String eventId;
    private String eventName;
    private String eventImage;
    private String eventDescription;
    private String eventTime;
    private String eventCost;
    private List<String> selectedUsers;

    public Event() {
    }

    public Event(String eventName, String eventImage, String eventDescription, String eventTime, String eventCost) {
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventDescription = eventDescription;
        this.eventTime = eventTime;
        this.eventCost = eventCost;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventCost() {
        return eventCost;
    }

    public void setEventCost(String eventCost) {
        this.eventCost = eventCost;
    }

    public List<String> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<String> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public void addUserToEvents(String uid)
    {
        this.selectedUsers.add(uid);
    }
}
