package com.example.capstone_project_redo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone_project_redo.category.Food;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountPart2 extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    FirebaseAuth uAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_part2);

        uAuth = FirebaseAuth.getInstance();

        final EditText firstname = findViewById(R.id.firstname);
        final EditText lastname = findViewById(R.id.lastname);
        final EditText age = findViewById(R.id.age);
        final EditText username = findViewById(R.id.username);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText province = findViewById(R.id.province);
        final EditText municipality = findViewById(R.id.municipality);
        final EditText locationDesc = findViewById(R.id.et_productDescription);
        final Button registerBtn = findViewById(R.id.registerBtn);
        final Button cancelBtn = findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String emailTxt = extras.getString("email");
                    String passwordTxt = extras.getString("password");
                    deleteUser(emailTxt,passwordTxt);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstnameTxt = firstname.getText().toString();
                final String lastnameTxt = lastname.getText().toString();
                final String ageTxt = age.getText().toString();
                final String usernameTxt = username.getText().toString();
                final String mobileTxt = mobile.getText().toString();
                final String provinceTxt = province.getText().toString();
                final String municipalityTxt = municipality.getText().toString();
                final String locationDescTxt = locationDesc.getText().toString();

                databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");

                if(firstnameTxt.trim().equals("") || lastnameTxt.trim().equals("") || ageTxt.trim().equals("") || usernameTxt.trim().equals("")
                        || mobileTxt.trim().equals("") || provinceTxt.trim().equals("") || municipalityTxt.trim().equals("") || locationDescTxt.trim().equals("")){
                    Toast.makeText(CreateAccountPart2.this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FirebaseUser currentUser = uAuth.getCurrentUser();
                            if (currentUser != null) {
                                uid = currentUser.getUid();
                                Bundle extras = getIntent().getExtras();
                                if (extras != null) {
                                    String emailTxt = extras.getString("email");
                                    String passwordTxt = extras.getString("password");

                                    databaseReference.child("users").child(uid).child("id").setValue(uid);
                                    databaseReference.child("users").child(uid).child("FirstName").setValue(firstnameTxt);
                                    databaseReference.child("users").child(uid).child("LastName").setValue(lastnameTxt);
                                    databaseReference.child("users").child(uid).child("Age").setValue(ageTxt);
                                    databaseReference.child("users").child(uid).child("Username").setValue(usernameTxt);
                                    databaseReference.child("users").child(uid).child("MobileNumber").setValue(mobileTxt);
                                    databaseReference.child("users").child(uid).child("Province").setValue(provinceTxt);
                                    databaseReference.child("users").child(uid).child("Municipality").setValue(municipalityTxt);
                                    databaseReference.child("users").child(uid).child("StallDescription").setValue(locationDescTxt);
                                    databaseReference.child("users").child(uid).child("Password").setValue(passwordTxt);
                                    databaseReference.child("users").child(uid).child("EmailAddress").setValue(emailTxt);

                                    uAuth.signOut();
                                    startActivity(new Intent(CreateAccountPart2.this, LoginActivity.class));
                                    Toast.makeText(CreateAccountPart2.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
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

    private void deleteUser(String email, String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        if (user != null) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(CreateAccountPart2.this, LoginActivity.class));
                                            }
                                        }
                                    });
                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String emailTxt = extras.getString("email");
            String passwordTxt = extras.getString("password");
            deleteUser(emailTxt, passwordTxt);
        }
        startActivity(new Intent(CreateAccountPart2.this, CreateAccountPart1.class));
        finish();
    }
}