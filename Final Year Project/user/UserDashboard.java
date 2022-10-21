package com.example.cuba2.user;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuba2.Common.LoginSignUp.Login;
import com.example.cuba2.Common.LoginSignUp.StartUpScreen;

import com.example.cuba2.Exercise;
import com.example.cuba2.Food;
import com.example.cuba2.Help;
import com.example.cuba2.KalcMainActivity;
import com.example.cuba2.R;
import com.example.cuba2.activity_history;
import com.example.cuba2.delete;
import com.example.cuba2.tester;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menuIcon,add;
    static final float END_SCALE=0.7f;
    LinearLayout contentView;
    private FirebaseAuth firebaseAuth;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();

        //hooks


        menuIcon = findViewById(R.id.menu_icon);
        add=findViewById(R.id.add);
        contentView=findViewById(R.id.content);


        //menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //navigation drawer


        navigationDrawer();





    }


    //Navigation Drawer Function
    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);


            }
        });

        animateNavigationDrawer();
        
    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setScrimColor(getResources().getColor(R.color.Primary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {


        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId())
        {

            case R.id.nav_home:
                Intent l= new Intent(UserDashboard.this, UserDashboard.class);
                startActivity(l);
                break;

            case R.id.nav_calculator:
                Intent i = new Intent(UserDashboard.this, KalcMainActivity.class);
                startActivity(i);
                break;

            case R.id.nav_dead:
                Intent a= new Intent(UserDashboard.this, delete.class);
                startActivity(a);
                break;

            case R.id.nav_history:
                Intent b = new Intent(UserDashboard.this, activity_history.class);
                startActivity(b);
                break;

            case R.id.nav_profile:
                Intent u = new Intent(UserDashboard.this, user_profile.class);
                startActivity(u);
                break;

            case R.id.nav_help:
                Intent h= new Intent(UserDashboard.this, Help.class);
                startActivity(h);
                break;

            case R.id.nav_logout:
                firebaseAuth.signOut();
                Intent c = new Intent(this, Login.class);
                startActivity(c);
                finish();




        }



        return true;
    }







    public void bay (View view)
    {
        Intent intent=new Intent(getApplicationContext(), Food.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.yaya),"boas");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void xer (View view)
    {
        Intent intent=new Intent(getApplicationContext(), Exercise.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.kurosaki),"sise");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void calcu (View view)
    {
        Intent intent=new Intent(getApplicationContext(), KalcMainActivity.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.calc),"calca");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this,pairs);
        startActivity(intent,options.toBundle());
    }


}