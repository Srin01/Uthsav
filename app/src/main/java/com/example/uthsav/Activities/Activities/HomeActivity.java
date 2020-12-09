package com.example.uthsav.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.EventsGridViewAdapter;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.phonepe.intent.sdk.api.PhonePe;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    public static final String EVENT_POS = "eventPosition";
    private static final int STORAGE_PERMISSION_CODE =1234 ;
    public static final String USER_ID ="userId" ;
    GridView eventsGridView;
    CarouselView carouselView;
    DrawerLayout drawerLayout;
    UserExpert userExpert;
    FirebaseAuth firebaseAuth;
    String userId;
    User user;

    int[] sampleImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        bindViews();
        setUpNavigationDrawerIcon();
        setUpListeners();
        checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE);


        EventsGridViewAdapter adapter = new EventsGridViewAdapter(this);
        eventsGridView.setAdapter(adapter);

        eventsGridView.setOnItemClickListener((adapterView, view, position, id) -> {
            //open Event Description activity using intents

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
        userExpert = UserExpert.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        Log.d("myTag", "bindViews: user id " + userId + " obtained");
        userExpert.printUsers();
        user = userExpert.getUserOfIdFromCache(userId);
        Log.d("myTag", "bindViews: user object " + user + " obtained");

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
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.text_View_profile_name);
        userName.setText(user.getUserName());
    }

    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                HomeActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            HomeActivity.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(HomeActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void onClickOpenUserProfile(View view)
    {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(USER_ID,userId);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

         if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HomeActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(HomeActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void setUpListeners()
    {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item1:
                   startActivity(new Intent(HomeActivity.this,EventListActivity.class));
                    break;
                case R.id.item2:
                    startActivity(new Intent(HomeActivity.this,MapActivity.class));
                    break;
                case R.id.item3:
                    Toast.makeText(HomeActivity.this, "You clicked help", Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        });

    }
}