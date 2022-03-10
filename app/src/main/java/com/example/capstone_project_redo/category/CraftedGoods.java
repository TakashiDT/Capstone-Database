package com.example.capstone_project_redo.category;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.adapter.CategoryInsideAdapter;
import com.example.capstone_project_redo.model.CategoryInsideModel;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CraftedGoods extends DrawerBaseActivity implements CategoryInsideAdapter.OnProductListener{

    RecyclerView insideList;
    CategoryInsideAdapter categoryInsideAdapter;
    com.example.capstone_project_redo.databinding.CategoryInsideBinding insideBinding;

    private ArrayList<CategoryInsideModel> mCraft = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        insideBinding = com.example.capstone_project_redo.databinding.CategoryInsideBinding.inflate(getLayoutInflater());
        setContentView(insideBinding.getRoot());
        allocateActivityTitle("Crafted Goods Section");

        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryInsideAdapter.startListening();
        categoryInsideAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoryInsideAdapter.stopListening();
    }

    private void loadData() {
        insideList = findViewById(R.id.lv_insideCategory);
        insideList.setHasFixedSize(true);
        insideList.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<CategoryInsideModel> options;
        options = new FirebaseRecyclerOptions.Builder<CategoryInsideModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").orderByChild("category").equalTo("Crafted Goods"), CategoryInsideModel.class)
                .build();

        categoryInsideAdapter = new CategoryInsideAdapter(this, options);
        insideList.setAdapter(categoryInsideAdapter);
    }

    @Override
    public void onCategoryClick(int position) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CraftedGoods.this, CategoryActivity.class));
        finish();
    }

}