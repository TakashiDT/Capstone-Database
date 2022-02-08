package com.example.capstone_project_redo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com/");
    FirebaseAuth uAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button createAccount = (Button) findViewById(R.id.createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CreateAccount.class));
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
        /*
        String uid = uAuth.getCurrentUser().getUid();
        */
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(getApplicationContext(),"User already logged in.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, HomePage.class));
        }

        final EditText email = findViewById(R.id.login_email);
        final EditText password = findViewById(R.id.login_password);
        final Button loginBtn = findViewById(R.id.logInBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent passData = getIntent();
                final String firstnameTxt = passData.getStringExtra("firstName");
                final String lastnameTxt = passData.getStringExtra("lastName");
                final String ageTxt = passData.getStringExtra("age");
                final String provinceTxt = passData.getStringExtra("province");
                final String municipalityTxt = passData.getStringExtra("municipality");
                final String usernameTxt = passData.getStringExtra("username");
                final String mobileTxt = passData.getStringExtra("mobile");

                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();



                if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Input Email or Password", Toast.LENGTH_SHORT).show();
                } else {
                    uAuth.signInWithEmailAndPassword(emailTxt,passwordTxt)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String uid = uAuth.getCurrentUser().getUid();
                                        databaseReference.child("users").child(uid).child("First Name").setValue(firstnameTxt);
                                        databaseReference.child("users").child(uid).child("Last Name").setValue(lastnameTxt);
                                        databaseReference.child("users").child(uid).child("Age").setValue(ageTxt);
                                        databaseReference.child("users").child(uid).child("Province").setValue(provinceTxt);
                                        databaseReference.child("users").child(uid).child("Municipality").setValue(municipalityTxt);
                                        databaseReference.child("users").child(uid).child("Username").setValue(usernameTxt);
                                        databaseReference.child("users").child(uid).child("Mobile Number").setValue(mobileTxt);

                                        startActivity(new Intent(LoginActivity.this, HomePage.class));
                                        Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}