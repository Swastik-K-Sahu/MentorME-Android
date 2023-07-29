package com.example.mentorme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText loginEmailEt, loginPasswordEt;
    Button loginBtn,toSignUpBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //finding IDs & Instance
        loginBtn = findViewById(R.id.loginBtn);
        toSignUpBtn = findViewById(R.id.toSignUpBtn);
        loginEmailEt = findViewById(R.id.loginEmailEt);
        loginPasswordEt = findViewById(R.id.loginPasswordEt);
        auth = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(view -> {
            String email = loginEmailEt.getText().toString();
            String password = loginPasswordEt.getText().toString();
            if(email.isEmpty()){
                loginEmailEt.setError("Enter valid E-Mail");
            } else if (password.length()<6) {
                loginPasswordEt.setError("Password must be atleast 6 characters");
            } else
            {   auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Login.this, Explore.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });

        toSignUpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
            finish();
        });
    }
}