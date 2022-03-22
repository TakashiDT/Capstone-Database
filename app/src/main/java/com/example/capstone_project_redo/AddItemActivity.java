package com.example.capstone_project_redo;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddItemActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    DatabaseReference userNameRef;
    StorageReference uItemStorageRef;
    Uri imageUri;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser = user.getUid();
    String productImageUrl;

    ProgressDialog imageUploadProgress;

    Spinner mainCategory, subCategory, subCategory2, priceExtension;
    List<String> subCategoryList = new ArrayList<>();
    List<String> subCategory2List = new ArrayList<>();

    EditText productName, productPrice, productDescription;
    String mainCatItem, subCatItem, subCat2Item, priceExtensionTxt;
    String dropDownCheck, dropDownCheck2;
    //Boolean subCatBool = false, subCat2Bool = false;
    Button selectImageBtn, clearImageBtn, uploadImageBtn;
    ImageView addProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        selectImageBtn = findViewById(R.id.btn_registerSelectImg);
        clearImageBtn = findViewById(R.id.btn_registerClearImg);
        addProductImage = findViewById(R.id.iv_addProductImage);
        uploadImageBtn = findViewById(R.id.btn_uploadImage);

        itemCategories();

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
                productPrice = findViewById(R.id.et_productPrice);
                productDescription = findViewById(R.id.et_productDescription);
                String productNameTxt = productName.getText().toString();
                String productPriceTxt = productPrice.getText().toString();
                String productDescTxt = productDescription.getText().toString();

                if (addProductImage.getDrawable() == null) {
                    Toast.makeText(AddItemActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (productNameTxt.isEmpty() || productPriceTxt.isEmpty() || productDescTxt.isEmpty() || mainCatItem==null || priceExtensionTxt==null) {
                        Toast.makeText(AddItemActivity.this,"Fill in all fields",Toast.LENGTH_SHORT).show();
                    }
                    else if (dropDownCheck.equals("t")) {
                        if (subCatItem == null) {
                            Toast.makeText(AddItemActivity.this, "Select a SubCategory", Toast.LENGTH_SHORT).show();
                        }
                        else if (dropDownCheck2.equals("t")){
                            if (subCat2Item == null) {
                                Toast.makeText(AddItemActivity.this, "Select a Category inside of SubCategory", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                uploadData();
                            }
                        }
                        else {
                            uploadData();
                        }
                    }
                    else {
                        uploadData();
                        /*
                        addProductImage.setImageURI(null);
                        productName.setText(null);
                        productPrice.setText(null);
                        productDescription.setText(null);

                         */
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

    private void uploadData() {

        imageUploadProgress = new ProgressDialog(this);
        imageUploadProgress.setTitle("Uploading File...");
        imageUploadProgress.show();

        productName = findViewById(R.id.et_productName);
        productPrice = findViewById(R.id.et_productPrice);
        productDescription = findViewById(R.id.et_productDescription);
        String productNameTxt = productName.getText().toString();
        String productPriceTxt = productPrice.getText().toString();
        String productDescTxt = productDescription.getText().toString();

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

                        /* Checks the database for children as integers, starts from 0 onwards.
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

                         */
                        // UPLOADS DATA TO "PRODUCTS"
                        databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");

                        databaseReference.child("products").child(currentUser).child(productKey).child("productId").setValue(productKey);
                        databaseReference.child("products").child(currentUser).child(productKey).child("name").setValue(productNameTxt);
                        databaseReference.child("products").child(currentUser).child(productKey).child("price").setValue(productPriceTxt);
                        databaseReference.child("products").child(currentUser).child(productKey).child("priceExtension").setValue(priceExtensionTxt);
                        databaseReference.child("products").child(currentUser).child(productKey).child("description").setValue(productDescTxt);
                        databaseReference.child("products").child(currentUser).child(productKey).child("imageUrl").setValue(productImageUrl);
                        databaseReference.child("products").child(currentUser).child(productKey).child("dateUploaded").setValue(filename);
                        if (subCatItem == null && subCat2Item == null) {
                            databaseReference.child("products").child(currentUser).child(productKey).child("category").setValue(mainCatItem);
                        }
                        else if (subCatItem != null && subCat2Item == null) {
                            databaseReference.child("products").child(currentUser).child(productKey).child("category").setValue(mainCatItem);
                            databaseReference.child("products").child(currentUser).child(productKey).child("categorySub").setValue(subCatItem);
                        }
                        else {
                            databaseReference.child("products").child(currentUser).child(productKey).child("category").setValue(mainCatItem);
                            databaseReference.child("products").child(currentUser).child(productKey).child("categorySub").setValue(subCatItem);
                            databaseReference.child("products").child(currentUser).child(productKey).child("categorySub2").setValue(subCat2Item);
                        }
                        Toast.makeText(AddItemActivity.this,"Successfully Uploaded Data",Toast.LENGTH_SHORT).show();


                        userNameRef = database.getReference("users").child(user.getUid());
                        userNameRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String usernameTxt = (String) snapshot.child("Username").getValue();
                                String mobileTxt = (String) snapshot.child("MobileNumber").getValue();

                                if (subCatItem == null && subCat2Item == null) {
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("id").setValue(currentUser);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("productId").setValue(productKey);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("name").setValue(productNameTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("seller").setValue(usernameTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("mobile").setValue(mobileTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("price").setValue(productPriceTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("priceExtension").setValue(priceExtensionTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("description").setValue(productDescTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("imageUrl").setValue(productImageUrl);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("category").setValue(mainCatItem);

                                }
                                else if (subCatItem != null && subCat2Item == null) {
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("id").setValue(currentUser);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("productId").setValue(productKey);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("name").setValue(productNameTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("seller").setValue(usernameTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("mobile").setValue(mobileTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("price").setValue(productPriceTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("priceExtension").setValue(priceExtensionTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("description").setValue(productDescTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("imageUrl").setValue(productImageUrl);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("category").setValue(mainCatItem);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("categorySub").setValue(subCatItem);

                                }
                                else {
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("id").setValue(currentUser);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("productId").setValue(productKey);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("name").setValue(productNameTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("seller").setValue(usernameTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("mobile").setValue(mobileTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("price").setValue(productPriceTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("priceExtension").setValue(priceExtensionTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("description").setValue(productDescTxt);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("imageUrl").setValue(productImageUrl);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("category").setValue(mainCatItem);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("categorySub").setValue(subCatItem);
                                    databaseReference.child("categories").child(mainCatItem).child(productKey).child("categorySub2").setValue(subCat2Item);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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

    public void itemCategories() {

        //Spinner
        priceExtension = findViewById(R.id.spinner_priceExtension);
        mainCategory = findViewById(R.id.spinner_mainCategory);
        subCategory = findViewById(R.id.spinner_subCategory);
        subCategory2 = findViewById(R.id.spinner_subCategory2);
        subCategory.setVisibility(View.GONE); subCategory2.setVisibility(View.GONE);

        List<String> priceExtensionList = new ArrayList<>();
        Collections.addAll(priceExtensionList, "Select Price Extension","per kg","per pc","per bag","per box");
        // PRICE EXTENSION
        ArrayAdapter<String> priceExtensionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priceExtensionList);
        priceExtensionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceExtension.setAdapter(priceExtensionAdapter);
        priceExtension.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(AddItemActivity.this, "Please Select a Price Extension", Toast.LENGTH_SHORT).show();
                    priceExtensionTxt = null;
                }
                else if (i == 1) {
                    priceExtensionTxt = "per kg";
                }
                else if (i == 2) {
                    priceExtensionTxt = "per pc";
                }
                else if (i == 3) {
                    priceExtensionTxt = "per bag";
                }
                else if (i == 4) {
                    priceExtensionTxt = "per box";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> mainCategoryList = new ArrayList<>();
        Collections.addAll(mainCategoryList, "Select Category","Food","Household Essentials","Crafted Goods");

        // THE MAIN CATEGORY
        ArrayAdapter<String> mainCatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mainCategoryList);
        mainCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainCategory.setAdapter(mainCatAdapter);
        mainCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(AddItemActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
                    dropDownCheck = "f";
                    dropDownCheck2 = "f";
                    mainCatItem = null;
                    subCategoryList.clear();
                    subCategory.setVisibility(View.GONE);
                    subCategory2.setVisibility(View.GONE);
                }
                else if (i == 1) {
                    subCategoryList.clear();
                    Collections.addAll(subCategoryList, "Select Food Category","Meat","Processed Food","Seafood","Fruits","Vegetables");
                    subCategory.setVisibility(View.VISIBLE);
                    subCategoryFill();
                    mainCatItem = "Food";
                }
                else if (i==2) {
                    dropDownCheck = "f";
                    dropDownCheck2 = "f";
                    subCategoryList.clear();
                    subCategory.setVisibility(View.GONE);
                    subCategory2.setVisibility(View.GONE);
                    mainCatItem = "Household Essentials";
                }
                else if (i==3) {
                    dropDownCheck = "f";
                    dropDownCheck2 = "f";
                    subCategoryList.clear();
                    subCategory.setVisibility(View.GONE);
                    subCategory2.setVisibility(View.GONE);
                    mainCatItem = "Crafted Goods";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddItemActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
            }
        });
    }
                // THE SUBCATEGORY INSIDE MAIN CATEGORY
                public void subCategoryFill() {
                    ArrayAdapter<String> subCatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subCategoryList);
                    subCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategory.setAdapter(subCatAdapter);
                    subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) {
                                Toast.makeText(AddItemActivity.this, "Please select a subcategory", Toast.LENGTH_SHORT).show();
                                dropDownCheck = "t";
                                dropDownCheck2 = "f";
                                subCatItem = null;
                                subCategory2List.clear();
                                subCategory2.setVisibility(View.GONE);
                            }
                            else if (i == 1) {
                                subCategory2List.clear();
                                Collections.addAll(subCategory2List, "Select Meat Category","Chicken","Pork","Beef");
                                subCategory2.setVisibility(View.VISIBLE);
                                subCategoryMeatFill();
                                subCatItem = "Meat";
                            }
                            else if (i == 2) {
                                subCategory2List.clear();
                                Collections.addAll(subCategory2List, "Select Processed Food Category","Frozen","Canned");
                                subCategory2.setVisibility(View.VISIBLE);
                                subCategoryProcessFill();
                                subCatItem = "Processed Food";
                            }
                            else if (i == 3) {
                                subCategory2List.clear();
                                Collections.addAll(subCategory2List, "Select Seafood Category","Fish","Shellfish");
                                subCategory2.setVisibility(View.VISIBLE);
                                subCategorySeaFill();
                                subCatItem = "Seafood";
                            }
                            else if (i==4) {
                                subCategory2List.clear();
                                subCategory2.setVisibility(View.GONE);
                                subCatItem = "Fruits";
                            }
                            else if (i==5) {
                                subCategory2List.clear();
                                subCategory2.setVisibility(View.GONE);
                                subCatItem = "Vegetables";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            Toast.makeText(AddItemActivity.this, "Please select a subcategory", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                            // THE SUBCATEGORY INSIDE THE SUBCATEGORY
                            public void subCategoryMeatFill() {
                                ArrayAdapter<String> subCatAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subCategory2List);
                                subCatAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subCategory2.setAdapter(subCatAdapter2);
                                subCategory2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if (i == 0) {
                                            Toast.makeText(AddItemActivity.this, "Please select animal meat", Toast.LENGTH_SHORT).show();
                                            dropDownCheck2 = "t";
                                            subCat2Item = null;
                                        }
                                        else if (i == 1) {
                                            subCat2Item = "Chicken";
                                        }
                                        else if (i == 2) {
                                            subCat2Item = "Pork";
                                        }
                                        else if (i == 3) {
                                            subCat2Item = "Beef";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        Toast.makeText(AddItemActivity.this, "Please select animal meat", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            public void subCategoryProcessFill() {
                                ArrayAdapter<String> subCatAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subCategory2List);
                                subCatAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subCategory2.setAdapter(subCatAdapter2);
                                subCategory2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if (i == 0) {
                                            Toast.makeText(AddItemActivity.this, "Please select type of processed food", Toast.LENGTH_SHORT).show();
                                            dropDownCheck2 = "t";
                                            subCat2Item = null;
                                        }
                                        else if (i == 1) {
                                            subCat2Item = "Frozen";
                                        }
                                        else if (i == 2) {
                                            subCat2Item = "Canned";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        Toast.makeText(AddItemActivity.this, "Please select type of processed food", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            public void subCategorySeaFill() {
                                ArrayAdapter<String> subCatAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subCategory2List);
                                subCatAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subCategory2.setAdapter(subCatAdapter2);
                                subCategory2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if (i == 0) {
                                            Toast.makeText(AddItemActivity.this, "Please select type of seafood", Toast.LENGTH_SHORT).show();
                                            dropDownCheck2 = "t";
                                            subCat2Item = null;
                                        }
                                        else if (i == 1) {
                                            subCat2Item = "Fish";
                                        }
                                        else if (i == 2) {
                                            subCat2Item = "Shellfish";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        Toast.makeText(AddItemActivity.this, "Please select type of seafood", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

    public static class EditProductActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_product);
        }
    }
}