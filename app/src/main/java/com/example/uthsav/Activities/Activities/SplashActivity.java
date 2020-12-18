package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.uthsav.Activities.Activities.HomeActivity;
import com.example.uthsav.Activities.Activities.LoginActivity;
import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Expert.OrganiserExpert;
import com.example.uthsav.Activities.Expert.SelectedEventsExpert;
import com.example.uthsav.Activities.Expert.SelectedUserExpert;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

import static com.example.uthsav.Activities.Activities.HomeActivity.USER_ID;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    UserExpert userExpert;
    EventExpert eventExpert;
    SelectedEventsExpert selectedEventsExpert;
    OrganiserExpert organiserExpert;
    Animation topAnim, bottomAnim;
    ImageView utsav, dareToDream;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();

        bindViews();
    }

    private void bindViews()
    {
        utsav = findViewById(R.id.LogoHolder);
        dareToDream = findViewById(R.id.slogan);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        utsav.setAnimation(topAnim);
        dareToDream.setAnimation(bottomAnim);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent;
        intent = firebaseAuth.getCurrentUser() != null ? new Intent(this, HomeActivity.class) : new Intent(this, LoginActivity.class);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            eventExpert = EventExpert.getInstance();
            selectedEventsExpert = SelectedEventsExpert.getInstance();
            userExpert = UserExpert.getInstance();
            organiserExpert = OrganiserExpert.getInstance();
            startActivity(intent);
            finish();
        }, 3000);

    }
}