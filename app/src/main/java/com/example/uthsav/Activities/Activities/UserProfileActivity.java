package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uthsav.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class UserProfileActivity extends AppCompatActivity
{

    ImageView profilePhoto;
    TextView Name;
    TextView CollegeName;
    TextView EmailIdLabel;
    TextView EmailId;
    TextView ContactLabel;
    TextView contactNumber;
    TextView registerNumberLabel;
    TextView registerNumber;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity);

        bindViews();
    }

    private  void bindViews()
    {
        profilePhoto = findViewById(R.id.profilePicture);
        Name = findViewById(R.id.Name_textView);
        CollegeName = findViewById(R.id.CollegeName_textView);
        EmailIdLabel = findViewById(R.id.EmailLabel_textView);
        EmailId = findViewById(R.id.EmailId_textView);
        ContactLabel = findViewById(R.id.phoneNumberLabel_textview);
        contactNumber = findViewById(R.id.phoneNumber_textview);
        registerNumberLabel = findViewById(R.id.RegisterNumberLabel_textview);
        registerNumber = findViewById(R.id.RegisterNumber);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.edit_button);
    }


    public void onClickEditUserProfile(View view)
    {
        Intent intent = new Intent(this,EditUserProfileActivity.class);
        startActivity(intent);
    }
}