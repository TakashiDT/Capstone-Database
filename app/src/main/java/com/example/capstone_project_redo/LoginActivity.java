package com.example.capstone_project_redo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone_project_redo.nav.HomePage;
import com.example.capstone_project_redo.nav.MyProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth uAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button createAccount = (Button) findViewById(R.id.createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CreateAccountPart1.class));
            }
        });

        Button goToHome = (Button) findViewById(R.id.logInGuest);
        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HomePage.class));
            }
        });

        uAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            Toast.makeText(getApplicationContext(),"User already logged in.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, HomePage.class));
        }else uAuth.signOut();

        final EditText email = findViewById(R.id.login_email);
        final EditText password = findViewById(R.id.login_password);
        final Button loginBtn = findViewById(R.id.btn_continue);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();

                if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Input both Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    uAuth.signInWithEmailAndPassword(emailTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (uAuth.getCurrentUser().getUid().equals("y0HGN02WYaTK4GaefHjpSQUNzyz2")) {
                                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                        }
                                        else if (uAuth.getCurrentUser().isEmailVerified()) {
                                            databaseReference = database.getReference("users").child(uAuth.getCurrentUser().getUid());
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String activation = (String) snapshot.child("activate").getValue();
                                                    if (activation.equals("true")) {
                                                        startActivity(new Intent(LoginActivity.this, HomePage.class));
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Please wait 2-3 working days for Admin to enable your account", Toast.LENGTH_SHORT).show();
                                                        uAuth.signOut();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    uAuth.signOut();
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
                                            uAuth.signOut();
                                        }
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                                        uAuth.signOut();
                                    }
                                }
                            });
                }
            }
        });

    }
}