package com.example.mentorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText nameEt,emailEt,passwordEt,rePasswordEt,expertiseEt;
    Button createAccBtn,toLoginBtn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String emailFormat = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //finding IDs & getting Instance
        createAccBtn = findViewById(R.id.createAccBtn);
        toLoginBtn = findViewById(R.id.toLoginBtn);
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        rePasswordEt = findViewById(R.id.rePasswordEt);
        expertiseEt = findViewById(R.id.expertiseEt);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        createAccBtn.setOnClickListener(view -> {
            String name = nameEt.getText().toString();
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();
            String rePassword = rePasswordEt.getText().toString();
            String expertise = expertiseEt.getText().toString();

            if (name.length() == 0) {
                nameEt.setError("Name cannot be empty");
            } else if (!email.matches(emailFormat)) {
                emailEt.setText("");
                emailEt.setError("Enter a valid E-Mail");
            } else if (password.length() < 6) {
                passwordEt.setError("Password must be atleast 6 chars");
            } else if (!password.equals(rePassword)) {
                rePasswordEt.setText("");
                rePasswordEt.setError("Password does not match");
            } else if (expertise.length() == 0) {
                expertiseEt.setError("Mandatory field");
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String uid = task.getResult().getUser().getUid();
                    DatabaseReference reference = database.getReference().child("USERS").child(uid);

                    reference.setValue(new Users(name, email, password, expertise,uid)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
                });

            }
        });

        toLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}