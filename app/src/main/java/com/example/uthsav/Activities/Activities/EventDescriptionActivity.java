package com.example.uthsav.Activities.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.uthsav.Activities.Activities.HomeActivity.EVENT_POS;
import static com.example.uthsav.Activities.Activities.MessageActivity.ORGANISER_ID;
import static com.example.uthsav.Activities.Activities.SelectionListActivity.EVENT_ID;

public class EventDescriptionActivity extends AppCompatActivity
{
    TextView eventName;
    ImageView eventPhoto;
    TextView eventDescription;
    TextView eventTimings;
    TextView eventCost;
    UserExpert userExpert;
    Event event;
    String userId;
    User user;

    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        EventExpert eventExpert = EventExpert.getInstance();
        bindViews();

        int position = getIntent().getIntExtra(EVENT_POS,0);
        event = eventExpert.getEventOfPosition(position);

        if(position == 0) eventPhoto.setImageResource(R.drawable.treasure_hunt);
        if(position == 1) eventPhoto.setImageResource(R.drawable.kagada);
        eventName.setText(event.getEventName());
        eventDescription.setText(event.getEventDescription());
        eventCost.setText(event.getEventCost());
        eventTimings.setText(event.getEventTime());
    }

    private void bindViews()
    {
        eventName = findViewById(R.id.eventName_textView);
        eventPhoto = findViewById(R.id.eventImage);
        eventDescription = findViewById(R.id.eventDescription);
        eventTimings = findViewById(R.id.eventTime);
        eventCost = findViewById(R.id.eventCost);
        userExpert = UserExpert.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = userExpert.getUserOfIdFromCache(userId);
    }

    public void onClickMessage(View view)
    {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(ORGANISER_ID,event.getOrganiserId());
        startActivity(intent);
    }

    public void onClickBuyTicket(View view)
    {
        int choice = userExpert.addEventToUserData(userId,event.getEventId());
        if(choice == 0)
        {
            Toast.makeText(this, "Already registered to event", Toast.LENGTH_SHORT).show();
        }
        else {
//        startActivity(new Intent(this, SuccessfulRegistrationActivity.class));
            Uri uri = new Uri.Builder()
                    .scheme("upi")
                    .authority("pay")
                    .appendQueryParameter("pa", "kavyakbhat12@okaxis")
                    .appendQueryParameter("pn", "Kavya Bhat")
//                    .appendQueryParameter("mc", "your-merchant-code")
//                    .appendQueryParameter("tr", "your-transaction-ref-id")
                    .appendQueryParameter("tn", "THMN")
                    .appendQueryParameter("am", "1")
                    .appendQueryParameter("cu", "INR")
//                .appendQueryParameter("url", "your-transaction-url")
                    .build();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_PAY_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                if(data!= null)
                {
                    String trxt = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);

                }
                else
                    {
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            }
            else {
                //when user simply back without payment
                Log.e("UPI", "onActivityResult: " + "Return data is null");
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }

    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(EventDescriptionActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(EventDescriptionActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(EventDescriptionActivity.this, "Payment Successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,SuccessfulRegistrationActivity.class);
                user.addEventToUserList(event.getEventId());
                intent.putExtra(EVENT_ID, event.getEventId());
                startActivity(intent);
                finish();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(EventDescriptionActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(EventDescriptionActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            @SuppressLint("MissingPermission") NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public void onClickToolBar(View view)
    {
        startActivity(new Intent(this,HomeActivity.class));
    }

    public void onClickHomeScreen(View view)
    {
        startActivity(new Intent(this,HomeActivity.class));
    }
}
