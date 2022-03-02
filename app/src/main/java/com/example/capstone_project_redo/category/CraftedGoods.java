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
import com.example.capstone_project_redo.adapter.CraftedGoodsAdapter;
import com.example.capstone_project_redo.databinding.CategoryCraftedGoodsBinding;
import com.example.capstone_project_redo.model.CraftedGoodsModel;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CraftedGoods extends DrawerBaseActivity implements CraftedGoodsAdapter.OnCraftedGoodsListener{

    DatabaseReference productIdRef;
    RecyclerView craftedGoodsList;
    CraftedGoodsAdapter craftedGoodsAdapter;
    String productId;

    CategoryCraftedGoodsBinding craftedGoodsBinding;

    private ArrayList<CraftedGoodsModel> mCraft = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        craftedGoodsBinding = CategoryCraftedGoodsBinding.inflate(getLayoutInflater());
        setContentView(craftedGoodsBinding.getRoot());
        allocateActivityTitle("Crafted Goods Section");


        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        craftedGoodsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        craftedGoodsAdapter.stopListening();
    }

    private void loadData() {
        craftedGoodsList = findViewById(R.id.lv_craftedGoodsList);
        craftedGoodsList.setHasFixedSize(true);
        craftedGoodsList.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<CraftedGoodsModel> options =
                new FirebaseRecyclerOptions.Builder<CraftedGoodsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Crafted Goods").child("mixed"), CraftedGoodsModel.class)
                        .build();


        craftedGoodsAdapter = new CraftedGoodsAdapter(this, options);
        craftedGoodsList.setAdapter(craftedGoodsAdapter);
    }

    @Override
    public void onCategoryClick(int position) {
        Log.d(TAG, "onCraftedGoodsClick: clicked");

        productIdRef = FirebaseDatabase.getInstance().getReference("categories").child("Crafted Goods").child(String.valueOf(position));
        productIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productId = (String) snapshot.child("productId").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent craftedGoodsIntent = new Intent(this, DisplayCraftedGoods.class);
        craftedGoodsIntent.putExtra("productId", productId);
        startActivity(craftedGoodsIntent);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CraftedGoods.this, CategoryActivity.class));
        finish();
    }

}