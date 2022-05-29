package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MessageActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";

    public static void startActivity(Context context, long contactId) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_INDEX, contactId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }
}