package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class History extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;

    DatabaseReference reviewReference;

    FirebaseRecyclerAdapter<ReviewDetailsConstructor,CommentHolder> adapter;
    FirebaseRecyclerOptions<ReviewDetailsConstructor> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.side_nav);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.review);
        reviewReference = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        String Userid = getIntent().getStringExtra("userid");

        //toolbar
        setSupportActionBar(toolbar);

        //navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_history);

        final Query query = reviewReference.child(Userid).child("History").orderByChild("reviewid");
        options = new FirebaseRecyclerOptions.Builder<ReviewDetailsConstructor>().setQuery(query,ReviewDetailsConstructor.class).build();
        adapter = new FirebaseRecyclerAdapter<ReviewDetailsConstructor, CommentHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CommentHolder commentHolder, int i, @NonNull final ReviewDetailsConstructor reviewDetailsConstructor) {


                commentHolder.holdercomtime.setText(reviewDetailsConstructor.getReviewdate());
                commentHolder.holderrating.setRating(Float.parseFloat(reviewDetailsConstructor.getReviewrate()));
                commentHolder.holdercomment.setText(reviewDetailsConstructor.getReviewtext());

                final String key = reviewDetailsConstructor.getUserid();

                reviewReference = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
                reviewReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String uname = dataSnapshot.child("username").getValue().toString();
                        String imagename = dataSnapshot.child("imageurl").getValue().toString();

                        commentHolder.holdercomname.setText(uname);
                        Picasso.get().load(imagename).into(commentHolder.holdercompic);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,parent,false);
                return new CommentHolder(v2);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
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
                String Userid = getIntent().getStringExtra("userid");
                Intent intent2 = new Intent(History.this, Home2.class);
                intent2.putExtra("userid",Userid);
                startActivity(intent2);
                break;
            case  R.id.nav_history:
                break;
            case R.id.nav_profile:
                String Userid2 = getIntent().getStringExtra("userid");
                Intent intent = new Intent(History.this, Profile.class);
                intent.putExtra("userid",Userid2);
                startActivity(intent);
                break;
            case  R.id.logout:
                Intent intent3 = new Intent(History.this, Home.class);
                startActivity(intent3);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
