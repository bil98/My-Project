package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Review extends AppCompatActivity {

    RecyclerView reviewRecycler;
    DatabaseReference reviewDatabase;
    FirebaseRecyclerAdapter<ReviewConstructor,ReviewHolder> adapter;
    FirebaseRecyclerOptions<ReviewConstructor> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewDatabase = FirebaseDatabase.getInstance().getReference().child("CourtMap");
        reviewRecycler = findViewById(R.id.recyclerViews);

        reviewRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviewRecycler.setHasFixedSize(true);

        final String Userid = getIntent().getStringExtra("userid");

        final Query query = reviewDatabase.orderByChild("Location");

        options = new FirebaseRecyclerOptions.Builder<ReviewConstructor>().setQuery(query, ReviewConstructor.class).build();
        adapter = new FirebaseRecyclerAdapter<ReviewConstructor, ReviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ReviewHolder reviewHolder, final int i, @NonNull ReviewConstructor reviewConstructor) {

                reviewHolder.holderreviewcontact.setText(reviewConstructor.getContact());
                reviewHolder.holderreviewlocation.setText(reviewConstructor.getAddress());
                reviewHolder.holderreviewname.setText(reviewConstructor.getLocation());
                Picasso.get().load(reviewConstructor.getImageurl()).into(reviewHolder.holderreviewpic);
                reviewHolder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Review.this, ReviewDetails.class);
                        intent.putExtra("CourtID", getRef(i).getKey());
                        intent.putExtra("userid", Userid);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),getRef(i).getKey(),Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @NonNull
            @Override
            public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout,parent,false);
                return new ReviewHolder(v);
            }
        };
        adapter.startListening();
        reviewRecycler.setAdapter(adapter);
    }
}
