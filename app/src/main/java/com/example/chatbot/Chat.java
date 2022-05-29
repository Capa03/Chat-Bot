package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
@Dao
public class Chat {

    @PrimaryKey(autoGenerate = true)
    public long chatId;
    public String chatName;


    public Chat(long chatId, String chatName){
        this.chatId = chatId;
        this.chatName =chatName;
    }

    public long getChatID() {
        return chatId;
    }

    public String getChatName() {
        return chatName;
    }

}
