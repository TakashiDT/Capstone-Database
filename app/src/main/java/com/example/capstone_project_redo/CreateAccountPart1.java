package com.example.capstone_project_redo;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAccountPart1 extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");
    StorageReference uItemStorageRef;
    FirebaseAuth uAuth;
    Uri imageUri;
    ProgressDialog progressDialog;
    String imageProofUrl;

    EditText registerEmail, registerPassword, confirmPassword;

    Button selectImageBtn, clearImageBtn, uploadDataBtn;
    ImageView addValidationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_part1);

        uAuth = FirebaseAuth.getInstance();
        selectImageBtn = findViewById(R.id.btn_registerSelectImg);
        clearImageBtn = findViewById(R.id.btn_registerClearImg);
        addValidationImage = findViewById(R.id.iv_validationImage);
        uploadDataBtn = findViewById(R.id.btn_continue);


        progressDialog = new ProgressDialog(this);

        selectImageBtn.setOnClickListener(v -> mGetImage.launch("image/*"));
        clearImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addValidationImage.setImageResource(0);
            }
        });

        Button goToLogIn = (Button)findViewById(R.id.logInGuest);
        goToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountPart1.this, LoginActivity.class));
            }
        });

        uploadDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEmail = findViewById(R.id.register_email);
                registerPassword = findViewById(R.id.register_password);
                confirmPassword = findViewById(R.id.confirm_password);
                String registerEmailTxt = registerEmail.getText().toString();
                String registerPasswordTxt = registerPassword.getText().toString();
                String confirmPasswordTxt = confirmPassword.getText().toString();

                if (registerEmailTxt.trim().equals("") || registerPasswordTxt.trim().equals("") || confirmPasswordTxt.trim().equals("")) {
                    Toast.makeText(CreateAccountPart1.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (registerPasswordTxt.length() < 6) {
                        Toast.makeText(CreateAccountPart1.this, "Password too short", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (!registerPasswordTxt.equals(confirmPasswordTxt)) {
                            Toast.makeText(CreateAccountPart1.this, "Password does not match", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (addValidationImage.getDrawable()==null) {
                                Toast.makeText(CreateAccountPart1.this, "Please select an image", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                register(view);
                            }
                        }
                    }
                }
            }
        });
    }

    public void register(View view) {

        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        String registerEmailTxt = registerEmail.getText().toString();
        String registerPasswordTxt = registerPassword.getText().toString();

        progressDialog.setMessage("Please wait");
        progressDialog.show();

        uAuth.createUserWithEmailAndPassword(registerEmailTxt, registerPasswordTxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.hide();
                            Toast.makeText(CreateAccountPart1.this, "First Part Completed", Toast.LENGTH_SHORT).show();

                            uAuth.signInWithEmailAndPassword(registerEmailTxt, registerPasswordTxt)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                uploadImage();
                                                Intent i = new Intent(CreateAccountPart1.this, CreateAccountPart2.class);
                                                i.putExtra("email", registerEmailTxt);
                                                i.putExtra("password", registerPasswordTxt);
                                                startActivity(i);
                                            }

                                        }
                                    });
                        }
                        else {
                            progressDialog.hide();
                            Toast.makeText(CreateAccountPart1.this, "Invalid Email or Email has already been taken", Toast.LENGTH_SHORT).show();
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
                        addValidationImage.setImageURI(imageUri);
                    }
                }
            });

    private void uploadImage() {

        String validationKey = database.getReference().push().getKey();

        FirebaseUser currentUser = uAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            uItemStorageRef = FirebaseStorage.getInstance().getReference("users/" + uid +"/"+"imageProof/" + validationKey);

            uItemStorageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uItemStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageProofUrl = uri.toString();
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