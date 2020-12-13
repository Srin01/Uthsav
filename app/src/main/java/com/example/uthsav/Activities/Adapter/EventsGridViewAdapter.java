package com.example.uthsav.Activities.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class EventsGridViewAdapter extends BaseAdapter {

    private Context c;
    private LayoutInflater inflater;
    EventExpert eventExpert;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference() ;

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
        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null) {
            view = inflater.inflate(R.layout.event_item, null);
        }

        ImageView eventImage = view.findViewById(R.id.event_image_gridView);
        TextView eventName = view.findViewById(R.id.event_name_gridView);
        TextView eventOneLineDescription = view.findViewById(R.id.event_oneLine_description_gridView);
        TextView eventPrice = view.findViewById(R.id.event_price_gridView);

        Event event = eventExpert.getEventOfPosition(position);
        StorageReference profileRef = storageReference.child("events/"+ event.getEventId()+"/"+event.getEventId()+".jpeg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(eventImage));
        eventName.setText(event.getEventName());
        eventOneLineDescription.setText(event.getEventDescription());
        eventPrice.setText(event.getEventCost());
        return view;
    }
}

