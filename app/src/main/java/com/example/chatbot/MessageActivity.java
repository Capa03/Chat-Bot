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
import android.widget.EditText;

import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private static final String CHAT_ID = "index";

    private AdapterMessge adapterMessge;
    private Chat chat;
    private EditText personMessageSend;
    private RecyclerView messageRecyclerView;

    public static void startActivity(Context context, long chatId) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(CHAT_ID, chatId);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        long chatId;
        try {
            chatId = getIntent().getExtras().getLong(CHAT_ID);
        } catch (Exception e) {
            finish();
            return;
        }

        Log.i("MessageChat","ChatId: " + chatId);

        this.chat = AppDataBase.getInstance(this).getChatDAO().getChatById(chatId);

        AppDataBase db = AppDataBase.getInstance(this);
        MessageDAO dao = db.getMessageDAO();
        List<Message> messages = dao.getMessageByChat(this.chat.getChatID());
        this.adapterMessge = new AdapterMessge(messages);

        this.messageRecyclerView = findViewById(R.id.RecyclerViewMessage);
        RecyclerView.LayoutManager layoutManagerMessage = new LinearLayoutManager(this);
        messageRecyclerView.setAdapter(adapterMessge);
        messageRecyclerView.setLayoutManager(layoutManagerMessage);
        if(!messages.isEmpty()) {
            messageRecyclerView.smoothScrollToPosition(this.adapterMessge.getItemCount() - 1);
        }
        cacheViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.adapterMessge.updateListMessage(AppDataBase.getInstance(this).getMessageDAO().getMessageByChat(this.chat.getChatID()));
    }

    public void onSendMessage(View view) {
        String messagePerson = this.personMessageSend.getText().toString();
        if(!messagePerson.isEmpty()){
            Message message = new Message(this.chat.getChatID(),messagePerson,System.currentTimeMillis(),Message.MESSAGE_SEND_BY_PERSON);
            AppDataBase.getInstance(this).getMessageDAO().insert(message);

            String messageBot = this.personMessageSend.getText().toString();
            Message messageFromBot = new Message(this.chat.getChatID(),messageBot,System.currentTimeMillis(),Message.MESSAGE_SEND_BY_BOT);
            AppDataBase.getInstance(this).getMessageDAO().insert(messageFromBot);

            this.personMessageSend.setText("");
            this.adapterMessge.updateListMessage(AppDataBase.getInstance(this).getMessageDAO().getMessageByChat(this.chat.getChatID()));
            this.messageRecyclerView.smoothScrollToPosition(this.adapterMessge.getItemCount()-1);

            this.chat.setLastMessageDate(messageFromBot.getDate());
            AppDataBase.getInstance(this).getChatDAO().update(this.chat);
        }

    }

    private void cacheViews(){
        this.personMessageSend = findViewById(R.id.editTextMessageSendByPerson);
    }
}