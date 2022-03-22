package com.example.capstone_project_redo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.capstone_project_redo.admin.ActiveUsers;
import com.example.capstone_project_redo.admin.PendingUsers;
import com.example.capstone_project_redo.nav.AboutActivity;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.example.capstone_project_redo.nav.HomePage;
import com.example.capstone_project_redo.nav.MyProductsActivity;
import com.example.capstone_project_redo.nav.MyProfileActivity;
import com.example.capstone_project_redo.nav.SRPActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            hideItem();
        }
        else if (user.getUid().equals("y0HGN02WYaTK4GaefHjpSQUNzyz2")) {
            hidefromAdmin();
        }
        else {
            hideAdmin();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, HomePage.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_category:
                startActivity(new Intent(this, CategoryActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_profile:
                startActivity(new Intent(this, MyProfileActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_upload:
                startActivity(new Intent(this, MyProductsActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_dtisrp:
                startActivity(new Intent(this, SRPActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_pending:
                startActivity(new Intent(this, PendingUsers.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_active:
                startActivity(new Intent(this, ActiveUsers.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(0, 0);
                break;
        }

        return false;
    }
    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_profile).setVisible(false);
        nav_Menu.findItem(R.id.nav_upload).setVisible(false);
        nav_Menu.findItem(R.id.nav_pending).setVisible(false);
        nav_Menu.findItem(R.id.nav_active).setVisible(false);
        invalidateOptionsMenu();
    }

    private void hidefromAdmin() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_home).setVisible(false);
        nav_Menu.findItem(R.id.nav_category).setVisible(false);
        nav_Menu.findItem(R.id.nav_dtisrp).setVisible(false);
        nav_Menu.findItem(R.id.nav_about).setVisible(false);
        nav_Menu.findItem(R.id.nav_profile).setVisible(false);
        nav_Menu.findItem(R.id.nav_upload).setVisible(false);
        invalidateOptionsMenu();
    }

    private void hideAdmin() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_pending).setVisible(false);
        nav_Menu.findItem(R.id.nav_active).setVisible(false);
        invalidateOptionsMenu();
    }

    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}