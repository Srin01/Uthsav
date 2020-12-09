package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.SelectionListAdapter;
import com.example.uthsav.Activities.Adapter.UserProfileActivityAdapter;
import com.example.uthsav.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.WriterException;

import org.w3c.dom.Text;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

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

    ImageView qrCode;
    Bitmap bitmap;
    String inputValue = "hello";
    UserProfileActivityAdapter userProfileActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity);

        bindViews();

        userProfileActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userProfileActivityAdapter = new UserProfileActivityAdapter(this);
        userProfileActivityRecyclerView.setAdapter(userProfileActivityAdapter);

        qrCodeGenerator();
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
        }
        catch (WriterException e)
        {
            Toast.makeText(this, "QR Code not generated", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickEditUserProfile(View view)
    {
        Intent intent= new Intent(this,EditUserProfileActivity.class);
        startActivity(intent);
    }
}