package com.example.uthsav.Activities.Drivers;

import android.util.Log;

import com.example.uthsav.Activities.Modal.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UserDriver
{
    FirebaseFirestore firebaseFirestore;
    public static String TAG = "myTag";

    public UserDriver()
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void addUserToDB(String uid, User user)
    {
        firebaseFirestore.collection("users").document(uid).set(user).addOnSuccessListener(documentReference -> Log.d(TAG, "addUserToDB: added user of id " + uid + " to fireBase"));
    }

    public ArrayList<User> getAllUsersFromDB() throws InterruptedException {
        ArrayList<User> users = new ArrayList<>();
        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, "OnCompleteGetDataFromUsers " + document.getId() + " => " + document.getData());
                            User user = document.toObject(User.class);
                            users.add(user);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
            Log.d(TAG, "getAllUsersFromDB: " + e);
        });
        Thread.sleep(1000);
        return users;
    }

    public void addEventsRegistered(String uid, String eventId)
    {
        firebaseFirestore.collection("users").document(uid).update("userParticipatedEvents", FieldValue.arrayUnion(eventId));
        Log.d(TAG, "addEventsRegistered: added event of id "+ eventId+ " to user "+ uid);
    }
}
