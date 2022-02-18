package com.example.capstone_project_redo;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddItemActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    StorageReference uItemStorageRef;
    Uri imageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser = user.getUid();
    String productImageUrl;

    ProgressDialog imageUploadProgress;

    EditText productName, productCategory;

    Button selectImageBtn, clearImageBtn, uploadImageBtn;
    ImageView addProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        selectImageBtn = findViewById(R.id.btn_selectImage);
        clearImageBtn = findViewById(R.id.btn_clearImage);
        addProductImage = findViewById(R.id.iv_addProductImage);
        uploadImageBtn = findViewById(R.id.btn_uploadImage);
        //Spinner chooseCategory = (Spinner)findViewById(R.id.spinnerCategory);

        selectImageBtn.setOnClickListener(v -> mGetImage.launch("image/*"));
        clearImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductImage.setImageResource(0);
            }
        });

        // Calls the uploadImage Method when clicked
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productName = findViewById(R.id.et_productName);
                productCategory = findViewById(R.id.et_productCategory);
                String productNameTxt = productName.getText().toString();
                String productCategoryTxt = productCategory.getText().toString();

                if (addProductImage == null) {
                    Toast.makeText(AddItemActivity.this,"Please Choose a photo",Toast.LENGTH_SHORT).show();
                }
                else {

                    if (productNameTxt.isEmpty() && productCategoryTxt.isEmpty()) {
                        Toast.makeText(AddItemActivity.this,"Fill in all fields",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadImage();
                        addProductImage.setImageURI(null);
                        productName.setText(null);
                        productCategory.setText(null);
                    }
                }

            }
        });
    }

    // This enables users to pick an image,then receive its uri
    ActivityResultLauncher<String> mGetImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        imageUri = result;
                        addProductImage.setImageURI(imageUri);

                    }
                }
            });

    private void uploadImage() {

        imageUploadProgress = new ProgressDialog(this);
        imageUploadProgress.setTitle("Uploading File...");
        imageUploadProgress.show();

        productName = findViewById(R.id.et_productName);
        productCategory = findViewById(R.id.et_productCategory);
        String productNameTxt = productName.getText().toString();
        String productCategoryTxt = productCategory.getText().toString();

        SimpleDateFormat imageFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String filename = imageFormat.format(now);

        String productKey = database.getReference("products").push().getKey();

        uItemStorageRef = FirebaseStorage.getInstance().getReference("users/"+currentUser+"/"+"Products/"+productKey);

        uItemStorageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uItemStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        productImageUrl = uri.toString();
                        databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");

                        // Checks the database for children as integers, starts from 0 onwards.
                        DatabaseReference productsRef = database.getReference().child("products").child("1allID").child(currentUser);
                        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int productCount = (int) snapshot.getChildrenCount();
                                String productCountTxt = Integer.toString(productCount);
                                do {
                                    databaseReference.child("products").child("1allID").child(currentUser).child(productCountTxt).setValue(productKey);
                                }
                                while (snapshot.hasChild(productCountTxt));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AddItemActivity.this,"Failed to upload product",Toast.LENGTH_SHORT).show();
                            }
                        });


                        databaseReference.child("products").child(currentUser).child(productKey).child("id").setValue(productKey);
                        databaseReference.child("products").child(currentUser).child(productKey).child("name").setValue(productNameTxt);
                        databaseReference.child("products").child(currentUser).child(productKey).child("category").setValue(productCategoryTxt);
                        databaseReference.child("products").child(currentUser).child(productKey).child("imageUrl").setValue(productImageUrl);
                        databaseReference.child("products").child(currentUser).child(productKey).child("dateUploaded").setValue(filename);
                        Toast.makeText(AddItemActivity.this,"Successfully Uploaded Data",Toast.LENGTH_SHORT).show();
                    }
                });
                if (imageUploadProgress.isShowing()) {
                    imageUploadProgress.dismiss();
                }
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (imageUploadProgress.isShowing()) {
                    imageUploadProgress.dismiss();
                }
                Toast.makeText(AddItemActivity.this,"Failed to Upload Data",Toast.LENGTH_SHORT).show();
            }
        });


    }

}