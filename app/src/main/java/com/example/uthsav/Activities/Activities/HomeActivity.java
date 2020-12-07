package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.EventsGridViewAdapter;
import com.example.uthsav.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeActivity extends AppCompatActivity {

    public static final String EVENT_POS = "eventPosition";
    GridView eventsGridView;
    CarouselView carouselView;

    int[] sampleImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bindViews();

        EventsGridViewAdapter adapter = new EventsGridViewAdapter(this);
        eventsGridView.setAdapter(adapter);

        eventsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //open Event Description activity using intents
                Toast.makeText(HomeActivity.this, "You clicked on an event", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, EventDescriptionActivity.class);
                intent.putExtra(EVENT_POS,position);
                startActivity(intent);
            }
        });

    }

    private void bindViews()
    {
        eventsGridView = findViewById(R.id.events_gridView);
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(3);
        carouselView.setImageListener(imageListener);
    }

    public void onClickShowNotifications(View view)
    {
//        Toast.makeText(this, "Important", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,UserProfileActivity.class);
        startActivity(intent);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}