package com.example.uthsav.Activities.Drivers;

import android.util.Log;

import com.example.uthsav.Activities.Modal.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
        firebaseFirestore.collection("users").document(uid).set(user).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "addUserToDB: added user of id " + uid + " to fireBase");
        });
    }

    public ArrayList<User> getAllUsersFromDB()
    {
        ArrayList<User> users = new ArrayList<>();
        firebaseFirestore.collection("users").get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for(QueryDocumentSnapshot documentSnapshot: Objects.requireNonNull(task.getResult())){
                    users.add(documentSnapshot.toObject(User.class));
                    Log.d(TAG, "getAllUsersFromDB: user with id "+ documentSnapshot.getId()+" added");
                }
            }
            else
            {
                Log.d(TAG, "getAllUsersFromDB: "+ task.getException());
            }
        });
        return users;
    }

    public User getUserOfId(String uid)
    {
        final AtomicReference<User>[] user = new AtomicReference[]{new AtomicReference<>()};
        firebaseFirestore.collection("users").document(uid).get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                DocumentSnapshot documentSnapshots = task.getResult();
                assert documentSnapshots != null;
                if(documentSnapshots.exists())
                {
                    user[0].set((User) documentSnapshots.getData());
                }
            }
            else {
                Log.d(TAG, "getUserOfId: no such user " + uid + " user exists");
                user[0] = null;
            }
        });
        return user[0].get();
    }

    public void addEventsRegistered(String uid, String eventId)
    {
        firebaseFirestore.collection("users").document(uid).update("userParticipatedEvents", FieldValue.arrayUnion(eventId));
        Log.d(TAG, "addEventsRegistered: added event of id "+ eventId+ " to user "+ uid);
    }
}
