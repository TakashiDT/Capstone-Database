package com.example.capstone_project_redo.nav;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.capstone_project_redo.AddItemActivity;
import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityMyProductsBinding;
import com.example.capstone_project_redo.forRecyclerViews.MyListAdapter;
import com.example.capstone_project_redo.forRecyclerViews.MyListProducts;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MyProductsActivity extends DrawerBaseActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser = user.getUid();

    RecyclerView myList;
    MyListAdapter myListAdapter;
    ActivityMyProductsBinding activityMyProductsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProductsBinding = ActivityMyProductsBinding.inflate(getLayoutInflater());
        setContentView(activityMyProductsBinding.getRoot());
        allocateActivityTitle("My Products");


        FloatingActionButton fab_addItem = findViewById(R.id.fab_addItem);
        fab_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProductsActivity.this, AddItemActivity.class));
            }
        });

        loadData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        myListAdapter.startListening();
    }


    private void loadData() {
        myList = findViewById(R.id.lv_myProducts);
        myList.setHasFixedSize(true);
        myList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MyListProducts> options =
                new FirebaseRecyclerOptions.Builder<MyListProducts>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").child(currentUser), MyListProducts.class)
                        .build();


        myListAdapter = new MyListAdapter(options);
        myList.setAdapter(myListAdapter);
    }
}