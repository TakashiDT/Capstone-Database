package com.example.capstone_project_redo.nav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.HomePage;
import com.example.capstone_project_redo.LoginActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.adapter.AboutAdapter;
import com.example.capstone_project_redo.adapter.CategoryAdapter;
import com.example.capstone_project_redo.databinding.ActivityAboutBinding;
import com.example.capstone_project_redo.databinding.ActivityMyProfileBinding;
import com.example.capstone_project_redo.model.AboutModel;
import com.example.capstone_project_redo.model.CategoryModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends DrawerBaseActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://loginregister-f1e0d-default-rtdb.firebaseio.com");
    ProgressDialog loadingProgress;
    RecyclerView faqList;
    AboutAdapter aboutAdapter;

    ActivityAboutBinding activityAboutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(activityAboutBinding.getRoot());
        allocateActivityTitle("About the App & FAQ");

        loadingProgress = new ProgressDialog(this);
        loadingProgress.setMessage("Loading Questions, Please Wait...");
        loadingProgress.setCancelable(false);
        loadingProgress.show();

        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        aboutAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        aboutAdapter.stopListening();
    }

    private void loadData() {
        faqList = findViewById(R.id.lv_faqList);
        faqList.setHasFixedSize(true);
        faqList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AboutModel> options =
                new FirebaseRecyclerOptions.Builder<AboutModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("faq"), AboutModel.class)
                        .build();


        aboutAdapter = new AboutAdapter(options);
        faqList.setAdapter(aboutAdapter);

        databaseReference = database.getReference("products");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (loadingProgress.isShowing()) {
                    loadingProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (loadingProgress.isShowing()) {
                    loadingProgress.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AboutActivity.this, HomePage.class));
        finish();
    }
}