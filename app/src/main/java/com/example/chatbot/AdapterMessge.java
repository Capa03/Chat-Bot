package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterMessge extends RecyclerView.Adapter<AdapterMessge.MessageViewHolder> {

    private List<Message> messageList;

    public AdapterMessge(List<Message> messages){
        this.messageList = messages;
    }

    @NonNull
    @Override
    public AdapterMessge.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;

        if(viewType == Message.MESSAGE_SEND_BY_PERSON){
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_person_text,parent,false);

        }else if(viewType == Message.MESSAGE_SEND_BY_BOT){
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_bot_text,parent,false);
        }else{
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.welcome_message,parent,false);
        }
        return new MessageViewHolder(layout,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMessge.MessageViewHolder holder, int position) {
            holder.setMessage(messageList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return this.messageList.size();
    }

    public void updateListMessage(List<Message> allMessage){
        this.messageList = allMessage;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getMessageType();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView message;
        private TextView dateMessage;

        public MessageViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == Message.MESSAGE_SEND_BY_PERSON){
                this.message = itemView.findViewById(R.id.textViewSendByPerson);
            }else if(viewType== Message.MESSAGE_SEND_BY_BOT) {
                this.message = itemView.findViewById(R.id.textViewSendByBot);
            }else {
                this.message = itemView.findViewById(R.id.textViewWelcomeMessage);
            }

            this.dateMessage = itemView.findViewById(R.id.textViewDateChat);
        }

        public void setMessage(String message) {
            this.message.setText(message);
        }

    }
}
