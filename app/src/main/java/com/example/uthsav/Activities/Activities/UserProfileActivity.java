package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.UserProfileActivityAdapter;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static com.example.uthsav.Activities.Activities.HomeActivity.USER_ID;

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
    RecyclerView userProfileActivityRecyclerView;
    FloatingActionButton floatingActionButton;
    String userId;
    User user;

    UserExpert userExpert;

    ImageView qrCode;
    Bitmap bitmap;
    String inputValue ;
    JSONObject jsonObject;
    Uri uri;
    StorageReference storageReference;

    UserProfileActivityAdapter userProfileActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity);

        bindViews();
        //setUpViews();
        userProfileActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userProfileActivityAdapter = new UserProfileActivityAdapter(this,userId);
        userProfileActivityRecyclerView.setAdapter(userProfileActivityAdapter);

        try {
            getDataForQRCodeScanner();
            inputValue = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        qrCodeGenerator();
    }

    private void setUpViews()
    {
        Name.setText(user.getUserName());
        EmailId.setText(user.getUserMail());
        CollegeName.setText(user.getUserCollageName());
        contactNumber.setText(user.getUserPhoneNumber());

    }

    private void getDataForQRCodeScanner() throws JSONException {
        jsonObject = new JSONObject();
        ArrayList<String > eventList = userExpert.getUserOfIdFromCache(userId).getUserParticipatedEvents();
        jsonObject.put("userId", userId);
        jsonObject.put("eventsParticipating",eventList);
    }


    private void bindViews()
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
        userProfileActivityRecyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.edit_button);
        qrCode = findViewById(R.id.qrCode);
        userExpert = UserExpert.getInstance();
        userId =getIntent().getStringExtra(USER_ID);
        storageReference = FirebaseStorage.getInstance().getReference();
        user = userExpert.getUserOfIdFromCache(userId);

    }

    private void qrCodeGenerator()
    {
        QRGEncoder qrgEncoder;

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = Math.min(width, height);
        smallerDimension = smallerDimension * 3 / 4;

        qrgEncoder = new QRGEncoder(
                inputValue, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        try
        {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrCode.setImageBitmap(bitmap);
            uri = getImageUri(this,bitmap);
            uploadCertificateToFireBase(uri);
        }
        catch (WriterException e)
        {
            Toast.makeText(this, "QR Code not generated", Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadCertificateToFireBase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("users/" +userId+"qrCode.jpg");
        final StorageReference finalFileRef = fileRef;
        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(UserProfileActivity.this, "certificate successfully uploaded", Toast.LENGTH_SHORT).show();
            finalFileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(qrCode));
        }).addOnCanceledListener(() -> Toast.makeText(UserProfileActivity.this, "Upload failed", Toast.LENGTH_SHORT).show());
    }

    public void onClickEditUserProfile(View view)
    {
        Intent intent= new Intent(this,EditUserProfileActivity.class);
        startActivity(intent);
        finish();
    }
}