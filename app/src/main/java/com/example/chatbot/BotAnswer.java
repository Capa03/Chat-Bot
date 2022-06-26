package com.example.chatbot;

import android.content.Context;
import android.widget.Toast;

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
           }
       }
        return "I don't have answer for that";
    }


    private static List<Interaction> getInteraction() {

        if(interactions.isEmpty()){

            interactions.add(new Interaction("ola","Ola tudo bem?"));
            interactions.add(new Interaction("sim","Ainda bem que esta tudo bem!"));
            interactions.add(new Interaction("sim e contigo","Ainda bem que esta tudo bem, Tambem Obrigado!, Pergunta-me algo!"));
            interactions.add(new Interaction("tudo e contigo","Ainda bem que esta tudo bem, Tambem Obrigado!, Pergunta-me algo!"));
            interactions.add(new Interaction("como te chamas","Chat Bot e eu sei que o teu é, professor!"));
            interactions.add(new Interaction("professor","Sim,meu senhor!"));
            interactions.add(new Interaction("musica perferida","hmmm... The Beatles e a tua ?"));
            interactions.add(new Interaction("filme favorito","Hobbit o segundo filme! E o teu ?"));
            interactions.add(new Interaction("qual o teu sonho","ir a Lua, adoro o universo !"));
            interactions.add(new Interaction("Estas a gostar de falar comigo","Estou adorar! Ficaria a falar por horas!"));
        }

        return interactions;
    }

    private BotAnswer(){

    }

}
