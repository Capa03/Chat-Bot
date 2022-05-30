package com.example.chatbot;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Chat {

    @PrimaryKey(autoGenerate = true)
    public long chatId;
    public String chatName;
    public String profilePicture;
    public long date;

    public Chat( String chatName, String profilePicture,long date){
        this.chatName =chatName;
        this.profilePicture = profilePicture;
        this.date = date;
    }

    public long getChatID() {
        return chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public long getDate() {
        return date;
    }
}
