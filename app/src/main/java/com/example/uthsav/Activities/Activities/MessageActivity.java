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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    if ((chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId)) || (chat.getReceiver().equals(userId) && chat.getSender().equals(firebaseUser.getUid()))) {
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

    }


    private void readMessage(final String myId, final String userId)
    {
        chatList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Log.d("myTag", "onDataChange: data snap shot = " + snapshot.getChildren().toString());
                    Chat chat = snapshot.getValue(Chat.class);
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