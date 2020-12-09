package com.example.uthsav.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.uthsav.Activities.Adapter.MessageAdapter;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.Chat;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    public static final String ORGANISER_ID = "userId";
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    Intent intent;
    String organiserId;

    ImageButton imageButtonSend;
    EditText editText_Message;
    MessageAdapter messageAdapter;
    UserExpert userExpert;
    List<Chat> chatList;
    User Organiser;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        intent = getIntent();
        organiserId = intent.getStringExtra(ORGANISER_ID);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userExpert = UserExpert.getInstance();
        Organiser = userExpert.getUserOfIdFromCache(organiserId);
        imageButtonSend = findViewById(R.id.send_button);
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
    }

    private void sendMessage(String sender, String receiver, String message)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

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
}