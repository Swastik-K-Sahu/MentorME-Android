package com.example.mentorme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Chat extends AppCompatActivity {
    ImageView sendBtn, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        //finding IDs
        sendBtn = findViewById(R.id.sendBtn);
        backBtn = findViewById(R.id.backBtn);
        
        backBtn.setOnClickListener(view ->{
            Intent intent = new Intent(Chat.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}