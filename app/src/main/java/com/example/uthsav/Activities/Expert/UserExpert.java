package com.example.uthsav.Activities.Expert;

import android.util.Log;

import com.example.uthsav.Activities.Drivers.UserDriver;
import com.example.uthsav.Activities.Modal.User;

import java.util.ArrayList;

public class UserExpert
{
    ArrayList<User> users;
    private static UserExpert single_instance = null;
    UserDriver userDriver;

    private UserExpert() throws InterruptedException {
        userDriver = new UserDriver();
        users = userDriver.getAllUsersFromDB();
        Thread.sleep(3000);
    }

    public User getUserOfPosition(int position)
    {
        return users.get(position);
    }

    public static UserExpert getInstance()  {
        if(single_instance == null) {
            try {
                single_instance = new UserExpert();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("myTag", "getInstance: new UserList expert created " + single_instance.hashCode());
        }
        return single_instance;
    }

    public boolean ifUserExists(String uid)
    {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUserId().equals(uid))
                return true;
        }
        return false;
    }

    public void printUsers()
    {
        for (int i = 0; i <users.size() ; i++) {
            Log.d("myTag", "printUsers: "+ users.get(i).getUserId());
        }
    }

    public void addUserToDB(String uid, User user)
    {
        if(!ifUserExists(uid))
        {
            users.add(user);
        }
        userDriver.addUserToDB(uid,user);
    }

    public boolean isEventAlreadyRegistered(String uid, String eventId)
    {
        User user =  getUserOfIdFromCache(uid);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> events = user.getUserParticipatedEvents();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <events.size() ; i++) {
            if(events.get(i).equals(eventId))
            {
                return true;
            }
        }
        return false;
    }

    private void addEventToUserList(String uid,String eventId)
    {
        getUserOfIdFromCache(uid).getUserParticipatedEvents().add(eventId);
    }

    public User getUserOfIdFromCache(String uid)
    {
        for (int i = 0; i <users.size() ; i++) {
            if(users.get(i).getUserId().equals(uid))
            {
                return users.get(i);
            }
        }
        return null;
    }

    public int addEventToUserData(String uid, String eventId)
    {
//        if(isEventAlreadyRegistered(uid,eventId))
//        {
//            return 0;
//        }
//        else
        {
//            addEventToUserList(uid,eventId);
            userDriver.addEventsRegistered(uid,eventId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }

    public int getTotalUsers()
    {
        return users.size();
    }
}
