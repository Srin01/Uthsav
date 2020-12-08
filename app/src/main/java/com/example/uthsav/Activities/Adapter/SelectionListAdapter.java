package com.example.uthsav.Activities.Adapter;

import android.content.Context;
import android.media.MediaDrm;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uthsav.Activities.Expert.SelectedEventsExpert;
import com.example.uthsav.Activities.Expert.SelectedUserExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;

public class SelectionListAdapter extends RecyclerView.Adapter<SelectionListAdapter.MyViewHolder>
{
    private Context context;
    private SelectedEventsExpert selectedEventsExpert;
    OnEventClickListener onEventListener;

    public SelectionListAdapter(Context c,OnEventClickListener onEventListener)
    {
        context = c;
        selectedEventsExpert = SelectedEventsExpert.getInstance();
        this.onEventListener = onEventListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.selection_list_item, parent, false);
        return new MyViewHolder(view, onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        View v = holder.view;
        ImageView eventImage = v.findViewById(R.id.selectionList_eventImage);
        TextView eventName = v.findViewById(R.id.selectionList_eventName);

        //here the data from expert is required
        Event event = selectedEventsExpert.getEventOfPosition(position);
        eventImage.setImageResource(R.drawable.ic_launcher_background);
        eventName.setText(event.getEventName());
    }

    @Override
    public int getItemCount() {
        return selectedEventsExpert.getTotalDoneEvents();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        View view;
        OnEventClickListener onEventClickListener;
        public MyViewHolder(@NonNull View itemView, OnEventClickListener onEventClickListener)
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
