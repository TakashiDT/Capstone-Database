package com.example.capstone_project_redo.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.capstone_project_redo.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class FilterProducts extends AppCompatActivity {

    private Chip meat, processed, seafood, fruit, vegetable;
    private Chip chicken, pork, beef, goat;
    private Chip frozen, canned;
    private Chip fish, shellfish;

    private Button filterData;
    private ArrayList<String> selectedChips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_products);

        setFilterData();

        filterData = findViewById(R.id.btn_filterData);
        filterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendFilter = new Intent();
                sendFilter.putExtra("filtered", selectedChips.toString());
                setResult(126, sendFilter);
                onBackPressed();
            }
        });
    }

    public void setFilterData() {
        meat = findViewById(R.id.chip_meat);
        processed = findViewById(R.id.chip_processed);
        seafood = findViewById(R.id.chip_seafood);
        fruit = findViewById(R.id.chip_fruit);
        vegetable = findViewById(R.id.chip_vegetable);

        chicken = findViewById(R.id.chip_chicken);
        pork = findViewById(R.id.chip_pork);
        beef = findViewById(R.id.chip_beef);
        goat = findViewById(R.id.chip_goat);
        //Hide subGroups
        chicken.setVisibility(View.GONE);
        pork.setVisibility(View.GONE);
        beef.setVisibility(View.GONE);
        goat.setVisibility(View.GONE);

        frozen = findViewById(R.id.chip_frozen);
        canned = findViewById(R.id.chip_canned);
        //Hide subGroups
        frozen.setVisibility(View.GONE);
        canned.setVisibility(View.GONE);

        fish = findViewById(R.id.chip_fish);
        shellfish = findViewById(R.id.chip_shellfish);
        //Hide subGroups
        fish.setVisibility(View.GONE);
        shellfish.setVisibility(View.GONE);

        //FilterProducts.super.onBackPressed();

        selectedChips = new ArrayList<>();
        CompoundButton.OnCheckedChangeListener checkedChangeType = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    selectedChips.add(compoundButton.getText().toString());

                    Toast.makeText(FilterProducts.this, selectedChips.toString(), Toast.LENGTH_SHORT).show();
                    //loop through the currently selected main items
                    String s = compoundButton.getText().toString();
                    if ("Meat".equals(s)) {//show chips
                        chicken.setVisibility(View.VISIBLE);
                        pork.setVisibility(View.VISIBLE);
                        beef.setVisibility(View.VISIBLE);
                        goat.setVisibility(View.VISIBLE);

                    }
                    else if ("Processed Food".equals(s)) {//show chips
                        frozen.setVisibility(View.VISIBLE);
                        canned.setVisibility(View.VISIBLE);

                    }
                    else if ("Seafood".equals(s)) {//show chips
                        fish.setVisibility(View.VISIBLE);
                        shellfish.setVisibility(View.VISIBLE);

                    }
                }
                else {
                    selectedChips.remove(compoundButton.getText().toString());
                    String s = compoundButton.getText().toString();

                    if ("Meat".equals(s)) {
                        chicken.setVisibility(View.GONE);
                        pork.setVisibility(View.GONE);
                        beef.setVisibility(View.GONE);
                        goat.setVisibility(View.GONE);
                    }
                    else if ("Processed Food".equals(s)) {
                        frozen.setVisibility(View.GONE);
                        canned.setVisibility(View.GONE);
                    }
                    else if ("Seafood".equals(s)) {
                        fish.setVisibility(View.GONE);
                        shellfish.setVisibility(View.GONE);
                    }
                }
            }
        };
        meat.setOnCheckedChangeListener(checkedChangeType);
        processed.setOnCheckedChangeListener(checkedChangeType);
        seafood.setOnCheckedChangeListener(checkedChangeType);
        fruit.setOnCheckedChangeListener(checkedChangeType);
        vegetable.setOnCheckedChangeListener(checkedChangeType);

        chicken.setOnCheckedChangeListener(checkedChangeType);
        pork.setOnCheckedChangeListener(checkedChangeType);
        beef.setOnCheckedChangeListener(checkedChangeType);
        goat.setOnCheckedChangeListener(checkedChangeType);

        frozen.setOnCheckedChangeListener(checkedChangeType);
        canned.setOnCheckedChangeListener(checkedChangeType);

        fish.setOnCheckedChangeListener(checkedChangeType);
        shellfish.setOnCheckedChangeListener(checkedChangeType);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(FilterProducts.this,Food.class));
        finish();
    }
}