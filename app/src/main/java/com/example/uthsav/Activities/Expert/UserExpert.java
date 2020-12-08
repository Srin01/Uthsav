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

    private UserExpert(){
        userDriver = new UserDriver();
        users = userDriver.getAllUsersFromDB();
    }

    public User getUserOfPosition(int position)
    {
        return users.get(position);
    }

    public static UserExpert getInstance()
    {
        if(single_instance == null) {
            single_instance = new UserExpert();
            Log.d("myTag", "getInstance: new UserList expert created " + single_instance.hashCode());
        }
        return single_instance;
    }

    public User getUserOfId(String uid)
    {
        for (int i = 0; i <users.size() ; i++) {
            if(users.get(i).getUserId().equals(uid))
            {
                return users.get(i);
            }
        }
        return null;
    }

    public int getTotalUsers()
    {
        return users.size();
    }
}
