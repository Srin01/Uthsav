package com.example.uthsav.Activities.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.uthsav.Fragments.NotRegisteredEvents;
import com.example.uthsav.Fragments.RegisteredEvents;

public class PageAdapter extends FragmentStatePagerAdapter
{
    int NumOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int numOfTabs)
    {
        super(fm);
        NumOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                RegisteredEvents registeredEvents = new RegisteredEvents();
                return registeredEvents;

            case 1:
                NotRegisteredEvents notRegisteredEvents = new NotRegisteredEvents();
                return notRegisteredEvents;

                default:
                return fragment;
        }

    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }
}
