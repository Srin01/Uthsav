package com.example.uthsav.Activities.Drivers;

import android.util.Log;

import com.example.uthsav.Activities.Modal.Organiser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class OrganiserDriver {
    FirebaseFirestore firebaseFirestore;
    public static String TAG = "myTag";

    public OrganiserDriver()
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public Organiser getOrganiserOfId(String uid)
    {
        final Organiser[] organisers = new Organiser[1];
        firebaseFirestore.collection("organisers").document(uid).get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                DocumentSnapshot documentSnapshots = task.getResult();
                assert documentSnapshots != null;
                if(documentSnapshots.exists())
                {
                    organisers[0] = (Organiser) documentSnapshots.getData().get(Organiser.class);
                }
            }
            else {
                Log.d(TAG, "getOrganiserOfId: no such user " + uid + " user exists");
                organisers[0] = null;
            }
        });

        return organisers[0];
    }

    public ArrayList<Organiser> getAllOrgaisersFromDB()
    {
        ArrayList<Organiser> organisers = new ArrayList<>();
        firebaseFirestore.collection("organisers").get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for(QueryDocumentSnapshot documentSnapshot: Objects.requireNonNull(task.getResult())){
                    organisers.add(documentSnapshot.toObject(Organiser.class));
                    Log.d(TAG, "getAllOrgaisersFromDB: organiser with id "+ documentSnapshot.getId()+" added");
                }
            }
            else
            {
                Log.d(TAG, "getAllOrgaisersFromDB: "+ task.getException());
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return organisers
                ;
    }

    public void setEventId(String organiserId,String evenId)
    {
        firebaseFirestore.collection("organisers").document(organiserId).update("eventId",evenId);
        Log.d(TAG, "setRoundDone: set eventId of organiser " + evenId);
    }

    public void addOrganiser(Organiser organiser)
    {
        firebaseFirestore.collection("organisers").document(organiser.getOrganiserId()).set(organiser).addOnSuccessListener(documentReference ->{
            Log.d(TAG, "addUserToDB: added user of id " + organiser.getOrganiserId() + " to fireBase");});
    }

    public void setUserName(String name, String uid) {
        firebaseFirestore.collection("organisers").document(uid).update("organiserName",name);
        Log.d(TAG, "setUserName: set name of organiser " + uid);
    }
}
