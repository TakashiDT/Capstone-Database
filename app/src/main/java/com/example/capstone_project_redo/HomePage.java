package com.example.capstone_project_redo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_project_redo.databinding.ActivityHomePageBinding;
import com.example.capstone_project_redo.nav.AboutActivity;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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