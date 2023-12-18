package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static Global.Info.currentUser;

public class MainAgendaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //TextView userNameText;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agenda);
        drawerLayout = findViewById(R.id.mainAgenda);
        //Set users name on navigation header
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView)headerView.findViewById(R.id.textViewUser_NavMenu);
        navUsername.setText(currentUser.getDisplayName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
            case (R.id.nav_home):
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new MainFragment()).commit();
                return true;
            case (R.id.nav_logout):
                onClickLogOut();
                return true;
            case (R.id.nav_addJob):
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new AddJobFragment()).commit();
                return true;
            case (R.id.nav_listJob):
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new ListJobFragment()).commit();
                return true;
            case (R.id.nav_listSubject):
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new DeleteSubjectFragment()).commit();
                return true;
        }
        return false;
    }

    public void onClickLogOut() {
        FirebaseAuth.getInstance().signOut();
        Intent changeLogIn = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(changeLogIn);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainAgendaActivity.class));
        finish();
    }
}