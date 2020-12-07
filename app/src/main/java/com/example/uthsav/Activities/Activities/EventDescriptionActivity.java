package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;

import static com.example.uthsav.Activities.Activities.HomeActivity.EVENT_POS;

public class EventDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        EventExpert eventExpert = EventExpert.getInstance();
        ImageView eventImage  = findViewById(R.id.event_image);
        TextView eventName = findViewById(R.id.event_name);
        TextView eventOneLineDescription = findViewById(R.id.event_oneLine_description);
        TextView eventPrice = findViewById(R.id.event_price);
        TextView eventDate = findViewById(R.id.event_date_time);

        int position = getIntent().getIntExtra(EVENT_POS,0);
        Event event = eventExpert.getEventOfPosition(position);

        eventImage.setImageResource(R.drawable.ic_launcher_background);
        eventName.setText(event.getEventName());
        eventOneLineDescription.setText(event.getEventDescription());
        eventPrice.setText(event.getEventCost());
        eventDate.setText(event.getEventTime());
    }

    public void onClickMessage(View view)
    {
    }

    public void onClickBuyTicket(View view)
    {

    }
}