package com.example.uthsav.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;

public class EventsGridViewAdapter extends BaseAdapter {

    private Context c;
    private LayoutInflater inflater;
    EventExpert eventExpert;

    public EventsGridViewAdapter(Context c)
    {
        this.c = c;
        eventExpert = EventExpert.getInstance();
    }

    @Override
    public int getCount() {
        return eventExpert.getTotalEvents();
    }

    @Override
    public Object getItem(int position) {
        return eventExpert.getEventOfPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(inflater == null)
        {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null)
        {
            view = inflater.inflate(R.layout.event_item, null);
        }

        ImageView eventImage = view.findViewById(R.id.event_image);
        TextView eventName = view.findViewById(R.id.event_name);
        TextView eventOneLineDescription = view.findViewById(R.id.event_oneLine_description);
        TextView eventPrice = view.findViewById(R.id.event_price);

        Event event = eventExpert.getEventOfPosition(position);

        eventImage.setImageResource(R.drawable.ic_launcher_background);
        //eventImage.setImageResource(get pic of event dynamically);
        //get the text details dynamically
        eventName.setText(event.getEventName());
        eventOneLineDescription.setText(event.getEventDescription());
        eventPrice.setText(event.getEventCost());
        return view;
    }
}
