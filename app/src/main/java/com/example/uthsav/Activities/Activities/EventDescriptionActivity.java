package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;

import static com.example.uthsav.Activities.Activities.HomeActivity.EVENT_POS;

public class EventDescriptionActivity extends AppCompatActivity
{
    TextView eventName;
    ImageView eventPhoto;
    TextView eventDescription;
    TextView eventTimings;
    TextView eventCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        EventExpert eventExpert = EventExpert.getInstance();
        bindViews();

        int position = getIntent().getIntExtra(EVENT_POS,0);
        Event event = eventExpert.getEventOfPosition(position);

        eventPhoto.setImageResource(R.drawable.ic_launcher_background);
        eventName.setText(event.getEventName());
        eventDescription.setText(event.getEventDescription());
        eventCost.setText(event.getEventCost());
        eventTimings.setText(event.getEventTime());
    }

    private void bindViews()
    {
        eventName = findViewById(R.id.eventName_textView);
        eventPhoto = findViewById(R.id.eventImage);
        eventDescription = findViewById(R.id.eventDescription);
        eventTimings = findViewById(R.id.eventTime);
        eventCost = findViewById(R.id.eventCost);
    }

    public void onClickMessage(View view)
    {
        startActivity(new Intent(this, MessageActivity.class));
    }

    public void onClickBuyTicket(View view)
    {
        startActivity(new Intent(this, SuccessfulRegistrationActivity.class));
    }
}