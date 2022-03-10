package com.example.capstone_project_redo.nav;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.capstone_project_redo.AddItemActivity;
import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.HomePage;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityMyProductsBinding;
import com.example.capstone_project_redo.adapter.MyListAdapter;
import com.example.capstone_project_redo.model.MyListModel;
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

    ProgressDialog loadingProgress;
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
        myListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myListAdapter.stopListening();
    }

    private void loadData() {

        myList = findViewById(R.id.lv_myProducts);
        myList.setHasFixedSize(true);
        myList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MyListModel> options =
                new FirebaseRecyclerOptions.Builder<MyListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").child(currentUser), MyListModel.class)
                        .build();

        myListAdapter = new MyListAdapter(options);
        myList.setAdapter(myListAdapter);

        if (myListAdapter.getItemCount() != 0) {
            if (loadingProgress.isShowing()) {
                loadingProgress.dismiss();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_products_filter, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void txtSearch(String str) {

        FirebaseRecyclerOptions<MyListModel> options =
                new FirebaseRecyclerOptions.Builder<MyListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").child(currentUser)
                                .orderByChild("name").startAt(str).endAt(str+"~"), MyListModel.class)
                        .build();
        myListAdapter = new MyListAdapter(options);
        myListAdapter.startListening();
        myList.setAdapter(myListAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filterShowAll:
                filterShowAll();
                return true;

            case R.id.filterFood:
                String food = "Food";
                filterCategory(food);
                return true;

            case R.id.filterCrafts:
                String crafts = "Crafted Goods";
                filterCategory(crafts);
                return true;

            case R.id.filterHousehold:
                String house = "Household Essentials";
                filterCategory(house);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void filterShowAll() {
        FirebaseRecyclerOptions<MyListModel> options =
                new FirebaseRecyclerOptions.Builder<MyListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").child(currentUser), MyListModel.class)
                        .build();
        myListAdapter = new MyListAdapter(options);
        myListAdapter.startListening();
        myList.setAdapter(myListAdapter);
    }

    private void filterCategory(String str) {
        FirebaseRecyclerOptions<MyListModel> options =
                new FirebaseRecyclerOptions.Builder<MyListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").child(currentUser)
                                .orderByChild("category").equalTo(str), MyListModel.class)
                        .build();
        myListAdapter = new MyListAdapter(options);
        myListAdapter.startListening();
        myList.setAdapter(myListAdapter);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyProductsActivity.this, HomePage.class));
        finish();
    }
}