package com.example.chatbot;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


    @Database(entities = {Chat.class, Message.class}, version = 1)
    public abstract class AppDataBase extends RoomDatabase {

        public abstract ChatDAO getChatDAO();
        public abstract MessageDAO getMessageDAO();
        private static AppDataBase INSTANCE;

        public static AppDataBase getInstance(Context context) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDataBase.class, "ChatBotDatabase")
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }

    }
