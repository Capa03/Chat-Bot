package com.example.chatbot;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDAO {

    @Query("SELECT * FROM Chat")
    List<Chat> getAllChat();

    @Query("SELECT * FROM Chat WHERE chatId = :chatId")
    Chat getChatById(long chatId);

    @Query("SELECT * FROM Chat ORDER BY lastMessageDate DESC")
    List<Chat> getOrderedChat();

    @Query("SELECT profilePictureId FROM CHAT WHERE chatId =:chatId")
    String getPictureByChatId(long chatId);
    @Insert
    void insert(Chat chat);

    @Delete
    void delete(Chat chat);

    @Update
    void update(Chat chat);
}
