package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uthsav.R;

public class EventDescriptionActivity extends AppCompatActivity {

    TextView eventName;
    ImageView eventPhoto;
    TextView eventDescription;
    TextView eventTimings;
    TextView eventCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        bindViews();
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
    }

    public void onClickBuyTicket(View view)
    {

    }
}