package com.example.mentorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText nameEt,emailEt,passwordEt,rePasswordEt;
    String expertise;
    Spinner spinner;
    Button createAccBtn,toLoginBtn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String emailFormat = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    String[] domains = {"App Dev","Web Dev","Electronics","Cricket"};
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
        spinner = findViewById(R.id.spinner);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        ArrayAdapter<String> ad = new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_item,domains);
        ad.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(ad);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                expertise = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        createAccBtn.setOnClickListener(view -> {
            String name = nameEt.getText().toString();
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();
            String rePassword = rePasswordEt.getText().toString();


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
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String uid = task.getResult().getUser().getUid();
                    DatabaseReference reference = database.getReference().child("USERS").child(uid);
                    Log.d("exp",expertise);
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