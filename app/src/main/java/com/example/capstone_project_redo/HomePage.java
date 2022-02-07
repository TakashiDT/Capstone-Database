package com.example.capstone_project_redo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.capstone_project_redo.databinding.ActivityHomePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends DrawerBaseActivity {

    ActivityHomePageBinding activityHomePageBinding;

    TextView currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(activityHomePageBinding.getRoot());
        allocateActivityTitle("Home");

        currentUser = (TextView)findViewById(R.id.fb_currentUser);

        FirebaseUser uAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (uAuth != null) {
            currentUser.setText(uAuth.getEmail());
        }
    }
}