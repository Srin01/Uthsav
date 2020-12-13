package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.github.jinatonic.confetti.CommonConfetti;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.uthsav.Activities.Activities.SelectionListActivity.EVENT_ID;

public class SuccessfulRegistrationActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    TextView successfulRegistration, eventName;
    Button continueButton;
    ConstraintLayout constraintLayout;
    Toolbar toolbar;
    UserExpert userExpert;
    User user;
    FirebaseUser firebaseUser;
    String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_registration);

        bindViews();

        successfulRegistration.setAnimation(topAnim);
        eventName.setAnimation(topAnim);
        continueButton.setAnimation(bottomAnim);
    }

    private void bindViews()
    {
        successfulRegistration = findViewById(R.id.successful_registration_textView);
        eventName = findViewById(R.id.successfulRegistration_eventName);
        continueButton = findViewById(R.id.continue_button);
        constraintLayout = findViewById(R.id.constraintView);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userExpert = UserExpert.getInstance();
        user = userExpert.getUserOfIdFromCache(firebaseUser.getUid());
        eventId = getIntent().getStringExtra(EVENT_ID);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }

    public void onClickBomb(View view)
    {
        CommonConfetti.rainingConfetti(constraintLayout,
                new int[]{Color.parseColor("#f45c2c"), Color.parseColor("#ecca72"), Color.parseColor("#048181"), Color.parseColor("#5a9c7d"), Color.parseColor("#9fbeb2"), Color.parseColor("#366854")})
                .infinite();
        user.addEventToUserList(eventId);
        userExpert.addEventToUserData(firebaseUser.getUid(),eventId);
        Handler handler = new Handler();

        handler.postDelayed(this::finish, 2000);
    }

    public void onClickToolBar(View view)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }
}