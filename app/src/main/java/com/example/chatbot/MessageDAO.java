package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDAO {

    @Query("SELECT * FROM Message")
    List<Message> getAllMessage();

    @Query("SELECT * FROM MESSAGE WHERE messageID = :messageID")
    Message getMessageById(long messageID);


    @Query("SELECT * FROM MESSAGE WHERE chatID = :chatID")
    List<Message> getMessageByChat(long chatID);

    @Insert
    void insert(Message message);

    @Delete
    void delete(Message message);
}
