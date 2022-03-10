package com.example.capstone_project_redo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.capstone_project_redo.databinding.ActivityAdminBinding;
import com.example.capstone_project_redo.databinding.ActivityHomePageBinding;

public class AdminActivity extends DrawerBaseActivity {

    ActivityAdminBinding adminBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        adminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(adminBinding.getRoot());
        allocateActivityTitle("Admin");
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminActivity.this, LoginActivity.class));
        finish();
    }
}