package com.example.cuba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class malay extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FoodConstructor> options = new FirebaseRecyclerOptions.Builder<FoodConstructor>().setQuery(FirebaseDatabase.getInstance().getReference().child("Malay"), FoodConstructor.class).build();

        adapter = new FoodAdapter(options);
        recyclerView.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);



        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<FoodConstructor> options =
                new FirebaseRecyclerOptions.Builder<FoodConstructor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Malay").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), FoodConstructor.class)
                        .build();

        adapter=new FoodAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}