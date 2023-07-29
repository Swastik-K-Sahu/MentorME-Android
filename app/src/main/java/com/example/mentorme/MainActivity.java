package com.example.mentorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mainRecyclerView;
    UserAdapter userAdapter;
    FirebaseDatabase database;
    ArrayList<Users> usersArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //finding IDs & getting Instance
        mainRecyclerView = findViewById(R.id.main_RecyclerView);
        database = FirebaseDatabase.getInstance();
        String domain = getIntent().getStringExtra("domain");
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        DatabaseReference reference = database.getReference().child("USERS");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear();
                for(DataSnapshot data:snapshot.getChildren()){
                    Users user = data.getValue(Users.class);
                    assert user != null;
                    if((!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(user.getUserID())) && user.getExpertise().equals(domain)) usersArrayList.add(user);
                    }
                userAdapter = new UserAdapter(MainActivity.this,usersArrayList);
                mainRecyclerView.setAdapter(userAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error",error.getMessage());
            }
        });
    }
}