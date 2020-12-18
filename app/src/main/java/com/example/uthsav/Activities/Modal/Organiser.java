package com.example.uthsav.Activities.Modal;

public class Organiser {
    private String organiserId;
    private String organiserName;
    private String organiserEmail;
    private String eventId;

    public Organiser() {
    }

    public Organiser(String organiserId, String organiserName, String organiserEmail, String eventId) {
        this.organiserId = organiserId;
        this.organiserName = organiserName;
        this.organiserEmail = organiserEmail;
        this.eventId = eventId;
    }

    public String getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(String organiserId) {
        this.organiserId = organiserId;
    }

    public String getOrganiserName() {
        return organiserName;
    }

    public void setOrganiserName(String organiserName) {
        this.organiserName = organiserName;
    }

    public String getOrganiserEmail() {
        return organiserEmail;
    }

    public void setOrganiserEmail(String organiserEmail) {
        this.organiserEmail = organiserEmail;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
