package com.example.capstone_project_redo.nav;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.category.Misc;
import com.example.capstone_project_redo.category.CraftedGoods;
import com.example.capstone_project_redo.category.Food;
import com.example.capstone_project_redo.category.Basic;
import com.example.capstone_project_redo.databinding.ActivityCategoryBinding;
import com.example.capstone_project_redo.adapter.CategoryAdapter;
import com.example.capstone_project_redo.model.CategoryModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CategoryActivity extends DrawerBaseActivity implements CategoryAdapter.OnCategoryListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");
    ProgressDialog loadingProgress;
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

        loadingProgress = new ProgressDialog(this);
        loadingProgress.setMessage("Loading, Please Wait...");
        loadingProgress.setCancelable(false);
        loadingProgress.show();

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

        databaseReference = database.getReference("products");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (loadingProgress.isShowing()){
                    loadingProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (loadingProgress.isShowing()){
                    loadingProgress.dismiss();
                }
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        Log.d(TAG, "onCategoryClick: clicked");
        switch (position) {
            case 0:
                Intent basicIntent = new Intent(this, Basic.class);
                startActivity(basicIntent);
                finish();
                break;
            case 1:
                Intent craftIntent = new Intent(this, CraftedGoods.class);
                startActivity(craftIntent);
                finish();
                break;
            case 2:
                Intent foodIntent = new Intent(this, Food.class);
                startActivity(foodIntent);
                finish();
                break;
            case 3:
                Intent miscIntent = new Intent(this, Misc.class);
                startActivity(miscIntent);
                finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CategoryActivity.this, HomePage.class));
        finish();
    }
}