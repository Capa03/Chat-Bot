package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat,parent,false);
        return new ChatViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.ChatViewHolder holder, int position) {
        Chat chatlist = this.chatList.get(position);
        holder.setChatNameTextView(chatlist.getChatName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onChatClicked(chatlist.getChatID());
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

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chatNameTextView = itemView.findViewById(R.id.textViewChatName);
            this.messageTextView = itemView.findViewById(R.id.textViewChatMessage);
        }

        public void setChatNameTextView(String chatNameTextView) {
            this.chatNameTextView.setText(chatNameTextView);
        }

        public void setMessageTextView(String messageTextView) {
            this.messageTextView.setText(messageTextView);
        }
    }

    public interface ChatAdapterEventListener{
        void onChatClicked(long chatID);
    }
}
