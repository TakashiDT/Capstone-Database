package com.example.capstone_project_redo.nav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.databinding.ActivityAboutBinding;
import com.example.capstone_project_redo.databinding.ActivityMyProfileBinding;

public class AboutActivity extends DrawerBaseActivity {

    ActivityAboutBinding activityAboutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(activityAboutBinding.getRoot());
        allocateActivityTitle("About the App & FAQ");
    }
}