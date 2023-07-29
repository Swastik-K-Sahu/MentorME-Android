package com.example.mentorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Explore extends AppCompatActivity {
    TextView explorename;
    FirebaseUser fuser;
    FirebaseDatabase database;
    Button appdevBtn, webdevBtn, electronicsBtn, cricketBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        explorename = findViewById(R.id.explore_name);
        appdevBtn = findViewById(R.id.appdev_Btn);
        webdevBtn = findViewById(R.id.webdev_Btn);
        electronicsBtn = findViewById(R.id.electronics_Btn);
        cricketBtn = findViewById(R.id.cricket_Btn);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference().child("USERS").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                String name = user.getName();
                explorename.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        appdevBtn.setOnClickListener(task ->{
            Intent in = new Intent(Explore.this,MainActivity.class);
            in.putExtra("domain","App Dev");
            startActivity(in);
        });
        webdevBtn.setOnClickListener(task ->{
            Intent in = new Intent(Explore.this,MainActivity.class);
            in.putExtra("domain","Web Dev");
            startActivity(in);
        });
        electronicsBtn.setOnClickListener(task ->{
            Intent in = new Intent(Explore.this,MainActivity.class);
            in.putExtra("domain","Electronics");
            startActivity(in);
        });
        cricketBtn.setOnClickListener(task ->{
            Intent in = new Intent(Explore.this,MainActivity.class);
            in.putExtra("domain","Cricket");
            startActivity(in);
        });
    }
}