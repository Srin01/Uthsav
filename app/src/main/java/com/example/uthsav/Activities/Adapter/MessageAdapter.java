package com.example.uthsav.Activities.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uthsav.Activities.Expert.UserExpert;
import com.example.uthsav.Activities.Modal.Chat;
import com.example.uthsav.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyCourseViewHolder>
{
    private Context context;
    private List<Chat> chats;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private UserExpert userExpert;
    StorageReference storageReference;
    StorageReference profileRef;
    private ImageView imageView;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chats)
    {
        this.context = context;
        this.chats = chats;
    }

    @NonNull
    @Override
    public MyCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        storageReference = FirebaseStorage.getInstance().getReference();

        if (viewType == MSG_TYPE_RIGHT) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.chat_item_right, parent, false);
            return new MyCourseViewHolder(view);
        }
        else
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.chat_item_left, parent, false);
            return new MyCourseViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyCourseViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.show_message.setText(chat.getMessage());
        profileRef = storageReference.child("users/"+chat.getSender()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(holder.profile_image));
        if(position == chats.size()-1)
        {
            if(chat.IsisSeen()) {
                holder.seen_text.setText("seen");
            }
            else
                {
            holder.seen_text.setText("Delivered");
            }
        }
        else {
            holder.seen_text.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount()
    {
        return chats.size();
    }

    public static class MyCourseViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public TextView show_message;
        public ImageView profile_image;
        public TextView seen_text;
        public MyCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            seen_text = view.findViewById(R.id.text_seen);
            show_message = view.findViewById(R.id.show_message);
            profile_image = view.findViewById(R.id.profile_image);
        }

    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}
