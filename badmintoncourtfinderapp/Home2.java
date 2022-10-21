package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView map, review;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        map = findViewById(R.id.map);
        review = findViewById(R.id.review);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.side_nav);
        toolbar = findViewById(R.id.toolbar);

        //toolbar
        setSupportActionBar(toolbar);

        //navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);






        final String Userid = getIntent().getStringExtra("userid");


        Toast.makeText(Home2.this,Userid,Toast.LENGTH_SHORT).show();

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home2.this,Map.class);
                i.putExtra("userid",Userid);
                startActivity(i);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home2.this,Review.class);
                i.putExtra("userid",Userid);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case  R.id.nav_history:
                String Userid = getIntent().getStringExtra("userid");
                Intent intent = new Intent(Home2.this, History.class);
                intent.putExtra("userid",Userid);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                String Userid2 = getIntent().getStringExtra("userid");
                Intent intent2 = new Intent(Home2.this, Profile.class);
                intent2.putExtra("userid",Userid2);
                startActivity(intent2);
                break;
            case  R.id.logout:
                Intent intent3 = new Intent(Home2.this, Home.class);
                startActivity(intent3);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // public void openMap(View v){
   //
   // }
    //public void openReview(View v){
    //    Intent i = new Intent(this,Review.class);
    //    i.putExtra("userid",Userid);
    //    startActivity(i);
   // }
}


