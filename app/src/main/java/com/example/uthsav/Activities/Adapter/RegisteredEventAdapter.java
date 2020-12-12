package com.example.uthsav.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;

import java.util.ArrayList;
import java.util.List;

public  class RegisteredEventAdapter  extends RecyclerView.Adapter<RegisteredEventAdapter.MyViewHolder>
{
    Context context;
    String userId;

    private User user;
    UserExpert userExpert ;
    ArrayList<String> registeredEvents ;
    EventExpert eventExpert;

    public RegisteredEventAdapter(Context context, String UserId)
    {
        this.context = context;
        this.userId = UserId;
        userExpert = UserExpert.getInstance();
        user = userExpert.getUserOfIdFromCache(UserId);
        registeredEvents = user.getUserParticipatedEvents();
        eventExpert = EventExpert.getInstance();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.registeredevents_item,parent,false);
        MyViewHolder myviewHolder = new MyViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
       holder.eventName.setText(eventExpert.getEventOfIdFromCache(registeredEvents.get(position)).getEventName());
       holder.eventPhoto.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return registeredEvents.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView eventPhoto;
        private TextView eventName;


        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventName);
            eventPhoto = itemView.findViewById(R.id.eventPhoto);
        }
    }

}

