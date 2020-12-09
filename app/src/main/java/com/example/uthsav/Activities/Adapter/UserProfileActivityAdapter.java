package com.example.uthsav.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uthsav.Activities.Drivers.UserDriver;
import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserProfileActivityAdapter extends RecyclerView.Adapter<UserProfileActivityAdapter.MyViewHolder>
{
    private Context context;
    OnEventClickListener onEventListener;
    ArrayList<String> eventIDs;
    UserExpert userExpert;
    EventExpert eventExpert;
    UserDriver userDriver;
    String userId;

    public UserProfileActivityAdapter(Context c, String userId) {
        context = c;
        userExpert = UserExpert.getInstance();
        userDriver = new UserDriver();
        eventExpert = EventExpert.getInstance();
        this.userId = userId;
        eventIDs = userExpert.getUserOfIdFromCache(userId).getUserParticipatedEvents();
    }

    @NonNull
    @Override
    public UserProfileActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.selection_list_item, parent, false);
        return new MyViewHolder(view, onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View v = holder.view;
        ImageView eventImage = v.findViewById(R.id.selectionList_eventImage);
        TextView eventName = v.findViewById(R.id.selectionList_eventName);

        eventName.setText(eventExpert.getEventOfIdFromCache(eventIDs.get(position)).getEventName());
        eventImage.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        View view;
        UserProfileActivityAdapter.OnEventClickListener onEventClickListener;
        public MyViewHolder(@NonNull View itemView, UserProfileActivityAdapter.OnEventClickListener onEventClickListener)
        {
            super(itemView);
            this.view = itemView;
            this.onEventClickListener = onEventClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEventClickListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventClickListener
    {
        void onEventClick(int position);
    }
}