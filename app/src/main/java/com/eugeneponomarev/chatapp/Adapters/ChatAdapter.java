package com.eugeneponomarev.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eugeneponomarev.chatapp.MessageActivity;
import com.eugeneponomarev.chatapp.R;
import com.eugeneponomarev.chatapp.model.User;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    public ChatAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_chats, viewGroup, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder viewHolder, int position) {
        final User user = mUsers.get(position);
        viewHolder.usernameRV.setText(user.getUsername());
        if (user.getImageURL().equals("default")){
            viewHolder.profileImageRV.setImageResource(R.drawable.mignon_deadpool);
        } else{
            Glide.with(mContext).load(user.getImageURL()).into(viewHolder.profileImageRV);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId", user.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView usernameRV;
        public ImageView profileImageRV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameRV = (TextView) itemView.findViewById(R.id.usernameRV);
            profileImageRV = (ImageView) itemView.findViewById(R.id.profileImage);
        }
    }
}
