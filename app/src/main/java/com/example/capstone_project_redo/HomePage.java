package com.example.capstone_project_redo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_project_redo.databinding.ActivityHomePageBinding;
import com.example.capstone_project_redo.nav.CategoryActivity;
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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            currentUser.setText(user.getEmail());
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(HomePage.this, LoginActivity.class));
        finish();
    }
}