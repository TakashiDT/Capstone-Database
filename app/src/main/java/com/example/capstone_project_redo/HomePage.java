package com.example.capstone_project_redo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone_project_redo.databinding.ActivityHomePageBinding;

public class HomePage extends DrawerBaseActivity {

    ActivityHomePageBinding activityHomePageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(activityHomePageBinding.getRoot());
        allocateActivityTitle("Home");
    }
}