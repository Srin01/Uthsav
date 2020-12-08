package com.example.uthsav.Activities.Adapter;

import android.content.Context;
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

    public SelectionListAdapter(Context c)
    {
        context = c;
        selectedEventsExpert = SelectedEventsExpert.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.selection_list_item, parent, false);
        return new MyViewHolder(view);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.view = itemView;
        }
    }
}
