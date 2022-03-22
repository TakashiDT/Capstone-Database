package com.example.capstone_project_redo.nav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.LoginActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityHomePageBinding;

public class HomePage extends DrawerBaseActivity {

    Button toCategory, toAbout;

    ActivityHomePageBinding activityHomePageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(activityHomePageBinding.getRoot());
        allocateActivityTitle("Home");

        toCategory = findViewById(R.id.btn_hCategory);
        toAbout = findViewById(R.id.btn_hAbout);

        toCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, CategoryActivity.class));
            }
        });
        toAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, AboutActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(HomePage.this, LoginActivity.class));
        finish();
    }
}