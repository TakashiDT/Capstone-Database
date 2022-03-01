package com.example.capstone_project_redo.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.CategoryHouseholdNecessitiesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HouseholdNecessities extends DrawerBaseActivity {

    CategoryHouseholdNecessitiesBinding householdNecessitiesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        householdNecessitiesBinding = CategoryHouseholdNecessitiesBinding.inflate(getLayoutInflater());
        setContentView(householdNecessitiesBinding.getRoot());
        allocateActivityTitle("Household Necessities Section");

    }
}