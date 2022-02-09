package com.example.capstone_project_redo.nav;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_project_redo.CreateAccount;
import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityMyProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyProfileActivity extends DrawerBaseActivity {

    ActivityMyProfileBinding activityMyProfileBinding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");

    TextView profileUser, profileFullName, profileAge, profileAddress, profileMobile, profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProfileBinding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(activityMyProfileBinding.getRoot());
        allocateActivityTitle("Profile");

        profileUser = findViewById(R.id.tv_profileUser);
        profileFullName = findViewById(R.id.tv_profileFullName);
        profileAge = findViewById(R.id.tv_profileAge);
        profileAddress = findViewById(R.id.tv_profileAddress);
        profileMobile = findViewById(R.id.tv_profileMobile);
        profileEmail = findViewById(R.id.tv_profileEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(this, "" + user.getUid(), Toast.LENGTH_SHORT).show();

        databaseReference = database.getReference("users").child(user.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = (String) snapshot.child("Username").getValue();
                String firstName = (String) snapshot.child("FirstName").getValue();
                String lastName = (String) snapshot.child("LastName").getValue();
                String fullName = firstName + " " + lastName;
                String age = (String) snapshot.child("Age").getValue();
                String municipality = (String) snapshot.child("Municipality").getValue();
                String province = (String) snapshot.child("Province").getValue();
                String marketAddress = municipality + ", " + province;
                String mobile = (String) snapshot.child("MobileNumber").getValue();
                String email = (String) snapshot.child("EmailAddress").getValue();

                profileUser.setText(username);
                profileFullName.setText(fullName);
                profileAge.setText(age);
                profileAddress.setText(marketAddress);
                profileMobile.setText(mobile);
                profileEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}