package com.example.capstone_project_redo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button goToLogIn = (Button)findViewById(R.id.logInGuest);
        goToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccount.this, LoginActivity.class));
            }
        });


        final EditText firstname = findViewById(R.id.firstname);
        final EditText lastname = findViewById(R.id.lastname);
        final EditText age = findViewById(R.id.age);
        final EditText province = findViewById(R.id.province);
        final EditText municipality = findViewById(R.id.municipality);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText email = findViewById(R.id.email);

        final Button registerBtn = findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstnameTxt = firstname.getText().toString();
                final String lastnameTxt = lastname.getText().toString();
                final String ageTxt = age.getText().toString();
                final String provinceTxt = province.getText().toString();
                final String municipalityTxt = municipality.getText().toString();
                final String usernameTxt = username.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String mobileTxt = mobile.getText().toString();
                final String emailTxt = email.getText().toString();

                if(firstnameTxt.isEmpty() || lastnameTxt.isEmpty() || ageTxt.isEmpty() || provinceTxt.isEmpty() || municipalityTxt.isEmpty() || usernameTxt.isEmpty() || passwordTxt.isEmpty() || mobileTxt.isEmpty() || emailTxt.isEmpty()){
                    Toast.makeText(CreateAccount.this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernameTxt)){
                                Toast.makeText(CreateAccount.this, "Username is already Exist", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(usernameTxt).child("First Name").setValue(firstnameTxt,lastnameTxt);
                                databaseReference.child("users").child(usernameTxt).child("Last Name").setValue(lastnameTxt);
                                databaseReference.child("users").child(usernameTxt).child("Age").setValue(ageTxt);
                                databaseReference.child("users").child(usernameTxt).child("Province").setValue(provinceTxt);
                                databaseReference.child("users").child(usernameTxt).child("Municipality").setValue(municipalityTxt);
                                databaseReference.child("users").child(usernameTxt).child("Username").setValue(usernameTxt);
                                databaseReference.child("users").child(usernameTxt).child("Password").setValue(passwordTxt);
                                databaseReference.child("users").child(usernameTxt).child("Mobile Number").setValue(mobileTxt);
                                databaseReference.child("users").child(usernameTxt).child("Email Address").setValue(emailTxt);

                                Toast.makeText(CreateAccount.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });









    }
}