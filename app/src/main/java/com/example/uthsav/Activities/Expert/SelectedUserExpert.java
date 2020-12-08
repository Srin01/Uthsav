package com.example.uthsav.Activities.Expert;

import android.util.Log;

import com.example.uthsav.Activities.Drivers.EventDriver;

import java.util.List;

public class SelectedUserExpert
{
    List<String> selectedUserList ;
    String eventId;
    EventExpert eventExpert;
    private static SelectedUserExpert single_instance = null;
    EventDriver eventDriver;

    private SelectedUserExpert(String eventId){
        eventDriver = new EventDriver();
        this.eventId = eventId;
        eventExpert = EventExpert.getInstance();
        selectedUserList = eventExpert.getEventOfIdFromCache(eventId).getSelectedUsers();
        Log.d("myTag", "SelectedUserExpert: " + selectedUserList.get(0) + " " + selectedUserList.get(1));
    }

    public static SelectedUserExpert getInstance(String eventId)
    {
        if(single_instance == null) {
            single_instance = new SelectedUserExpert(eventId);
            Log.d("myTag", "getInstance: new SelectedUserList expert created " + single_instance.hashCode());
        }
        return single_instance;
    }

    public String getUserOfPosition(int position)
    {
        return selectedUserList.get(position);
    }

    public int getTotalNumberSelectedUsers()
    {
        return selectedUserList.size();
    }

    public void addUserToSelectedList(String uid)
    {
        selectedUserList.add(uid);
        eventDriver.addUserToSelectedList(eventId,uid);
    }
}
