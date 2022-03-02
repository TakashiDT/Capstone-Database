package com.example.capstone_project_redo.nav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.databinding.ActivityAboutBinding;
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
}