package com.example.capstone_project_redo.nav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.InsideCategoryActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityCategoryBinding;
import com.example.capstone_project_redo.forRecyclerViews.CategoryAdapter;
import com.example.capstone_project_redo.forRecyclerViews.CategoryModel;
import com.example.capstone_project_redo.forRecyclerViews.MyListAdapter;
import com.example.capstone_project_redo.forRecyclerViews.MyListModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryActivity extends DrawerBaseActivity {

    RecyclerView categoryList;
    CategoryAdapter categoryAdapter;

    ActivityCategoryBinding activityCategoryBinding;

    //Button toVegetables, toMeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryBinding.getRoot());
        allocateActivityTitle("Category");



        /*
        toVegetables = (Button)findViewById(R.id.btn_category);
        toMeat = (Button)findViewById(R.id.btn_category1);

        toVegetables.setOnClickListener(this);
        toMeat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_category:
                Intent intent = new Intent(this, InsideCategoryActivity.class);
                intent.putExtra("button", "vegetables");
                startActivity(intent);
                break;
            case R.id.btn_category1:
                Intent intent1 = new Intent(this, InsideCategoryActivity.class);
                intent1.putExtra("button", "meat");
                startActivity(intent1);
                break;
        }
         */


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
        categoryList.setLayoutManager(new GridLayoutManager(this,2));

        FirebaseRecyclerOptions<CategoryModel> options =
                new FirebaseRecyclerOptions.Builder<CategoryModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("categoryImages"), CategoryModel.class)
                        .build();


        categoryAdapter = new CategoryAdapter(options);
        categoryList.setAdapter(categoryAdapter);
    }

}