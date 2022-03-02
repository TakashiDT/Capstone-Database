package com.example.capstone_project_redo.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.adapter.HouseholdEssentialsAdapter;
import com.example.capstone_project_redo.databinding.CategoryHouseholdEssentialsBinding;
import com.example.capstone_project_redo.model.HouseholdEssentialsModel;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HouseholdEssentials extends DrawerBaseActivity implements HouseholdEssentialsAdapter.OnHouseholdEssentialsListener {

    RecyclerView householdEssentials;
    HouseholdEssentialsAdapter householdEssentialsAdapter;

    CategoryHouseholdEssentialsBinding householdEssentialsBinding;

    private ArrayList<HouseholdEssentialsModel> mHousehold = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        householdEssentialsBinding = CategoryHouseholdEssentialsBinding.inflate(getLayoutInflater());
        setContentView(householdEssentialsBinding.getRoot());
        allocateActivityTitle("Household Essentials Section");


        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        householdEssentialsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        householdEssentialsAdapter.stopListening();
    }

    private void loadData() {
        householdEssentials = findViewById(R.id.lv_householdEssentials);
        householdEssentials.setHasFixedSize(true);
        householdEssentials.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<HouseholdEssentialsModel> options =
                new FirebaseRecyclerOptions.Builder<HouseholdEssentialsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Household Essentials").child("mixed"), HouseholdEssentialsModel.class)
                        .build();


        householdEssentialsAdapter = new HouseholdEssentialsAdapter(this, options);
        householdEssentials.setAdapter(householdEssentialsAdapter);
    }
    @Override
    public void onCategoryClick(int position) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HouseholdEssentials.this, CategoryActivity.class));
        finish();
    }

}