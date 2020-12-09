package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfileActivity extends AppCompatActivity

{
    CircleImageView uploadProfilePhoto;
    TextView uploadPhoto;
    FirebaseAuth firebaseAuth;
    UserExpert userExpert;

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

    String userName;
    String phoneNumber;
    String collageName;
    String userEmail;
    String userRegisterNumber;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        bindViews();

    }

    private void updateValues()
    {
        User user = userExpert.getUserOfIdFromCache(uid);
        user.setUserMail(userEmail);
        user.setUserCollageName(collageName);
        user.setUserPhoneNumber(phoneNumber);
        user.setUserRegisterNumber(userRegisterNumber);
        user.setUserParticipatedEvents(null);
        userExpert.addUserToDB(uid, user);
        finish();
    }

    private void getValues()
    {
        userName = Objects.requireNonNull(name_editText.getText()).toString();
        phoneNumber = Objects.requireNonNull(phoneNumber_editText.getText()).toString();
        collageName = Objects.requireNonNull(collegeName_editText.getText()).toString();
        userEmail = Objects.requireNonNull(emailId_editText.getText()).toString();
        userRegisterNumber = Objects.requireNonNull(registerNumber_editText.getText()).toString();
    }

    private  void bindViews()
    {
        userExpert = UserExpert.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();

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

    public void onClickSave(View view) {
        getValues();
        updateValues();
    }
}