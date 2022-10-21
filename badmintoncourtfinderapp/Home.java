package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    TextInputLayout phone, password;
    Button login;
    DatabaseReference UserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        UserReference = FirebaseDatabase.getInstance().getReference().child("Users");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pho = phone.getEditText().getText().toString();
                final String passw = password.getEditText().getText().toString();

                if(!TextUtils.isEmpty(pho) && !TextUtils.isEmpty(passw))
                {
                    UserReference.child(pho).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(dataSnapshot.child("password").getValue().equals(passw)){
                                    Toast.makeText(getApplicationContext(),"Successful login",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(),Home2.class);
                                    i.putExtra("userid",pho);
                                    startActivity(i);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Please re enter password",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Please re enter username",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill up empty form",Toast.LENGTH_SHORT).show();

                }






            }
        });
    }



    public void openSignUp(View v){
        Intent i = new Intent(this,SignUp.class);
        startActivity(i);
    }

    public void openMap(View v){
        Intent i = new Intent(this,Map.class);
        startActivity(i);
    }
}
