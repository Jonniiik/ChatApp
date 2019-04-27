package com.eugeneponomarev.chatapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eugeneponomarev.chatapp.R;
import com.eugeneponomarev.chatapp.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageURL;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageURL) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int position) {
        Chat chat = mChat.get(position);

        viewHolder.textShowMessage.setText(chat.getMessage());

        if (imageURL.equals("default")){
            viewHolder.profileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(imageURL).into(viewHolder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textShowMessage;
        public ImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textShowMessage = (TextView) itemView.findViewById(R.id.textShowMessage);
            profileImage = (ImageView) itemView.findViewById(R.id.profileImage);
        }
    }

    @Override
    public int getItemViewType(int position){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
