package com.example.uthsav.Activities.Adapter;

import android.content.Context;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.uthsav.Activities.Drivers.UserDriver.TAG;

public  class RegisteredEventAdapter  extends RecyclerView.Adapter<RegisteredEventAdapter.MyViewHolder>
{
    Context context;
    String userId;

    private User user;
    UserExpert userExpert ;
    ArrayList<String> registeredEvents ;
    EventExpert eventExpert;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference() ;

    public RegisteredEventAdapter(Context context, String UserId)
    {
        this.context = context;
        this.userId = UserId;
        userExpert = UserExpert.getInstance();
        user = userExpert.getUserOfIdFromCache(UserId);
        registeredEvents = user.getUserParticipatedEvents();
        Log.d(TAG, "RegisteredEventAdapter: got events " + registeredEvents);
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
        Event event = eventExpert.getEventOfIdFromCache(registeredEvents.get(position));
       holder.eventName.setText(event.getEventName());
       StorageReference profileRef = storageReference.child("events/"+ event.getEventId()+"/"+event.getEventId()+".jpeg");
       profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(holder.eventPhoto));
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

