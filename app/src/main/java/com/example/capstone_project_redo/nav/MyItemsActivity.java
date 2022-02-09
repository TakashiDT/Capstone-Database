package com.example.capstone_project_redo.nav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.capstone_project_redo.DrawerBaseActivity;
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

        uItemStorageRef = FirebaseStorage.getInstance().getReference();

        FloatingActionButton fab_addItem = findViewById(R.id.fab_addItem);
        fab_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.add_item);
            }
        });
    }
}