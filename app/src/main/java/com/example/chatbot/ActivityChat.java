package com.example.chatbot;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    protected void onStart() {
        super.onStart();
        this.adapterChat.updateList(AppDataBase.getInstance(this).getChatDAO().getOrderedChat());
    }

    @Override
    public void onChatClicked(long chatID) {
        MessageActivity.startActivity(this,chatID);
    }

    public void onCreateChat(View view) {
        Intent intent = new Intent(this, CreateNewChat.class);
        startActivity(intent);
    }

    @Override
    public void onChatLongClicked(long chatID) {
        Context context = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elminar Contacto");
        builder.setMessage("Queres mesmo elminar o chat?");

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Código a ser executado quando o utilizador clica em Cancel
                Toast.makeText(context,"Nenhuma Alteração", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Elminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Código a ser executado quando o utilizador clica em Delete

                Chat chat = AppDataBase.getInstance(ActivityChat.this).getChatDAO().getChatById(chatID);
                AppDataBase.getInstance(ActivityChat.this).getChatDAO().delete(chat);
                ActivityChat.this.adapterChat.updateList(AppDataBase.getInstance(ActivityChat.this).getChatDAO().getOrderedChat());
                List<Message> allMessageFromChat = AppDataBase.getInstance(ActivityChat.this).getMessageDAO().getAllMessageFromChat(chatID);
                for (Message message: allMessageFromChat ) {
                    AppDataBase.getInstance(ActivityChat.this).getMessageDAO().delete(message);
                }
                Toast.makeText(context,"Chat apagado com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}