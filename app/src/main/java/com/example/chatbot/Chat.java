package com.example.chatbot;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Chat {

    @PrimaryKey(autoGenerate = true)
    public long chatId;
    public String chatName;
    public String profilePictureId;
    public long date;
    public long lastMessageDate;

    public Chat(String chatName, String profilePictureId, long date, long lastMessageDate){
        this.chatName =chatName;
        this.profilePictureId = profilePictureId;
        this.date = date;
        this.lastMessageDate = lastMessageDate;
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

    public String getProfilePictureId() {
        return profilePictureId;
    }

    public long getDate() {
        return date;
    }

    public void setLastMessageDate(long lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }
}
