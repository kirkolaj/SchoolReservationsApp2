package com.example.schoolreservationsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Toolbar toolbar;
    ProgressBar progressBar;
    EditText userEmail;
    EditText userPass;
    Button userLogin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.etUserEmail);
        userPass = findViewById(R.id.etUserPassword);
        userLogin = findViewById(R.id.btnUserLogIn);

        //toolbar.setTitle("Login");

        firebaseAuth = FirebaseAuth.getInstance();

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(),
                        userPass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()) {
                                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                }else{
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage()
                                                , Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
    }
}
