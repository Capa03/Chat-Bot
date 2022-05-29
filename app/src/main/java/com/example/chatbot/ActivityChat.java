package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActivityChat extends AppCompatActivity implements AdapterChat.ChatAdapterEventListener {

    private AdapterChat adapterChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView chatRecyclerView = findViewById(R.id.RecyclerViewChat);
        this.adapterChat = new AdapterChat(this);
        RecyclerView.LayoutManager layoutManagerChat = new LinearLayoutManager(this);
        chatRecyclerView.setAdapter(adapterChat);
        chatRecyclerView.setLayoutManager(layoutManagerChat);

    }

    @Override
    public void onChatClicked(long chatID) {
        MessageActivity.startActivity(this,chatID);
    }
}