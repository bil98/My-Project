package com.example.cuba2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.cuba2.user.UserDashboard;
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


public class Food extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter adapter;
    TextView retrieveTV,bmr,tty;
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooding);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser users = firebaseAuth.getCurrentUser();
        final String finaluser = users.getUid();

        reference= FirebaseDatabase.getInstance().getReference("user").child(finaluser);


        bmr=findViewById(R.id.ais);
        tty=findViewById(R.id.aiss);




        getdata();


    }
    public void a1 (View view)
    {
        Intent intent=new Intent(getApplicationContext(), malay.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.a11),"a");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Food.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void a2 (View view)
    {
        Intent intent=new Intent(getApplicationContext(), chinese.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.a12),"aa");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Food.this,pairs);
        startActivity(intent,options.toBundle());
    }
    public void a3 (View view)
    {
        Intent intent=new Intent(getApplicationContext(), indian.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.a13),"aaa");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Food.this,pairs);
        startActivity(intent,options.toBundle());
    }


    private void getdata () {

        // calling add value event listener method
        // for getting the values from database.
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@io.reactivex.rxjava3.annotations.NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String bmrr = snapshot.child("BMR").getValue(String.class);
                String ttyy = snapshot.child("TTY").getValue(String.class);
                // after getting the value we are setting
                // our value to our text view in below line.
                bmr.setText(bmrr);
                tty.setText(ttyy);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(Food.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

