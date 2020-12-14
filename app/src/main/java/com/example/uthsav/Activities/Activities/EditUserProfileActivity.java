package com.example.uthsav.Activities.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfileActivity extends AppCompatActivity

{
    CircleImageView uploadProfilePhoto;
    TextView uploadPhoto;
    FirebaseAuth firebaseAuth;
    UserExpert userExpert;
    StorageReference storageReference;

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
    Toolbar toolbar;

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
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

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

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+ uid+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(uploadProfilePhoto));
    }

    public void uploadPhoto(View view) {
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, 1000);
    }

    public void onClickSave(View view) {

        getValues();

        if(isNotNull(userName, name_textInputLayout) && isNotNull(phoneNumber, phoneNumber_textInputLayout) && isNotNull(collageName, collegeName_textInputLayout)
                && isNotNull(userEmail, emailId_textInputLayout) && isNotNull(userRegisterNumber, registerNumber_textInputLayout))
        {

            Toast.makeText(this, "Editted Successfully", Toast.LENGTH_SHORT).show();
            updateValues();
        }
    }

    private boolean isNotNull(String s, TextInputLayout layout)
    {
        if(s.equals(""))
        {
            layout.setError("Enter a valid Input");
            layout.setErrorEnabled(true);
            return false;
        }
        else
            return true;
    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef = storageReference.child("users/"+ uid+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(EditUserProfileActivity.this, "photo successfully uploaded", Toast.LENGTH_SHORT).show();
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(uploadProfilePhoto));
        }).addOnCanceledListener(() -> Toast.makeText(EditUserProfileActivity.this, "Upload failed", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == RESULT_OK)
            {
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }

    public void onClickToolBar(View view)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void onClickHomeScreen(View view)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }

}