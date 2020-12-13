package com.example.uthsav.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "myTag";
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    UserExpert userExpert;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();

        setUpGoogleSignIn();
    }

    private void bindViews()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        userExpert = UserExpert.getInstance();
        Button signInButton = findViewById(R.id.button_signIn);
        signInButton.setOnClickListener(view -> {
            Intent googleSignInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(googleSignInIntent, RC_SIGN_IN);
        });
    }

    private void setUpGoogleSignIn()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed In Successful", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e)
        {
            System.out.println(e.getStatusCode() + "");
            Toast.makeText(this, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
            //FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct)
    {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
            if(task.isSuccessful())
            {
                Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                updateUI(user);
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser fUser)
    {
        if(fUser != null)
        {
            String userid = fUser.getUid();

            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", userid);
            hashMap.put("username", fUser.getDisplayName());
            hashMap.put("imageURL", "default");
            hashMap.put("status", "offline");
            hashMap.put("search", Objects.requireNonNull(fUser.getDisplayName()).toLowerCase());

            String personName = fUser.getDisplayName();
            String personEmail = fUser.getEmail();
            userExpert.addUserToDB(fUser.getUid(), new User(fUser.getUid(),personEmail,personName,"gjsgjaghd","789787971" ,"ECVU","19GACSE060",new ArrayList<>()));

            reference.setValue(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Log.d(TAG, "updateUI: user id created to db " + fUser.getUid());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }  //            Toast.makeText(LoginActivity.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();

    }
}