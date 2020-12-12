package com.example.uthsav.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.MessageAdapter;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.Chat;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.Activities.Notifications.APIService;
import com.example.uthsav.Activities.Notifications.Client;
import com.example.uthsav.Activities.Notifications.Data;
import com.example.uthsav.Activities.Notifications.MyResponse;
import com.example.uthsav.Activities.Notifications.Sender;
import com.example.uthsav.Activities.Notifications.Token;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.uthsav.Activities.Drivers.EventDriver.TAG;

public class MessageActivity extends AppCompatActivity {
    public static final String ORGANISER_ID = "userId";
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    Intent intent;
    String organiserId;

    APIService apiService;

    ImageButton imageButtonSend;
    EditText editText_Message;
    MessageAdapter messageAdapter;
    CircleImageView circleImageViewProfile;
    TextView profileName;
    UserExpert userExpert;
    List<Chat> chatList;
    User Organiser;

    RecyclerView recyclerView;
    ValueEventListener seenListener;
    DatabaseReference firebaseDatabase;
    DatabaseReference reference;

    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        intent = getIntent();
        organiserId = intent.getStringExtra(ORGANISER_ID);

        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener((view -> finish()));


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userExpert = UserExpert.getInstance();
        Organiser = userExpert.getUserOfIdFromCache(organiserId);
        imageButtonSend = findViewById(R.id.send_button);
        circleImageViewProfile = findViewById(R.id.profile_image_Toolbar);
        profileName = findViewById(R.id.userName);



        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        circleImageViewProfile.setImageResource(R.drawable.ic_launcher_background);
        profileName.setText(Organiser.getUserName());

        reference = FirebaseDatabase.getInstance().getReference("Users").child(organiserId);
        editText_Message = findViewById(R.id.text_send);
        recyclerView = findViewById(R.id.recyclerView_Messages);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(organiserId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessage(firebaseUser.getUid(), organiserId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imageButtonSend.setOnClickListener(view -> {
            notify = true;
            String message = editText_Message.getText().toString();
            if (!message.equals(""))
            {
                sendMessage(firebaseUser.getUid(), organiserId, message);
                editText_Message.setText("");
            }
            else
            {
                Toast.makeText(MessageActivity.this, "Please enter a valid text", Toast.LENGTH_SHORT).show();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(organiserId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                readMessage(firebaseUser.getUid(), organiserId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(organiserId);

    }

    private void seenMessage(String userId)
    {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("chats");
        seenListener = firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot: datasnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if ((chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId))) {
                        Map<String ,Object> map = new HashMap<>();
                        map.put("isSeen",true);
                        snapshot.getRef().updateChildren(map);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isSeen",false);

        databaseReference.child("chats").push().setValue(hashMap);

        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(sender)
                .child(receiver);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(organiserId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(organiserId)
                .child(firebaseUser.getUid());
        chatRefReceiver.child("id").setValue(firebaseUser.getUid());


        final String msg = message;
//        sendNotification(receiver,firebaseUser.getDisplayName(), msg);
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = (String) dataSnapshot.child("username").getValue();
//                Log.d("myTag", "onDataChange: "+ dataSnapshot.child("username"));
                if (notify) {
                    sendNotification(receiver,userName, msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendNotification(String receiver, String userName, String message)
    {
        DatabaseReference tokens =FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Token token1 = snapshot.getValue(Token.class);
                    Log.d("myTag", "onDataChange: Token = " + token1.getToken());
                    Data data = new Data(firebaseUser.getUid(),R.mipmap.ic_launcher,userName+": "+message,"New Message",organiserId);
                    assert token1 != null;
                    Sender sender = new Sender(data, token1.getToken());
                    Log.d(TAG, "OnDataChange: sending to  = " + sender.to + " " + sender.data);
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(@NotNull Call<MyResponse> call, @NotNull Response<MyResponse> response) {
                                    if(response.code() == 200)
                                    {
                                        assert response.body() != null;
                                        Log.d(TAG, "Onresponse: sented to  " + sender.to);
                                        if(response.body().success != 1) {
                                            Toast.makeText(MessageActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Toast.makeText(MessageActivity.this, "Failed again", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void readMessage(final String myId, final String userId)
    {
        chatList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Log.d("myTag", "onDataChange: data snap shot = " + snapshot.getChildren().toString());
                    Chat chat = snapshot.getValue(Chat.class);
                    chat.setSeen((Boolean) snapshot.child("isSeen").getValue());
                    if((chat.getReceiver().equals(myId) && chat.getSender().equals(userId)) || (chat.getReceiver().equals(userId) && chat.getSender().equals(myId)))
                    {
                        chatList.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, chatList);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseDatabase.removeEventListener(seenListener);
    }
}