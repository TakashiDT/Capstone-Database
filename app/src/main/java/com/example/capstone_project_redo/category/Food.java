package com.example.capstone_project_redo.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
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
import com.example.capstone_project_redo.adapter.MyListAdapter;
import com.example.capstone_project_redo.databinding.CategoryFoodBinding;
import com.example.capstone_project_redo.model.FoodModel;
import com.example.capstone_project_redo.model.MyListModel;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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
    NavigationView navigationView;

    CategoryFoodBinding foodBinding;

    private ArrayList<FoodModel> mCraft = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodBinding = CategoryFoodBinding.inflate(getLayoutInflater());
        setContentView(foodBinding.getRoot());
        allocateActivityTitle("Food Section");

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

    }

    MenuItem filterMeat;
    MenuItem filterProcessed;
    MenuItem filterSeafood;
    MenuItem filterFruits;
    MenuItem filterVegetables;

    MenuItem itemChicken;
    MenuItem itemPork;
    MenuItem itemBeef;
    MenuItem itemFrozen;
    MenuItem itemCanned;
    MenuItem itemFish;
    MenuItem itemShellfish;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.filter_food, menu);

        filterMeat = menu.findItem(R.id.filterMeat).setVisible(true);
        filterProcessed = menu.findItem(R.id.filterProcessed).setVisible(true);
        filterSeafood = menu.findItem(R.id.filterSeafood).setVisible(true);
        filterFruits = menu.findItem(R.id.filterFruits).setVisible(true);
        filterVegetables = menu.findItem(R.id.filterVegetables).setVisible(true);

        itemChicken = menu.findItem(R.id.itemChicken).setVisible(false);
        itemPork = menu.findItem(R.id.itemPork).setVisible(false);
        itemBeef = menu.findItem(R.id.itemBeef).setVisible(false);

        itemFrozen = menu.findItem(R.id.itemFrozen).setVisible(false);
        itemCanned = menu.findItem(R.id.itemCanned).setVisible(false);

        itemFish = menu.findItem(R.id.itemFish).setVisible(false);
        itemShellfish = menu.findItem(R.id.itemShellfish).setVisible(false);

        MenuItem item = menu.findItem(R.id.searchFood);
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

        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("mixed")
                        .orderByChild("name").startAt(str).endAt(str+"~"), FoodModel.class)
                .build();
        foodAdapter = new FoodAdapter(this, options);
        foodAdapter.startListening();
        foodList.setAdapter(foodAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filterShowAllFood:
                filterMeat.setVisible(true);
                filterProcessed.setVisible(true);
                filterSeafood.setVisible(true);
                filterFruits.setVisible(true);
                filterVegetables.setVisible(true);


                itemChicken.setVisible(false);
                itemPork.setVisible(false);
                itemBeef.setVisible(false);
                itemFrozen.setVisible(false);
                itemCanned.setVisible(false);
                itemFish.setVisible(false);
                itemShellfish.setVisible(false);
                filterShowAll();
                return true;
            case R.id.filterMeat:
                String meat = "Meat";
                filterMeat(meat);
                return true;
                case R.id.itemChicken:
                    String chicken = "Chicken";
                    filterChicken(chicken);
                    return true;
                case R.id.itemPork:
                    String pork = "Pork";
                    filterPork(pork);
                    return true;
                case R.id.itemBeef:
                    String beef = "Beef";
                    filterBeef(beef);
                    return true;
            case R.id.filterProcessed:
                String processed = "Processed Food";
                filterProcessed(processed);
                return true;
                case R.id.itemFrozen:
                    String frozen = "Frozen";
                    filterFrozen(frozen);
                    return true;
                case R.id.itemCanned:
                    String canned = "Canned";
                    filterCanned(canned);
                    return true;
            case R.id.filterSeafood:
                String seafood = "Seafood";
                filterSeafood(seafood);
                return true;
                case R.id.itemFish:
                    String fish = "Fish";
                    filterFish(fish);
                    return true;
                case R.id.itemShellfish:
                    String shellfish = "Shellfish";
                    filterShellfish(shellfish);
                    return true;
            case R.id.filterFruits:
                String fruit = "Fruits";
                filterFruit(fruit);
                return true;
            case R.id.filterVegetables:
                String vegetables = "Vegetables";
                filterVegetables(vegetables);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void filterShowAll() {
        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("mixed"), FoodModel.class)
                .build();

        foodAdapter = new FoodAdapter(this, options);
        foodAdapter.startListening();
        foodList.setAdapter(foodAdapter);

    }

    private void filterMeat(String str) {
        filterMeat.setVisible(false);
        filterProcessed.setVisible(false);
        filterSeafood.setVisible(false);
        filterFruits.setVisible(false);
        filterVegetables.setVisible(false);

        itemChicken.setVisible(true);
        itemPork.setVisible(true);
        itemBeef.setVisible(true);

        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Meat")
                        .orderByChild("categorySub").equalTo(str), FoodModel.class)
                .build();

        foodAdapter = new FoodAdapter(this, options);
        foodAdapter.startListening();
        foodList.setAdapter(foodAdapter);

    }

        private void filterChicken(String str) {
            FirebaseRecyclerOptions<FoodModel> options;
            options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Meat")
                            .orderByChild("categorySub2").equalTo(str), FoodModel.class)
                    .build();

            foodAdapter = new FoodAdapter(this, options);
            foodAdapter.startListening();
            foodList.setAdapter(foodAdapter);

        }

        private void filterPork(String str) {
            FirebaseRecyclerOptions<FoodModel> options;
            options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Meat")
                            .orderByChild("categorySub2").equalTo(str), FoodModel.class)
                    .build();

            foodAdapter = new FoodAdapter(this, options);
            foodAdapter.startListening();
            foodList.setAdapter(foodAdapter);

        }

        private void filterBeef(String str) {
            FirebaseRecyclerOptions<FoodModel> options;
            options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Meat")
                            .orderByChild("categorySub2").equalTo(str), FoodModel.class)
                    .build();

            foodAdapter = new FoodAdapter(this, options);
            foodAdapter.startListening();
            foodList.setAdapter(foodAdapter);

        }

    private void filterProcessed(String str) {
        filterMeat.setVisible(false);
        filterProcessed.setVisible(false);
        filterSeafood.setVisible(false);
        filterFruits.setVisible(false);
        filterVegetables.setVisible(false);

        itemFrozen.setVisible(true);
        itemCanned.setVisible(true);

        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Processed Food")
                        .orderByChild("categorySub").equalTo(str), FoodModel.class)
                .build();

        foodAdapter = new FoodAdapter(this, options);
        foodAdapter.startListening();
        foodList.setAdapter(foodAdapter);

    }

        private void filterFrozen(String str) {
            FirebaseRecyclerOptions<FoodModel> options;
            options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Processed Food")
                            .orderByChild("categorySub2").equalTo(str), FoodModel.class)
                    .build();

            foodAdapter = new FoodAdapter(this, options);
            foodAdapter.startListening();
            foodList.setAdapter(foodAdapter);

        }

        private void filterCanned(String str) {
            FirebaseRecyclerOptions<FoodModel> options;
            options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Processed Food")
                            .orderByChild("categorySub2").equalTo(str), FoodModel.class)
                    .build();

            foodAdapter = new FoodAdapter(this, options);
            foodAdapter.startListening();
            foodList.setAdapter(foodAdapter);

        }

    private void filterSeafood(String str) {
        filterMeat.setVisible(false);
        filterProcessed.setVisible(false);
        filterSeafood.setVisible(false);
        filterFruits.setVisible(false);
        filterVegetables.setVisible(false);

        itemFish.setVisible(true);
        itemShellfish.setVisible(true);

        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Seafood")
                        .orderByChild("categorySub").equalTo(str), FoodModel.class)
                .build();

        foodAdapter = new FoodAdapter(this, options);
        foodAdapter.startListening();
        foodList.setAdapter(foodAdapter);

    }

        private void filterFish(String str) {
            FirebaseRecyclerOptions<FoodModel> options;
            options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Seafood")
                            .orderByChild("categorySub2").equalTo(str), FoodModel.class)
                    .build();

            foodAdapter = new FoodAdapter(this, options);
            foodAdapter.startListening();
            foodList.setAdapter(foodAdapter);

        }

        private void filterShellfish(String str) {
            FirebaseRecyclerOptions<FoodModel> options;
            options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Seafood")
                            .orderByChild("categorySub2").equalTo(str), FoodModel.class)
                    .build();

            foodAdapter = new FoodAdapter(this, options);
            foodAdapter.startListening();
            foodList.setAdapter(foodAdapter);

        }

    private void filterFruit(String str) {
        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Fruits")
                        .orderByChild("categorySub").equalTo(str), FoodModel.class)
                .build();

        foodAdapter = new FoodAdapter(this, options);
        foodAdapter.startListening();
        foodList.setAdapter(foodAdapter);

    }

    private void filterVegetables(String str) {
        FirebaseRecyclerOptions<FoodModel> options;
        options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food").child("Vegetables")
                        .orderByChild("categorySub").equalTo(str), FoodModel.class)
                .build();

        foodAdapter = new FoodAdapter(this, options);
        foodAdapter.startListening();
        foodList.setAdapter(foodAdapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Food.this, CategoryActivity.class));
        finish();
    }
}