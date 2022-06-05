package com.example.chatbot;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BotAnswer extends AppCompatActivity {

    private static List<Interaction> interactions = new ArrayList<>();


    public static String getBotAnswer(String message, long chatID, Context context){

       List<Interaction> interactions = getInteraction();

       for(Interaction interaction: interactions){

           if(interaction.getMessageReceived().contains(message)){

               return interaction.getMessageToSend().toLowerCase(Locale.ROOT);

           }else if(message.equals("/delete")){

               List<Message> allMessageFromChat = AppDataBase.getInstance(context).getMessageDAO().getAllMessageFromChat(chatID);
               for (Message messages: allMessageFromChat ) {
                   AppDataBase.getInstance(context).getMessageDAO().delete(messages);
               }
               return null;
           }
       }

       return "I don't have answer for that";

    }


    private static List<Interaction> getInteraction() {

        if(interactions.isEmpty()){

            interactions.add(new Interaction("ola","Ola tudo bem?"));
            interactions.add(new Interaction("sim","Ainda bem que esta tudo bem!"));

        }

        return interactions;
    }

    private BotAnswer(){

    }

}
