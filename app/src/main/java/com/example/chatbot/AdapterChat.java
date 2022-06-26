package com.example.chatbot;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Chat chatPosition = this.chatList.get(position);

        Message lastMessage = AppDataBase.getInstance(holder.context).getMessageDAO().getLastMessageFromChat(chatPosition.getChatID());

        holder.setChatNameTextView(chatPosition.getChatName());

        if (lastMessage != null) {
            holder.setDate(lastMessage.getDate());
            holder.setLastMessage(lastMessage.getMessage());
        } else {
            holder.setDate(0);
            holder.setLastMessage("");
        }

        //https://stackoverflow.com/questions/17356312/converting-of-uri-to-string
        if(!chatPosition.getProfilePictureId().isEmpty()){
            holder.imageView.setImageURI(Uri.parse(chatPosition.getProfilePictureId()));
        }else{
            holder.imageView.setImageResource(R.drawable.avatar);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onChatClicked(chatPosition.getChatID());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                eventListener.onChatLongClicked(chatPosition.getChatID());
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

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        private static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        private static final SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");

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


        public void setLastMessage(String message){
            this.messageTextView.setText(message);
        }


        // https://stackoverflow.com/questions/3006150/how-to-check-if-a-date-object-equals-yesterday
        public void setDate(long lastMessageTime) {
            if (lastMessageTime == 0) {
                this.date.setText("");
            } else {

                Calendar messageCalendar = Calendar.getInstance();
                messageCalendar.setTimeInMillis(lastMessageTime);

                Calendar nowCalendar = Calendar.getInstance();
                nowCalendar.setTimeInMillis(System.currentTimeMillis());

                if (messageCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR) && messageCalendar.get(Calendar.DAY_OF_YEAR ) == nowCalendar.get(Calendar.DAY_OF_YEAR)) {
                    this.date.setText(sdfTime.format(new Date(lastMessageTime)));
                    //hora
                }else if (messageCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR) && messageCalendar.get(Calendar.DAY_OF_YEAR) == nowCalendar.get(Calendar.DAY_OF_YEAR) - 1) {
                    this.date.setText("Ontem");
                    //ontem
                }else{
                    this.date.setText(sdfDate.format(new Date(lastMessageTime)));
                    //data
                }
            }
        }
    }

    public interface ChatAdapterEventListener{
        void onChatClicked(long chatID);
        void onChatLongClicked(long chatID);
    }
}
