package com.example.chatbot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";

    private AdapterMessge adapterMessge;
    private static long chatID;
    private EditText personMessageSend;
    private TextView personMessageText;

    public static void startActivity(Context context, long chatId) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_INDEX, chatId);
        context.startActivity(intent);
        chatID = chatId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Log.i("MessageChat","ChatId: " + chatID);

        AppDataBase db = AppDataBase.getInstance(this);
        MessageDAO dao = db.getMessageDAO();
        this.adapterMessge = new AdapterMessge();

        RecyclerView messageRecyclerView = findViewById(R.id.RecyclerViewMessage);
        RecyclerView.LayoutManager layoutManagerMessage = new LinearLayoutManager(this);
        messageRecyclerView.setAdapter(adapterMessge);
        messageRecyclerView.setLayoutManager(layoutManagerMessage);

        if (getIntent().getExtras() != null) {
            long id = getIntent().getExtras().getLong(KEY_INDEX, -1);
            if (id == -1) finish();
            this.chatID = id;

        } else {
            finish();
        }

        cacheViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.adapterMessge.updateListMessage(AppDataBase.getInstance(this).getMessageDAO().getMessageByChat(chatID));
    }

    public void onSendMessage(View view) {

        Date now = new Date();
        Long longTime = now.getTime()/1000;

        String messagePerson = this.personMessageSend.getText().toString();
        Message message = new Message(this.chatID,messagePerson,longTime,Message.MESSAGE_SEND_BY_PERSON);
        AppDataBase.getInstance(this).getMessageDAO().insert(message);
        this.adapterMessge.updateListMessage(AppDataBase.getInstance(this).getMessageDAO().getMessageByChat(chatID));
    }

    private void cacheViews(){
        this.personMessageSend = findViewById(R.id.editTextMessageSendByPerson);
    }
}