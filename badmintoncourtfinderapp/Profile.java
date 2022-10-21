package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseReference Users;
    TextInputLayout fname, uname, email, pass;
    Button submit;
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.side_nav);
        toolbar = findViewById(R.id.toolbar);

        Users = FirebaseDatabase.getInstance().getReference("Users");
        fname = findViewById(R.id.fullname);
        uname = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        submit = findViewById(R.id.updatebutton);
        phone = findViewById(R.id.phone);


        //toolbar
        setSupportActionBar(toolbar);

        //navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);

        final String Userid = getIntent().getStringExtra("userid");

        Users.child(Userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String funame = dataSnapshot.child("fullname").getValue().toString();
                String uname2 = dataSnapshot.child("username").getValue().toString();
                String email2 = dataSnapshot.child("email").getValue().toString();
                String pass2 = dataSnapshot.child("password").getValue().toString();
                String phone2 = dataSnapshot.child("phone").getValue().toString();

                fname.getEditText().setText(funame);
                uname.getEditText().setText(uname2);
                email.getEditText().setText(email2);
                pass.getEditText().setText(pass2);
                phone.setText(phone2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname3 = fname.getEditText().getText().toString();
                String uname3 = uname.getEditText().getText().toString();
                String email3 = email.getEditText().getText().toString();
                String pass3 = pass.getEditText().getText().toString();

                if(!TextUtils.isEmpty(fname3) && !TextUtils.isEmpty(uname3) && !TextUtils.isEmpty(email3) && !TextUtils.isEmpty(pass3))
                {
                    Users.child(Userid).child("fullname").setValue(fname3);
                    Users.child(Userid).child("username").setValue(uname3);
                    Users.child(Userid).child("email").setValue(email3);
                    Users.child(Userid).child("password").setValue(pass3);

                    Toast.makeText(getApplicationContext(),"Profile update successfully",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(getApplicationContext(),Profile.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(Profile.this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();

                }
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
                String Userid2 = getIntent().getStringExtra("userid");
                Intent intent2 = new Intent(Profile.this, Home2.class);
                intent2.putExtra("userid",Userid2);
                startActivity(intent2);
                break;
            case  R.id.nav_history:
                String Userid = getIntent().getStringExtra("userid");
                Intent intent = new Intent(Profile.this, History.class);
                intent.putExtra("userid",Userid);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                break;
            case  R.id.logout:
                Intent intent3 = new Intent(Profile.this, Home.class);
                startActivity(intent3);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
