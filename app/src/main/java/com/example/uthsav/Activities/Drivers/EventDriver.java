package com.example.uthsav.Activities.Drivers;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.uthsav.Activities.Modal.Event;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
        return events;
    }

    public Event getEventOfId(String id)
    {
        final AtomicReference<Event>[] event = new AtomicReference[]{null};
        firebaseFirestore.collection("events").whereEqualTo("eventId", id).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful()) {
                QuerySnapshot document = task.getResult();
                assert document != null;
                if (!document.isEmpty()) {
                    event[0].set((Event) document.toObjects(Event.class));
                    Log.d(TAG, "DocumentSnapshot data: of ID " + event[0].get().getEventId() + " fetched from DB");
                } else {
                    Log.d(TAG, "No such document");
                    event[0] = null;
                }
            }
        });
        return event[0].get();
    }

    public int getTotalNumberOfEvents()
    {
        final int[] count = {0};
        firebaseFirestore.collection("events").addSnapshotListener((value, error) -> {
            assert value != null;
            count[0] = value.size();
        });
        return count[0];
    }
}
