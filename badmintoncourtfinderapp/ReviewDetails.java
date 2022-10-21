package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Date;

public class ReviewDetails extends AppCompatActivity {

    ImageView courtpic;
    TextView courtname, courtloc;
    DatabaseReference courtReference;
    DatabaseReference userReference;
    DatabaseReference reviewReference;
    RecyclerView commentRecycler;
    RelativeLayout rl, r2;





    FirebaseRecyclerAdapter<ReviewDetailsConstructor,CommentHolder> adapter;
    FirebaseRecyclerOptions<ReviewDetailsConstructor> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        courtpic = findViewById(R.id.courtpic);
        courtname = findViewById(R.id.courtname);
        courtloc = findViewById(R.id.courtlocation);
        commentRecycler = findViewById(R.id.recyclercomments);
        rl = findViewById(R.id.rl3);
        r2 = findViewById(R.id.rl6);
        courtReference = FirebaseDatabase.getInstance().getReference().child("CourtMap");
        userReference = FirebaseDatabase.getInstance().getReference().child("Users");
        reviewReference = FirebaseDatabase.getInstance().getReference().child("Review");
        final String Userid = getIntent().getStringExtra("userid");
        final String CourtID = getIntent().getStringExtra("CourtID");

        commentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentRecycler.setHasFixedSize(true);

        courtReference.child(CourtID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String Cpic = dataSnapshot.child("imageurl").getValue().toString();
                    String Cname = dataSnapshot.child("Location").getValue().toString();
                    String Cloc = dataSnapshot.child("Address").getValue().toString();

                    Picasso.get().load(Cpic).into(courtpic);
                    courtname.setText(Cname);
                    courtloc.setText(Cloc);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Query query = reviewReference.child(CourtID).orderByChild("reviewid");
        options = new FirebaseRecyclerOptions.Builder<ReviewDetailsConstructor>().setQuery(query,ReviewDetailsConstructor.class).build();
        adapter = new FirebaseRecyclerAdapter<ReviewDetailsConstructor, CommentHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CommentHolder commentHolder, int i, @NonNull final ReviewDetailsConstructor reviewDetailsConstructor) {


                commentHolder.holdercomtime.setText(reviewDetailsConstructor.getReviewdate());
                commentHolder.holderrating.setRating(Float.parseFloat(reviewDetailsConstructor.getReviewrate()));
                commentHolder.holdercomment.setText(reviewDetailsConstructor.getReviewtext());

                final String key = reviewDetailsConstructor.getUserid();

                userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
                userReference.addValueEventListener(new ValueEventListener() {
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
        commentRecycler.setAdapter(adapter);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReviewDetails.this,AddReview.class);
                i.putExtra("CourtID", CourtID);
                i.putExtra("userid", Userid);
                startActivity(i);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                courtReference.child(CourtID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            String Cphone = dataSnapshot.child("Contact").getValue().toString();

                            try
                            {
                                if(Build.VERSION.SDK_INT > 22)
                                {
                                    if (ActivityCompat.checkSelfPermission(ReviewDetails.this,
                                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
// TODO: Consider calling
                                        ActivityCompat.requestPermissions(ReviewDetails.this, new String[]{
                                                Manifest.permission.CALL_PHONE}, 101);
                                        return; }
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + Cphone));
                                    startActivity(callIntent);
                                }else {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + Cphone));
                                    startActivity(callIntent);
                                } }
                            catch (Exception ex)
                            {ex.printStackTrace(); }



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

}
