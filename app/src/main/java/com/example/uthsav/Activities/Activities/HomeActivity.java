package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.EventsGridViewAdapter;
import com.example.uthsav.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeActivity extends AppCompatActivity {

    public static final String EVENT_POS = "eventPosition";
    GridView eventsGridView;
    CarouselView carouselView;
    DrawerLayout drawerLayout;

    int[] sampleImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        bindViews();
        setUpNavigationDrawerIcon();


        EventsGridViewAdapter adapter = new EventsGridViewAdapter(this);
        eventsGridView.setAdapter(adapter);

        eventsGridView.setOnItemClickListener((adapterView, view, position, id) -> {
            //open Event Description activity using intents
            Toast.makeText(HomeActivity.this, "You clicked on an event", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, EventDescriptionActivity.class);
            intent.putExtra(EVENT_POS,position);
            startActivity(intent);
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
        Intent intent = new Intent(this, SelectionListActivity.class);
        startActivity(intent);
    }

    ImageListener imageListener = (position, imageView) -> imageView.setImageResource(sampleImages[position]);


    public void setUpNavigationDrawerIcon()
    {
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.on_drawer_opened, R.string.on_drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void onClickOpenUserProfile(View view)
    {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}