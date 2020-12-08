package com.example.uthsav.Activities.Expert;

import android.util.Log;

import com.example.uthsav.Activities.Drivers.EventDriver;
import com.example.uthsav.Activities.Modal.Event;

import java.util.ArrayList;

public class SelectedEventsExpert
{
    ArrayList<Event> events ;
    EventDriver eventDriver;
    private static SelectedEventsExpert single_instance = null;

    private SelectedEventsExpert(){
        eventDriver = new EventDriver();
        events = eventDriver.getEventOfRoundDone();
    }

    public static SelectedEventsExpert getInstance()
    {
        if(single_instance == null) {
            single_instance = new SelectedEventsExpert();
            Log.d("myTag", "getInstance: new SelectedEventsExpert created " + single_instance.hashCode());
        }
        return single_instance;
    }

    public Event getEventOfPosition(int position)
    {
        return events.get(position);
    }

    public int getTotalDoneEvents()
    {
        return events.size();
    }
}
