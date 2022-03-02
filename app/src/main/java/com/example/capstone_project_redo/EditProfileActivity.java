package com.example.capstone_project_redo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.capstone_project_redo.nav.MyProfileActivity;
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

public class EditProfileActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");
    StorageReference uItemStorageRef;
    Uri imageUri;
    ProgressDialog progressDialog;
    String imageProofUrl;
    String stringGlide;

    EditText editfirstname, editlastname, editage, editmunicipality, editprovince, editusername, editmobile;
    Button Updateprofile, selectImageBtn, clearImageBtn;
    ImageView addProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Profile Information ID
        editfirstname = findViewById(R.id.e_firstname);
        editlastname = findViewById(R.id.e_lastname);
        editage = findViewById(R.id.e_age);
        editmunicipality = findViewById(R.id.e_municipality);
        editmobile = findViewById(R.id.e_mobilenumber);
        editprovince = findViewById(R.id.e_province);
        editusername = findViewById(R.id.e_username);
        addProfileImage = findViewById(R.id.iv_addProfileImage);

        //Button ID
        selectImageBtn = findViewById(R.id.btn_selectImage);
        clearImageBtn = findViewById(R.id.btn_clearProfileImage);
        Updateprofile = findViewById(R.id.btn_UpdateProfile);

        progressDialog = new ProgressDialog(this);


        ActivityResultLauncher<String> mGetImage = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            imageUri = result;
                            addProfileImage.setImageURI(imageUri);
                        }
                    }
                });


        selectImageBtn.setOnClickListener(v -> mGetImage.launch("image/*"));
        clearImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfileImage.setImageResource(0);
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(this, "" + user.getUid(), Toast.LENGTH_SHORT).show();

        databaseReference = database.getReference("users").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String username = (String) snapshot.child("Username").getValue();
                String firstName = (String) snapshot.child("FirstName").getValue();
                String lastName = (String) snapshot.child("LastName").getValue();
                String age = (String) snapshot.child("Age").getValue();
                String municipality = (String) snapshot.child("Municipality").getValue();
                String province = (String) snapshot.child("Province").getValue();
                String mobile = (String) snapshot.child("MobileNumber").getValue();
                String url = (String) snapshot.child("ImageProfile").getValue();


                editfirstname.setText(firstName);
                editlastname.setText(lastName);
                editage.setText(age);
                editmunicipality.setText(municipality);
                editprovince.setText(province);
                editmobile.setText(mobile);
                editusername.setText(username);
                stringGlide = url;
                Glide.with(EditProfileActivity.this).load(url).into(addProfileImage);

                Updateprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String firstname_edit = editfirstname.getText().toString();
                        final String lastname_edit = editlastname.getText().toString();
                        final String age_edit = editage.getText().toString();
                        final String province_edit = editprovince.getText().toString();
                        final String municipality_edit = editmunicipality.getText().toString();
                        final String username_edit = editusername.getText().toString();
                        final String mobile_edit = editmobile.getText().toString();


                        if (addProfileImage.getDrawable() == null) {
                            Toast.makeText(EditProfileActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (firstname_edit.isEmpty() || lastname_edit.isEmpty() || age_edit.isEmpty() || province_edit.isEmpty() || municipality_edit.isEmpty() || username_edit.isEmpty() || mobile_edit.isEmpty()) {
                                Toast.makeText(EditProfileActivity.this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("FirstName").setValue(firstname_edit);
                                databaseReference.child("LastName").setValue(lastname_edit);
                                databaseReference.child("Age").setValue(age_edit);
                                databaseReference.child("MobileNumber").setValue(mobile_edit);
                                databaseReference.child("Municipality").setValue(municipality_edit);
                                databaseReference.child("Province").setValue(province_edit);
                                databaseReference.child("Username").setValue(username_edit);
                                Toast.makeText(EditProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                if (stringGlide == null) {
                                    uploadImage();
                                }


                                ProfileTransition();
                            }
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            private void ProfileTransition() {
                Intent intent = new Intent(EditProfileActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });

    }
    private void uploadImage() {

        String profileKey = database.getReference().push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            uItemStorageRef = FirebaseStorage.getInstance().getReference("users/" + uid +"/"+"Profile/"+profileKey);
            uItemStorageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uItemStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageProofUrl = uri.toString();
                            databaseReference.child("ImageProfile").setValue(imageProofUrl);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
            });
        }
    }
}