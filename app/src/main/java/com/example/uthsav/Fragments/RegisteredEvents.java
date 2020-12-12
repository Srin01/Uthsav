package com.example.uthsav.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uthsav.Activities.Adapter.RegisteredEventAdapter;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class RegisteredEvents extends Fragment
{

    private RecyclerView recyclerView;
    public RegisteredEvents()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_registered_events,container,false);
        recyclerView = view.findViewById(R.id.registeredEvents_recyclerView);
        RegisteredEventAdapter registeredEventAdapter = new RegisteredEventAdapter(getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid());
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      recyclerView.setAdapter(registeredEventAdapter);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}