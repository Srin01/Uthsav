package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.uthsav.R;
import com.github.jinatonic.confetti.CommonConfetti;

public class SuccessfulRegistrationActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    TextView successfulRegistration, eventName;
    Button continueButton;
    ConstraintLayout constraintLayout;

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
    }

    public void onClickBomb(View view)
    {
        CommonConfetti.rainingConfetti(constraintLayout,
                new int[]{Color.parseColor("#f45c2c"), Color.parseColor("#ecca72"), Color.parseColor("#048181"), Color.parseColor("#5a9c7d"), Color.parseColor("#9fbeb2"), Color.parseColor("#366854")})
                .infinite();
    }
}