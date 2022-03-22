package com.example.capstone_project_redo.nav;

import android.content.Intent;
import android.os.Bundle;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.databinding.ActivitySrpBinding;

public class SRPActivity extends DrawerBaseActivity {

    ActivitySrpBinding activity_srp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_srp = ActivitySrpBinding.inflate(getLayoutInflater());
        setContentView(activity_srp.getRoot());
        allocateActivityTitle("DTI's List of SRP's");
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SRPActivity.this, HomePage.class));
        finish();
    }
}