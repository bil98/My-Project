package com.example.cuba2.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cuba2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class user_profile extends AppCompatActivity {


    ImageView backbtn;
    TextInputLayout fullname,username,email,password;
    String _fullname,_username,_email,_password;
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference= FirebaseDatabase.getInstance().getReference("user");
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser users = firebaseAuth.getCurrentUser();
        backbtn = findViewById(R.id.profile_back_button);
        fullname=findViewById(R.id.fullname);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);


        String finaluser = users.getUid();



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_profile.super.onBackPressed();
            }
        });

        reference.child(finaluser).child("adult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String fullname2 = dataSnapshot.child("name").getValue().toString();
                String username2 = dataSnapshot.child("username").getValue().toString();
                String email2=dataSnapshot.child("email").getValue().toString();
                String password2=dataSnapshot.child("password").getValue().toString();



                fullname.getEditText().setText(fullname2);
                username.getEditText().setText(username2);
                email.getEditText().setText(email2);
                password.getEditText().setText(password2);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }

    public void update(View view)
    {

        FirebaseUser users = firebaseAuth.getCurrentUser();
        final String finaluser = users.getUid();

        String fname = fullname.getEditText().getText().toString();
        String uname = username.getEditText().getText().toString();
        String e_mail=email.getEditText().getText().toString();
        String pword=password.getEditText().getText().toString();


        if(!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(uname) && !TextUtils.isEmpty(e_mail) && !TextUtils.isEmpty(pword))
        {
            reference.child(finaluser).child("adult").child("name").setValue(fname);
            reference.child(finaluser).child("adult").child("username").setValue(uname);
            reference.child(finaluser).child("adult").child("email").setValue(e_mail);
            reference.child(finaluser).child("adult").child("password").setValue(pword);



            Toast.makeText(getApplicationContext(),"Profile update successfully",Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(getApplicationContext(),user_profile.class);
            startActivity(i);

        }
        else {
            Toast.makeText(user_profile.this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();
        }

    }


}