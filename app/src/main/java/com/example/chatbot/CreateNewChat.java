package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.Random;

public class CreateNewChat extends AppCompatActivity {
    private EditText chatName;

    private static String[] profilePictures = new String[] {
            "http://tcap.pbworks.com/f/1435170280/myAvatar.png",
            "https://www.f6s.com/content-resource/profiles/3072512_original.jpg",
            "https://layogroup.net/wp-content/uploads/2019/07/522569-1eWJyL1490726864.png",
            "https://cdn.dribbble.com/users/2364329/screenshots/5930135/aa.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_chat);
        cacheViews();


    }

    private Random random = new Random();


    public void onSubmitNewChat(View view) {
        String chatName = this.chatName.getText().toString();

        if(chatName.isEmpty()){
            Toast.makeText(this,"Title is Empty, Please Insert a Title",Toast.LENGTH_SHORT).show();
            return;
        }

        Date now = new Date();
        Long longTime = now.getTime()/1000;

        Chat chat = new Chat(chatName,profilePictures[random.nextInt(profilePictures.length)],longTime);

        AppDataBase.getInstance(this).getChatDAO().insert(chat);
        finish();
    }


    private void cacheViews(){
        this.chatName = findViewById(R.id.editTextNameChat);
    }

}