package com.example.capstone_project_redo;

import android.content.Intent;
import android.os.Bundle;

import com.example.capstone_project_redo.databinding.InsideMeatBinding;
import com.example.capstone_project_redo.databinding.InsideVegetablesBinding;

public class InsideCategoryActivity extends DrawerBaseActivity {

    InsideVegetablesBinding insideVegetablesBinding;
    InsideMeatBinding insideMeatBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent rIntent = getIntent();
        if (rIntent != null)
        {
            String clicked = rIntent.getStringExtra("button");
            if (clicked.equals("vegetables")) {
                insideVegetablesBinding = InsideVegetablesBinding.inflate(getLayoutInflater());
                setContentView(insideVegetablesBinding.getRoot());
            }
            else if (clicked.equals("meat")) {
                insideMeatBinding = InsideMeatBinding.inflate(getLayoutInflater());
                setContentView(insideMeatBinding.getRoot());
            }
        }
    }
}