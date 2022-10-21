package com.example.cuba2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cuba2.user.UserDashboard;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Exercise extends AppCompatActivity {

    String api_key = "Enter your API key here";

    @Override
    protected void
    onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_layout);


    }

    public void a (View view)
    {
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=WWVPITg03nU");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.a1),"aa1");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Exercise.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void b (View view)
    {
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=AoQhPzWrrVE");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.b1),"bb1");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Exercise.this,pairs);
        startActivity(intent,options.toBundle());
    }
    public void c (View view)
    {
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=kK8htdBZCig");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.c1),"cc1");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Exercise.this,pairs);
        startActivity(intent,options.toBundle());
    }
    public void d (View view)
    {
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=luHPUTK-hTo");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);


        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.d1),"dd1");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Exercise.this,pairs);
        startActivity(intent,options.toBundle());
    }
    public void e (View view)
    {
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=bRAzyPpmpT4");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.e1),"ee1");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Exercise.this,pairs);
        startActivity(intent,options.toBundle());
    }
}
