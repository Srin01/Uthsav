package com.example.uthsav.Activities.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uthsav.Activities.Expert.SelectedUserExpert;
import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.User;
import com.example.uthsav.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectedStudentListAdapter extends RecyclerView.Adapter<SelectedStudentListAdapter.MyViewHolder>
{
    private Context context;
    private SelectedUserExpert selectedUserExpert;
    private UserExpert userExpert;
    private String eventId;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference() ;

    public  SelectedStudentListAdapter(Context c, String eventId)
    {
        context = c;
        this.eventId = eventId;
        selectedUserExpert = SelectedUserExpert.getInstance(eventId);
        userExpert = UserExpert.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.selected_student_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        View v = holder.view;
        ImageView studentImage = v.findViewById(R.id.selected_student_image);
        TextView studentName = v.findViewById(R.id.selected_student_name);
        TextView studentEmailId = v.findViewById(R.id.selected_student_emailId);

        studentImage.setImageResource(R.drawable.ic_launcher_background);
        String userId = selectedUserExpert.getUserOfPosition(position);
        User user = userExpert.getUserOfIdFromCache(userId);
        Log.d("myTag", "onBindViewHolder: user object created "+ user);
        studentName.setText(user.getUserName());
        studentEmailId.setText(user.getUserMail());
        StorageReference profileRef = storageReference.child("users/"+ userId+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(studentImage));
    }

    @Override
    public int getItemCount() {
        return selectedUserExpert.getTotalNumberSelectedUsers();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.view = itemView;
        }
    }
}
