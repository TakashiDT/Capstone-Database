package com.example.capstone_project_redo.nav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstone_project_redo.DrawerBaseActivity;
import com.example.capstone_project_redo.databinding.ActivityAboutBinding;
import com.example.capstone_project_redo.databinding.ActivitySRPBinding;

public class SRPActivity extends DrawerBaseActivity {

    ActivitySRPBinding activity_s_r_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_s_r_p = ActivitySRPBinding.inflate(getLayoutInflater());
        setContentView(activity_s_r_p.getRoot());
        allocateActivityTitle("DTI's List of SRP's");
    }
}