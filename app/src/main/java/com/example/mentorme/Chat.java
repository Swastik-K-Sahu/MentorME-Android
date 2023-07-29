package com.example.mentorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Chat extends AppCompatActivity {
    CardView sendBtn;
    TextView nameTV;
    EditText inputET;
    FirebaseUser fuser;
    DatabaseReference reference;
    FirebaseDatabase database;
    String receiverUid,receiverName;
    RecyclerView chat_recyclerView;
    ChatAdapter chatAdapter;
    ArrayList<MsgModel> chatAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        sendBtn = findViewById(R.id.sendBtn);
        inputET = findViewById(R.id.inputET);
        nameTV = findViewById(R.id.nameTV);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        receiverUid = getIntent().getStringExtra("userID");
        receiverName = getIntent().getStringExtra("userName");

        chat_recyclerView = findViewById(R.id.chat_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chat_recyclerView.setLayoutManager(linearLayoutManager);

        sendBtn.setOnClickListener(view -> {
            String msg = inputET.getText().toString();
            if(msg.equals("")) Toast.makeText(Chat.this,"Enter message",Toast.LENGTH_LONG).show();
            else sendMessage(fuser.getUid(),receiverUid,msg);
            inputET.setText("");
        });

        reference = FirebaseDatabase.getInstance().getReference("USERS").child(receiverUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTV.setText(receiverName);
                readMessage(fuser.getUid(),receiverUid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void sendMessage(String senderUid, String receiverUid, String message){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("CHATS").push().setValue(new MsgModel(senderUid,receiverUid,message));
    }
    private void readMessage(String senderUid, String receiverUid){
        chatAL = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("CHATS");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatAL.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MsgModel msg = dataSnapshot.getValue(MsgModel.class);
                        assert msg!= null;
                        if(msg.getSenderUid().equals(senderUid) && msg.getReceiverUid().equals(receiverUid)
                        || msg.getSenderUid().equals(receiverUid) && msg.getReceiverUid().equals(senderUid)) {
                            chatAL.add(msg);
                        }
                        chatAdapter = new ChatAdapter(Chat.this,chatAL);
                        chat_recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}