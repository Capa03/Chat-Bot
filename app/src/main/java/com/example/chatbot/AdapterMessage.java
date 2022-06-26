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

import java.util.ArrayList;
import java.util.List;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.MessageViewHolder> {

    private List<Message> messageList;
    private final MessageAdapterEventListener eventListener;

    public AdapterMessage(MessageAdapterEventListener eventListener){
        this.eventListener = eventListener;
        this.messageList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterMessage.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;

        if(viewType == Message.MESSAGE_SEND_BY_PERSON){
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_person_text,parent,false);
        }else {
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_bot_text, parent, false);
        }
        return new MessageViewHolder(layout,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMessage.MessageViewHolder holder, int position) {

            Message message = this.messageList.get(position);

            String picture = AppDataBase.getInstance(holder.context).getChatDAO().getPictureByChatId(message.getChatID());
            holder.setMessage(this.messageList.get(position).getMessage());

            if(message.getMessageType() == Message.MESSAGE_SEND_BY_PERSON){
                holder.avatar.setImageResource(R.drawable.avatar);
            }else {
                holder.avatar.setImageURI(Uri.parse(picture));
            }


            holder.message.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    eventListener.onMessageLongClicked(message.getMessageID());
                    return false;
                }
            });
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
        private Context context;
        private TextView message;
        private TextView dateMessage;
        private ImageView avatar;
        public MessageViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == Message.MESSAGE_SEND_BY_PERSON){
                this.message = itemView.findViewById(R.id.textViewSendByPerson);
                this.avatar = itemView.findViewById(R.id.imageViewMessagePerson);

            }else if(viewType== Message.MESSAGE_SEND_BY_BOT) {
                this.message = itemView.findViewById(R.id.textViewSendByBot);
                this.avatar = itemView.findViewById(R.id.imageViewMessageBot);
            }
            this.dateMessage = itemView.findViewById(R.id.textViewDateChat);
        }

        public void setMessage(String message) {
            this.message.setText(message);
        }

    }

    public interface MessageAdapterEventListener{
        void onMessageLongClicked(long messageId);
    }

}

