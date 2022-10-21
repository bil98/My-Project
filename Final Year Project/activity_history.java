package com.example.cuba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cuba2.Common.LoginSignUp.SignUp;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.cuba2.Results;
import com.google.firebase.database.Query;

public class activity_history extends AppCompatActivity {
    RecyclerView recyclerView;
    HistoryAdapter adapter;
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        reference= FirebaseDatabase.getInstance().getReference("user");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser users = firebaseAuth.getCurrentUser();
        final String finaluser = users.getUid();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(activity_history.this, finaluser, Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<ResultConstruct> options = new FirebaseRecyclerOptions.Builder<ResultConstruct>().setQuery(FirebaseDatabase.getInstance().getReference("user").child(finaluser).child("health info").orderByChild("bmi"), ResultConstruct.class).build();
        adapter = new HistoryAdapter(options);
        recyclerView.setAdapter(adapter);


    }
    public void removeItem(int position) {
        adapter.notifyItemRemoved(position);
    }

    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}
