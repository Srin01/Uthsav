package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.uthsav.Activities.Activities.HomeActivity;
import com.example.uthsav.Activities.Activities.LoginActivity;
import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Expert.SelectedEventsExpert;
import com.example.uthsav.Activities.Expert.SelectedUserExpert;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    UserExpert userExpert;
    EventExpert eventExpert;
    SelectedEventsExpert selectedEventsExpert;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        userExpert = UserExpert.getInstance();
        eventExpert = EventExpert.getInstance();
        selectedEventsExpert = SelectedEventsExpert.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent;
        intent = firebaseAuth.getCurrentUser() != null ? new Intent(this, HomeActivity.class) : new Intent(this, LoginActivity.class);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 1000);

    }
}