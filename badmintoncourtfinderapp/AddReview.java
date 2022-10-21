package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class AddReview extends AppCompatActivity {

    TextInputLayout comment;
    RatingBar rating;
    Button submitReview;

    DatabaseReference reviewReference, userReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        final String CourtID = getIntent().getStringExtra("CourtID");
        final String Userid = getIntent().getStringExtra("userid");

        comment = findViewById(R.id.reviewDescription);
        rating = findViewById(R.id.reviewrating);
        submitReview = findViewById(R.id.submitreview);

        reviewReference = FirebaseDatabase.getInstance().getReference("Review");
        userReference = FirebaseDatabase.getInstance().getReference("Users");



        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String rate = String.valueOf(rating.getRating());
                 String comm = comment.getEditText().getText().toString();

                if(!TextUtils.isEmpty(rate) && !TextUtils.isEmpty(comm) )
                {
                    Date date = new Date();
                    String date2 = date.toString();

                    String key = reviewReference.push().getKey();

                    ReviewDetailsConstructor rd = new ReviewDetailsConstructor(key, date2, comm, rate,Userid,CourtID);
                    reviewReference.child(CourtID).child(key).setValue(rd);
                    userReference.child(Userid).child("History").child(key).setValue(rd);

                    Toast.makeText(AddReview.this, "Review has been added",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddReview.this,ReviewDetails.class);
                    i.putExtra("CourtID", CourtID);
                    i.putExtra("userid", Userid);
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(AddReview.this, "Fill all the form",Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}
