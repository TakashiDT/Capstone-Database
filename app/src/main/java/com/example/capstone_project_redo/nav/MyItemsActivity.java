package com.example.capstone_project_redo.nav;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstone_project_redo.AddItemActivity;
import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.HomePage;
import com.example.capstone_project_redo.LoginActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityMyItemsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyItemsActivity extends DrawerBaseActivity {

    private StorageReference uItemStorageRef;

    ActivityMyItemsBinding activityMyItemsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyItemsBinding = ActivityMyItemsBinding.inflate(getLayoutInflater());
        setContentView(activityMyItemsBinding.getRoot());
        allocateActivityTitle("My Items");


        FloatingActionButton fab_addItem = findViewById(R.id.fab_addItem);
        fab_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyItemsActivity.this, AddItemActivity.class));
            }
        });

    }
}