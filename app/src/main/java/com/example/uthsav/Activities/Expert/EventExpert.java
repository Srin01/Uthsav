package com.example.uthsav.Activities.Expert;

import android.util.Log;

import com.example.uthsav.Activities.Drivers.EventDriver;
import com.example.uthsav.Activities.Modal.Event;

import java.util.ArrayList;

public class EventExpert
{
    ArrayList<Event> events ;
    EventDriver eventDriver;
    private static EventExpert single_instance = null;

    private EventExpert(){
        eventDriver = new EventDriver();
        events = eventDriver.getAllEventsFromDB();
    }

    public static EventExpert getInstance()
    {
        if(single_instance == null) {
            single_instance = new EventExpert();
            Log.d("myTag", "getInstance: new Event expert created " + single_instance.hashCode());
        }
        return single_instance;
    }

    public Event getEventOfIdFromCache(String eventId)
    {
        for (int i = 0; i <getTotalEvents() ; i++) {
            if(events.get(i).getEventId().equals(eventId)) {
                return events.get(i);
            }
        }
        return null;
    }

    public Event getEventOfPosition(int position)
    {
        return events.get(position);
    }

    public void addNewEvent(Event event)
    {
        events.add(event);
        eventDriver.addEventToDB(event);
    }

    public int getTotalEvents()
    {
        return events.size();
    }

    public void setRoundDone(String eventId)
    {
        eventDriver.setRoundDone(eventId);
        getEventOfIdFromCache(eventId).setFirstRound(true);
    }

    public Event getEventOfId(String id)
    {
        return eventDriver.getEventOfId(id);
    }
}
