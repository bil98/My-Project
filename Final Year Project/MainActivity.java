package com.example.cuba2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuba2.Common.LoginSignUp.StartUpScreen;
import com.example.cuba2.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIMER=5000;

    ImageView backgroundImage;
    TextView poweredByLine;

    Animation sideAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Hooks
        backgroundImage = findViewById(R.id.background_image);

        //set animation
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        backgroundImage.setAnimation(sideAnim);

        //delay going to other Intent
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}




