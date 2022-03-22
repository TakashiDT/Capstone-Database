package com.example.capstone_project_redo.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.databinding.ViewholderProductDataBinding;

public class ProductData extends DrawerBaseActivity {

    ViewholderProductDataBinding productDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDataBinding = ViewholderProductDataBinding.inflate(getLayoutInflater());
        setContentView(productDataBinding.getRoot());
        allocateActivityTitle("Product Data");
    }
}