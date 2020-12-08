package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.uthsav.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfileActivity extends AppCompatActivity

{
    CircleImageView uploadProfilePhoto;
    TextView uploadPhoto;

    TextInputLayout name_textInputLayout;
    TextInputLayout collegeName_textInputLayout;
    TextInputLayout phoneNumber_textInputLayout;
    TextInputLayout emailId_textInputLayout;
    TextInputLayout registerNumber_textInputLayout;


    TextInputEditText name_editText;
    TextInputEditText phoneNumber_editText;
    TextInputEditText collegeName_editText;
    TextInputEditText emailId_editText;
    TextInputEditText registerNumber_editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        bindViews();

    }

    private  void bindViews()
    {
        uploadProfilePhoto = findViewById(R.id.uploadPhoto_imageView);
        uploadPhoto = findViewById(R.id.uploadphoto_textview);

        name_textInputLayout = findViewById(R.id.Name_textInputLayout);
        collegeName_textInputLayout = findViewById(R.id.CollegeName_textInputLayout);
        phoneNumber_textInputLayout = findViewById(R.id.PhoneNumber_textInputLayout);
        emailId_textInputLayout = findViewById(R.id.EmailId_textInputLayout);
        registerNumber_textInputLayout = findViewById(R.id.registerNumber_textInputLayout);

        name_editText = findViewById(R.id.name);
        phoneNumber_editText = findViewById(R.id.PhoneNumber_editText);
        collegeName_editText = findViewById(R.id.College_name);
        emailId_editText = findViewById(R.id.emailId_editText);
        registerNumber_editText = findViewById(R.id.registerNumber);



    }
}