package com.example.chatbot;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity
public class Message {

    public static final int MESSAGE_SEND_BY_PERSON = 0;
    public static final int MESSAGE_SEND_BY_BOT = 1;

    @PrimaryKey (autoGenerate = true)
    private long messageID;
    private String message;
    private long date;
    private int messageType;
    private long chatID;

    public Message(long chatID,String message,long date,int messageType){
        this.chatID = chatID;
        this.message = message;
        this.date = date;
        this.messageType = messageType;
    }

    public long getMessageID() {
        return messageID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public long getDate() {
        return date;
    }

    public int getMessageType() {
        return messageType;
    }

    public long getChatID() {
        return chatID;
    }
}
