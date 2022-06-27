package com.example.chatbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;

public class CreateNewChat extends AppCompatActivity {
    private EditText chatName;
    private ImageView mImageView;
    private Button bAddImage;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_chat);
        cacheViews();

        //Foi atravez deste tutorial que fiz o accesso a galeria do android: https://www.youtube.com/watch?v=O6dWwoULFI8&ab_channel=AtifPervaiz

        bAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ver Permissoes
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //Permissao nao garantida
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //Mostrar popup runTime da Permission
                        requestPermissions(permissions,PERMISSION_CODE);
                    }else{
                        // Permissao aceite
                        pickImageFromGallery();
                    }
                }else{
                    // Se o System OS Esta a baixo do Lollipop
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission e garantida
                    pickImageFromGallery();
                } else {
                    //Permission e rejeitada
                    Toast.makeText(this, "Permissao rejeitada!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //Set da image
            // https://stackoverflow.com/questions/17356312/converting-of-uri-to-string
          Uri selectedImageUri = data.getData();
          this.id = selectedImageUri.toString();
          //PreView
          mImageView.setImageURI(selectedImageUri);

        }
    }


    public void onSubmitNewChat(View view) {
        String chatName = this.chatName.getText().toString();

        if(chatName.isEmpty()){
            Toast.makeText(this,"Title is Empty, Please Insert a Title",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!this.id.isEmpty()){
            Chat chat = new Chat(chatName,this.id,System.currentTimeMillis(), System.currentTimeMillis());
            AppDataBase.getInstance(this).getChatDAO().insert(chat);
            finish();
        }else {
            Chat chat = new Chat(chatName, "", System.currentTimeMillis(), System.currentTimeMillis());
            AppDataBase.getInstance(this).getChatDAO().insert(chat);
            finish();
        }
    }


    private void cacheViews(){
        this.chatName = findViewById(R.id.editTextNameChat);
        this.mImageView = findViewById(R.id.imageViewAddChatPreview);
        this.bAddImage = findViewById(R.id.buttonAddNewChatChooseImage);
    }

}