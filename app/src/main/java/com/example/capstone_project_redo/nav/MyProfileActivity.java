package com.example.capstone_project_redo.nav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityMyProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfileActivity extends DrawerBaseActivity {

    ActivityMyProfileBinding activityMyProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProfileBinding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(activityMyProfileBinding.getRoot());
        allocateActivityTitle("User Profile");

        final TextView fullName = findViewById(R.id.tv_profileName);

    }


}