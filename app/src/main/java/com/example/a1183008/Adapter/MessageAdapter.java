package com.example.a1183008.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1183008.Model.Chat;
import com.example.a1183008.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat){
        this.mChat = mChat;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        if(chat.getTime()!=null && !chat.getTime().trim().equals("")) {
            holder.time_tv.setText(holder.convertTime(chat.getTime()));
        }
        if (position == mChat.size()-1){
            if (chat.isIsseen()){
                holder.txt_seen.setText("Dilihat");
            } else {
                holder.txt_seen.setText("Terkirim");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public TextView time_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            time_tv = itemView.findViewById(R.id.time_tv);
        }
        public String convertTime(String time){
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            String dateString = formatter.format(new Date(Long.parseLong(time)));
            return dateString;
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}