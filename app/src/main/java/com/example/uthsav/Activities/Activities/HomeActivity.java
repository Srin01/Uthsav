package com.example.uthsav.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.EventsGridViewAdapter;
import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

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
    Toolbar toolbar;
    EventExpert eventExpert;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference() ;
    FirebaseFirestore firebaseFirestore;

    int[] sampleImages = {R.drawable.corousel_1, R.drawable.corousel_2, R.drawable.corousel, R.drawable.corousel_4, R.drawable.corousel_5, R.drawable.corousel_6, R.drawable.corousel_7};

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

    @SuppressLint("NewApi")
    private void bindViews()
    {
        eventsGridView = findViewById(R.id.events_gridView);
        carouselView = findViewById(R.id.carouselView);
        toolbar = findViewById(R.id.toolbar);
        firebaseFirestore = FirebaseFirestore.getInstance();
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        userExpert = UserExpert.getInstance();
        eventExpert = EventExpert.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        Log.d("myTag", "bindViews: user id " + userId + " obtained");
        userExpert.printUsers();
        user = userExpert.getUserOfIdFromCache(userId);
        Log.d("myTag", "bindViews: user object " + user + " obtained");
        setSupportActionBar(toolbar);
    }

    public void onClickHomeScreen(View view)
    {
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
        ImageView imageView = headerView.findViewById(R.id.circleImageViewProfile);
        StorageReference profileRef = storageReference.child("users/"+userId+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(imageView));
        if(user!= null) {
            userName.setText(user.getUserName());
            Toast.makeText(this, "Please check your network connection and Re-open the app", Toast.LENGTH_SHORT).show();
        }
        else
            userName.setText("User Name");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return true;
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
                    startActivity(new Intent(HomeActivity.this, EventListActivity.class));
                    break;
                case R.id.item2:
                    startActivity(new Intent(HomeActivity.this, MapActivity.class));
                    break;
                case R.id.item3:
                    Toast.makeText(HomeActivity.this, "You clicked help", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logOutItem:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, SplashActivity.class));
                    finish();
                    break;
            }
            return false;
        });

    }
}