package com.example.chatbot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements AdapterMessage.MessageAdapterEventListener {

    private static final String CHAT_ID = "index";

    private AdapterMessage adapterMessage;
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
        this.adapterMessage = new AdapterMessage(this);

        this.messageRecyclerView = findViewById(R.id.RecyclerViewMessage);
        RecyclerView.LayoutManager layoutManagerMessage = new LinearLayoutManager(this);
        messageRecyclerView.setAdapter(adapterMessage);
        messageRecyclerView.setLayoutManager(layoutManagerMessage);
        if(!messages.isEmpty()) {
            messageRecyclerView.smoothScrollToPosition(messages.size() - 1);
        }
        cacheViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.adapterMessage.updateListMessage(AppDataBase.getInstance(this).getMessageDAO().getMessageByChat(this.chat.getChatID()));
    }

    public void onSendMessage(View view) {
        String messagePerson = this.personMessageSend.getText().toString();

        if(!messagePerson.isEmpty()){
            Message message = new Message(this.chat.getChatID(),messagePerson,System.currentTimeMillis(),Message.MESSAGE_SEND_BY_PERSON);
            AppDataBase.getInstance(this).getMessageDAO().insert(message);

            String messageBot = BotAnswer.getBotAnswer(messagePerson,chat.chatId, this);

            // Fazer comparação da String ou ver se existe na DB
                Message messageFromBot = new Message(this.chat.getChatID(),messageBot,System.currentTimeMillis(),Message.MESSAGE_SEND_BY_BOT);
                AppDataBase.getInstance(this).getMessageDAO().insert(messageFromBot);


            //Reset da EditText da message
            this.personMessageSend.setText("");
            this.adapterMessage.updateListMessage(AppDataBase.getInstance(this).getMessageDAO().getMessageByChat(this.chat.getChatID()));
            this.messageRecyclerView.smoothScrollToPosition(this.adapterMessage.getItemCount()-1);

            this.chat.setLastMessageDate(messageFromBot.getDate());
            AppDataBase.getInstance(this).getChatDAO().update(this.chat);
        }

    }


    private void cacheViews(){
        this.personMessageSend = findViewById(R.id.editTextMessageSendByPerson);
    }

    @Override
    public void onMessageLongClicked(long messageId) {
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar mensagem");
        builder.setMessage("Deseja eliminar esta mensagem?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Message message = AppDataBase.getInstance(context).getMessageDAO().getMessageById(messageId);
                AppDataBase.getInstance(context).getMessageDAO().delete(message);
                adapterMessage.updateListMessage(AppDataBase.getInstance(context).getMessageDAO().getAllMessageFromChat(chat.chatId));
                Toast.makeText(context,"Messagem apagada com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(context,"Nenhuma Alteração", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
