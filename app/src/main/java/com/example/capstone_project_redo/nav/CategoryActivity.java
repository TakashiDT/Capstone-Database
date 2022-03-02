package com.example.capstone_project_redo.nav;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.category.CraftedGoods;
import com.example.capstone_project_redo.category.Food;
import com.example.capstone_project_redo.category.HouseholdEssentials;
import com.example.capstone_project_redo.databinding.ActivityCategoryBinding;
import com.example.capstone_project_redo.adapter.CategoryAdapter;
import com.example.capstone_project_redo.model.CategoryModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CategoryActivity extends DrawerBaseActivity implements CategoryAdapter.OnCategoryListener {

    RecyclerView categoryList;
    CategoryAdapter categoryAdapter;

    ActivityCategoryBinding activityCategoryBinding;

    private ArrayList<CategoryModel> mCategory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryBinding.getRoot());
        allocateActivityTitle("Categories");

        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
    }

    private void loadData() {
        categoryList = findViewById(R.id.lv_category);
        categoryList.setHasFixedSize(true);
        categoryList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CategoryModel> options =
                new FirebaseRecyclerOptions.Builder<CategoryModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("categoryImages").child("mainCategory"), CategoryModel.class)
                        .build();


        categoryAdapter = new CategoryAdapter(this, options);
        categoryList.setAdapter(categoryAdapter);
    }

    @Override
    public void onCategoryClick(int position) {
        Log.d(TAG, "onCategoryClick: clicked");
        switch (position) {
            case 0:
                Intent craftedGoodsIntent = new Intent(this, CraftedGoods.class);
                startActivity(craftedGoodsIntent);
                finish();
                break;
            case 1:
                Intent foodIntent = new Intent(this, Food.class);
                startActivity(foodIntent);
                finish();
                break;
            case 2:
                Intent householdNecessitiesIntent = new Intent(this, HouseholdEssentials.class);
                startActivity(householdNecessitiesIntent);
                finish();
                break;
        }
    }
}