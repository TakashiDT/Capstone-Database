package com.example.capstone_project_redo.admin;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.capstone_project_redo.AdminActivity;
import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.databinding.AdminUsersBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ActiveUsers extends DrawerBaseActivity {

    RecyclerView userList;
    AdminAdapter adminAdapter;

    AdminUsersBinding usersBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        usersBinding = AdminUsersBinding.inflate(getLayoutInflater());
        setContentView(usersBinding.getRoot());
        allocateActivityTitle("Admin: Disable Accounts");

        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adminAdapter.startListening();
        adminAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adminAdapter.stopListening();
    }

    private void loadData() {
        userList = findViewById(R.id.lv_adminList);
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<AdminModel> options =
                new FirebaseRecyclerOptions.Builder<AdminModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("activate").equalTo("true"), AdminModel.class)
                        .build();

        adminAdapter = new AdminAdapter(options);
        userList.setAdapter(adminAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActiveUsers.this, AdminActivity.class));
        finish();
    }
}