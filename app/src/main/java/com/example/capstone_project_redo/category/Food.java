package com.example.capstone_project_redo.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.adapter.FoodAdapter;
import com.example.capstone_project_redo.databinding.CategoryFoodBinding;
import com.example.capstone_project_redo.model.FoodModel;
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

public class Food extends DrawerBaseActivity implements FoodAdapter.OnFoodListener{

    DatabaseReference productIdRef;
    RecyclerView foodList;
    FoodAdapter foodAdapter;
    String productId;

    CategoryFoodBinding foodBinding;

    private ArrayList<FoodModel> mCraft = new ArrayList<>();

    ActivityResultLauncher<Intent> filterLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == 126) {
                        Intent intent = result.getData();

                        if (intent != null) {

                            String filtered = intent.getStringExtra("filtered");
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodBinding = CategoryFoodBinding.inflate(getLayoutInflater());
        setContentView(foodBinding.getRoot());
        allocateActivityTitle("Food Section");

        FloatingActionButton fab_addItem = findViewById(R.id.fab_addFilterF);
        fab_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterIntent = new Intent(Food.this, FilterProducts.class);
                filterLauncher.launch(filterIntent);
            }
        });

        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        foodAdapter.startListening();
        foodAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        foodAdapter.stopListening();
    }

    private void loadData() {
        foodList = findViewById(R.id.lv_food);
        foodList.setHasFixedSize(true);
        foodList.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("mixed"), FoodModel.class)
                .build();

        foodAdapter = new FoodAdapter(this, options);
        foodList.setAdapter(foodAdapter);
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
        startActivity(new Intent(Food.this, CategoryActivity.class));
        finish();
    }
}