package com.example.capstone_project_redo.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.ViewholderProductDataBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductData extends DrawerBaseActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ProgressDialog loadingProgress;

    ImageView imageUrl;
    TextView name, seller, category, desc, mobile, price;

    ViewholderProductDataBinding productDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDataBinding = ViewholderProductDataBinding.inflate(getLayoutInflater());
        setContentView(productDataBinding.getRoot());
        allocateActivityTitle("Product Data");

        loadingProgress = new ProgressDialog(this);
        loadingProgress.setMessage("Loading, Please Wait...");
        loadingProgress.setCancelable(false);
        loadingProgress.show();

        // INTENT FROM ADAPTER
        Intent extra = getIntent();
        String productId = extra.getStringExtra("id");;
        String productCategory = extra.getStringExtra("category");

        // TEXTVIEW FROM LAYOUT
        name = findViewById(R.id.tv_productName);
        seller = findViewById(R.id.tv_productSeller);
        category = findViewById(R.id.tv_productCategory);
        desc = findViewById(R.id.tv_productDesc);
        mobile = findViewById(R.id.tv_productMobile);
        price = findViewById(R.id.tv_productPrice);
        imageUrl = findViewById(R.id.iv_productImage);

        databaseReference = database.getReference().child("categories").child(productCategory).child(productId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nameTxt = (String) snapshot.child("name").getValue();
                String sellerTxt = (String) snapshot.child("seller").getValue();
                String categoryTxt = (String) snapshot.child("category").getValue();
                String categorySubTxt = (String) snapshot.child("categorySub").getValue();
                String categorySub2Txt = (String) snapshot.child("categorySub2").getValue();
                String descTxt = (String) snapshot.child("description").getValue();
                String mobileTxt = (String) snapshot.child("mobile").getValue();
                String priceTxt = (String) snapshot.child("price").getValue();
                String priceExTxt = (String) snapshot.child("priceExtension").getValue();
                String imageUrlTxt = (String) snapshot.child("imageUrl").getValue();

                // SET TEXT TO TEXTVIEW
                name.setText(nameTxt);
                seller.setText(sellerTxt);
                if (categoryTxt != null && categorySubTxt == null && categorySub2Txt == null) {
                    category.setText(categoryTxt);
                }
                else if (categoryTxt != null && categorySubTxt != null && categorySub2Txt == null) {
                    category.setText(categoryTxt + " > " + categorySubTxt);
                }
                else {
                    category.setText(categoryTxt + " > " + categorySubTxt + " > " + categorySub2Txt);
                }
                desc.setText(descTxt);
                mobile.setText(mobileTxt);
                price.setText(priceTxt +" "+ priceExTxt);

                Glide.with(imageUrl.getContext())
                        .load(imageUrlTxt)
                        .into(imageUrl);

                if (loadingProgress.isShowing()){
                    loadingProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}