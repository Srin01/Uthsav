package com.example.uthsav.Activities.Drivers;

import android.util.Log;

import com.example.uthsav.Activities.Modal.Event;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Objects;

public class EventDriver
{
    FirebaseFirestore firebaseFirestore;
    public static String TAG = "myTag";

    public EventDriver()
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void addEventToDB(Event event)
    {
        firebaseFirestore.collection("events").add(event).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "addEventToDB: document with id "+ documentReference.getId());
            event.setEventId(documentReference.getId());
            documentReference.update("eventId",documentReference.getId());
        }).addOnFailureListener(error -> {
            Log.d(TAG, "addEventToDB: failed to push due to " + error);
        });
    }

    public ArrayList<Event> getAllEventsFromDB()
    {
        ArrayList<Event> events = new ArrayList<>();
        firebaseFirestore.collection("events").get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for(QueryDocumentSnapshot documentSnapshot: Objects.requireNonNull(task.getResult())){
                    events.add(documentSnapshot.toObject(Event.class));
                    Log.d(TAG, "getAllEventsFromDB: event with id "+ documentSnapshot.getId()+" added");
                }
            }
            else
            {
                Log.d(TAG, "getAllEventsFromDB: "+ task.getException());
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return events;
    }



    public void setRoundDone(String evenId)
    {
        firebaseFirestore.collection("events").document(evenId).update("isFirstRound",true);
        Log.d(TAG, "setRoundDone: round one done of event " + evenId);
    }

    public void addUserToSelectedList(String eventId, String uid)
    {
        firebaseFirestore.collection("events").document(eventId).update("selectedUsers", FieldValue.arrayUnion(uid));
        Log.d(TAG, "addUserToSelectedList: added user " + uid + " selected users to event " + eventId);
    }

    public ArrayList<Event> getEventOfRoundDone()
    {
        ArrayList<Event> events = new ArrayList<>();
        firebaseFirestore.collection("events").whereEqualTo("isFirstRound",true).get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for(QueryDocumentSnapshot documentSnapshot: Objects.requireNonNull(task.getResult())){
                    events.add(documentSnapshot.toObject(Event.class));
                    Log.d(TAG, "getEventOfRoundDone: selection event with id "+ documentSnapshot.getId()+" added ");
                }
            }
            else
            {
                Log.d(TAG, "getEventOfRoundDone: "+ task.getException());
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return events;
    }
}
