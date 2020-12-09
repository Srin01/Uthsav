package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.uthsav.R;
import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.Utils;
import com.github.jinatonic.confetti.confetto.BitmapConfetto;
import com.github.jinatonic.confetti.confetto.Confetto;

import java.util.List;
import java.util.Random;

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

    private void confettiDisplay()
    {
//        final List<Bitmap> allPossibleConfetti = Utils.generateConfettiBitmaps(new int[] { Color.BLACK }, 20 /* size */);
//
//        final int numConfetti = allPossibleConfetti.size();
//        final ConfettoGenerator confettoGenerator = new ConfettoGenerator() {
//            @Override
//            public Confetto generateConfetto(Random random) {
//                final Bitmap bitmap = allPossibleConfetti.get(random.nextInt(numConfetti));
//                return new BitmapConfetto(bitmap);
//            }
//        };
//
//        ViewGroup container = constraintLayout;
//
//        final int containerMiddleX = container.getWidth();
//        final int containerMiddleY = container.getHeight();
//        final ConfettiSource confettiSource = new ConfettiSource(containerMiddleX, containerMiddleY);
//
//        new ConfettiManager(this, confettoGenerator, confettiSource, container)
//                .setEmissionDuration(1000)
//                .setEmissionRate(100)
//                .setVelocityX(20, 10)
//                .setVelocityY(100)
//                .setRotationalVelocity(180, 180)
//                .animate();
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
        confettiDisplay();
        CommonConfetti.rainingConfetti(constraintLayout,
                new int[]{Color.parseColor("#f45c2c"), Color.parseColor("#ecca72"), Color.parseColor("#048181"), Color.parseColor("#5a9c7d"), Color.parseColor("#9fbeb2"), Color.parseColor("#366854")})
                .infinite();
    }
}