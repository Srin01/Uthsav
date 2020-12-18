package com.example.uthsav.Activities.Expert;

import com.example.uthsav.Activities.Drivers.OrganiserDriver;
import com.example.uthsav.Activities.Modal.Organiser;

import java.util.ArrayList;

public class OrganiserExpert
{
    private static OrganiserExpert single_instance = null;
    private Organiser organiser;
    OrganiserDriver organiserDriver;
    ArrayList<Organiser> organisers;

    public OrganiserExpert() {
        organiserDriver = new OrganiserDriver();
        organisers = organiserDriver.getAllOrgaisersFromDB();
    }

    public void addOrganiser(Organiser organiser)
    {
        organiserDriver.addOrganiser(organiser);
    }

    public static OrganiserExpert getInstance()
    {
        if(single_instance == null)
        {
            single_instance = new OrganiserExpert();
        }
        return single_instance;
    }

    public void updateUserName(String name, String uid)
    {
        organiserDriver.setUserName(name,uid);
    }

    public void setEventIdOfOrganiser(String eventIdOfOrganiser)
    {
        organiserDriver.setEventId(organiser.getOrganiserId(),eventIdOfOrganiser);
    }

    public Organiser getOrganiser(String uid) {
        for (int i = 0; i < organisers.size(); i++) {
            if(organisers.get(i).getOrganiserId().equals(uid))
            {
                return organisers.get(i);
            }
        }
        return null;
    }

    public void setOrganiser(Organiser organiser)
    {
        this.organiser = organiser;
    }
}
