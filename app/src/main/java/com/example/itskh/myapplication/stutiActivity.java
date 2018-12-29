package com.example.itskh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class stutiActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBarDrawerToggle toggle;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager mviewpager;
    private stutiActivityAdapter mstutiActivityAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuti);

        toolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        mviewpager = findViewById(R.id.stuti_pager);
        mstutiActivityAdapter = new stutiActivityAdapter(getSupportFragmentManager());
        mviewpager.setAdapter(mstutiActivityAdapter);
        tabLayout=findViewById(R.id.stutiTab);
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mviewpager);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.home)
        {
            Intent homeintent = new Intent(this, MainActivity.class);
            startActivity(homeintent);
        }
        else if (id== R.id.about)
        {
            Intent intent = new Intent(this, aboutme.class);
            startActivity(intent);
        }
        else if (id==R.id.stuti)
        {
            Intent intent = new Intent(this,stutiActivity.class);
            startActivity(intent);
        }

        return false;
    }
}
