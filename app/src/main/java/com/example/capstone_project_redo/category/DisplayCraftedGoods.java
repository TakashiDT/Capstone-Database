package com.example.capstone_project_redo.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.capstone_project_redo.R;

public class DisplayCraftedGoods extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_crafted_goods);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productId = extras.getString("productId");
            Toast.makeText(this, productId, Toast.LENGTH_SHORT).show();
        }
    }
}