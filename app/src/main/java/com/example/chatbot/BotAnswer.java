package com.example.chatbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BotAnswer {

    private static List<Interaction> interactions = new ArrayList<>();


    public static String getBotAnswer(String message){

       List<Interaction> interactions = getInteraction();

       for(Interaction interaction: interactions){

           if(interaction.getMessageReceived().contains(message)){

               return interaction.getMessageToSend().toLowerCase(Locale.ROOT);
           }
       }

       return "I don't have answer for that";

    }


    private static List<Interaction> getInteraction() {

        if(interactions.isEmpty()){

            interactions.add(new Interaction("ola","Ola tudo bem?"));
            interactions.add(new Interaction("sim","Ainda bem que esta tudo bem"));

        }

        return interactions;
    }

    private BotAnswer(){

    }

}
