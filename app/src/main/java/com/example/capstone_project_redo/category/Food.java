package com.example.capstone_project_redo.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.adapter.CategoryInsideAdapter;
import com.example.capstone_project_redo.databinding.CategoryInsideBinding;
import com.example.capstone_project_redo.model.CategoryInsideModel;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Food extends DrawerBaseActivity implements CategoryInsideAdapter.OnProductListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");
    ProgressDialog loadingProgress;

    RecyclerView insideList;
    CategoryInsideAdapter categoryInsideAdapter;
    CategoryInsideBinding insideBinding;

    private ArrayList<CategoryInsideModel> mCraft = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        insideBinding = CategoryInsideBinding.inflate(getLayoutInflater());
        setContentView(insideBinding.getRoot());
        allocateActivityTitle("Food Section");

        loadingProgress = new ProgressDialog(this);
        loadingProgress.setMessage("Loading, Please Wait...");
        loadingProgress.setCancelable(false);
        loadingProgress.show();

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
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food"), CategoryInsideModel.class)
                .build();

        categoryInsideAdapter = new CategoryInsideAdapter(this, options);
        insideList.setAdapter(categoryInsideAdapter);

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

        FirebaseRecyclerOptions<CategoryInsideModel> options;
        options = new FirebaseRecyclerOptions.Builder<CategoryInsideModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food")
                        .orderByChild("name").startAt(str).endAt(str+"~"), CategoryInsideModel.class)
                .build();
        categoryInsideAdapter = new CategoryInsideAdapter(this, options);
        categoryInsideAdapter.startListening();
        insideList.setAdapter(categoryInsideAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String meat = "Meat";
        String processed = "Processed Food";
        String seafood = "Seafood";
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
                itemChicken.setVisible(true);
                itemPork.setVisible(true);
                itemBeef.setVisible(true);

                filterFoodCat(meat);
                return true;
                case R.id.itemChicken:
                    String chicken = "Chicken";
                    filterSubCat(chicken);
                    return true;
                case R.id.itemPork:
                    String pork = "Pork";
                    filterSubCat(pork);
                    return true;
                case R.id.itemBeef:
                    String beef = "Beef";
                    filterSubCat(beef);
                    return true;

            case R.id.filterProcessed:
                itemFrozen.setVisible(true);
                itemCanned.setVisible(true);

                filterFoodCat(processed);
                return true;
                case R.id.itemFrozen:
                    String frozen = "Frozen";
                    filterSubCat(frozen);
                    return true;
                case R.id.itemCanned:
                    String canned = "Canned";
                    filterSubCat(canned);
                    return true;

            case R.id.filterSeafood:
                itemFish.setVisible(true);
                itemShellfish.setVisible(true);

                filterFoodCat(seafood);
                return true;
                case R.id.itemFish:
                    String fish = "Fish";
                    filterSubCat(fish);
                    return true;
                case R.id.itemShellfish:
                    String shellfish = "Shellfish";
                    filterSubCat(shellfish);
                    return true;
            case R.id.filterFruits:
                String fruit = "Fruits";
                filterHealth(fruit);
                return true;
            case R.id.filterVegetables:
                String vegetables = "Vegetables";
                filterHealth(vegetables);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void filterShowAll() {
        FirebaseRecyclerOptions<CategoryInsideModel> options;
        options = new FirebaseRecyclerOptions.Builder<CategoryInsideModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food"), CategoryInsideModel.class)
                .build();

        categoryInsideAdapter = new CategoryInsideAdapter(this, options);
        categoryInsideAdapter.startListening();
        insideList.setAdapter(categoryInsideAdapter);

    }

    private void filterFoodCat(String str) {
        filterMeat.setVisible(false);
        filterProcessed.setVisible(false);
        filterSeafood.setVisible(false);
        filterFruits.setVisible(false);
        filterVegetables.setVisible(false);

        FirebaseRecyclerOptions<CategoryInsideModel> options;
        options = new FirebaseRecyclerOptions.Builder<CategoryInsideModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food")
                        .orderByChild("categorySub").equalTo(str), CategoryInsideModel.class)
                .build();

        categoryInsideAdapter = new CategoryInsideAdapter(this, options);
        categoryInsideAdapter.startListening();
        insideList.setAdapter(categoryInsideAdapter);

    }

    private void filterHealth(String str) {
        FirebaseRecyclerOptions<CategoryInsideModel> options;
        options = new FirebaseRecyclerOptions.Builder<CategoryInsideModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food")
                        .orderByChild("categorySub").equalTo(str), CategoryInsideModel.class)
                .build();

        categoryInsideAdapter = new CategoryInsideAdapter(this, options);
        categoryInsideAdapter.startListening();
        insideList.setAdapter(categoryInsideAdapter);

    }

    private void filterSubCat(String str) {
        FirebaseRecyclerOptions<CategoryInsideModel> options;
        options = new FirebaseRecyclerOptions.Builder<CategoryInsideModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("categories").child("Food")
                        .orderByChild("categorySub2").equalTo(str), CategoryInsideModel.class)
                .build();

        categoryInsideAdapter = new CategoryInsideAdapter(this, options);
        categoryInsideAdapter.startListening();
        insideList.setAdapter(categoryInsideAdapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Food.this, CategoryActivity.class));
        finish();
    }
}