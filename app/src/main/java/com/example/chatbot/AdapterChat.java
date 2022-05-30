package com.example.chatbot;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ChatViewHolder> {

    public List<Chat> chatList;
    private final ChatAdapterEventListener eventListener;

    public AdapterChat(ChatAdapterEventListener eventListener){
        this.eventListener = eventListener;
        this.chatList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterChat.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row,parent,false);
        return new ChatViewHolder(layout, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.ChatViewHolder holder, int position) {
        Chat chat = this.chatList.get(position);
        holder.setChatNameTextView(chat.getChatName());
        holder.setDate(chat.getDate());
        Glide.with(holder.context).load(chat.getProfilePicture()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onChatClicked(chat.getChatID());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                eventListener.onChatLongClicked(chat.getChatID());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.chatList.size();
    }

    public void updateList(List<Chat> allChat){
        this.chatList = allChat;
        notifyDataSetChanged();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        private TextView chatNameTextView;
        private TextView messageTextView;
        private ImageView imageView;
        private TextView date;
        private Context context;


        public ChatViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.chatNameTextView = itemView.findViewById(R.id.textViewChatName);
            this.messageTextView = itemView.findViewById(R.id.textViewChatMessage);
            this.imageView = itemView.findViewById(R.id.imageViewChatRow);
            this.date = itemView.findViewById(R.id.textViewDateChat);
            this.context = context;
        }

        public void setChatNameTextView(String chatNameTextView) {
            this.chatNameTextView.setText(chatNameTextView);
        }

        public void setMessageTextView(String messageTextView) {
            this.messageTextView.setText(messageTextView);
        }



        public void setDate(long uxDate) {

            Date now = new Date();
            Long longTime = now.getTime()/1000;



            /*
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTimeInMillis(uxDate*1000);

            int messageOfTheDayHour = calendar.HOUR_OF_DAY;
            int messageOfTheDayMinutes = calendar.MINUTE;

            Date now = new Date();
            Long longTime = now.getTime()/1000;

             */

            /*
            se messageDAte = date de hoje então
            apresentar hora
             */

             /*


            if(messageOfTheDay == uxHour){
                String hour = String.valueOf(calendar.HOUR_OF_DAY);
                this.date.setText(hour);
            }
             */


            /*
            se date = dia de ontem então
            apresentar a data de ontem
                    */

            /*
            se não apresentar data
            */

            this.date.setText(String.valueOf(uxDate));

        }
    }

    public interface ChatAdapterEventListener{
        void onChatClicked(long chatID);
        void onChatLongClicked(long chatID);
    }
}
