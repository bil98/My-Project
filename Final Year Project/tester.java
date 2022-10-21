package com.example.cuba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.rxjava3.annotations.NonNull;

public class tester extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter adapter;
    TextView retrieveTV,bmr;
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser users = firebaseAuth.getCurrentUser();
        final String finaluser = users.getUid();
        reference= FirebaseDatabase.getInstance().getReference("user").child(finaluser);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bmr=findViewById(R.id.ais);

        FirebaseRecyclerOptions<FoodConstructor> options = new FirebaseRecyclerOptions.Builder<FoodConstructor>().setQuery(FirebaseDatabase.getInstance().getReference().child("Noodles"), FoodConstructor.class).build();

        adapter = new FoodAdapter(options);
        recyclerView.setAdapter(adapter);

        getdata();




    }

    private void getdata () {

        // calling add value event listener method
        // for getting the values from database.
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String bmrr = snapshot.child("BMR").getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                bmr.setText(bmrr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(tester.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}