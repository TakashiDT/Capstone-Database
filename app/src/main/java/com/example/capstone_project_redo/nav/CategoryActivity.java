package com.example.capstone_project_redo.nav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.InsideCategoryActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ActivityCategoryBinding;

public class CategoryActivity extends DrawerBaseActivity implements View.OnClickListener {

    ActivityCategoryBinding activityCategoryBinding;

    Button toVegetables, toMeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryBinding.getRoot());
        allocateActivityTitle("Category");

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
    }
}