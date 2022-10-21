package com.example.cuba2.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuba2.R;

import java.util.Calendar;

public class SignUp2nd extends AppCompatActivity {




    ImageView back_btn_2;
    Button Login2;
    RadioButton radioGroup;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd);

        back_btn_2=findViewById(R.id.signup2_back_button);
        Login2=findViewById(R.id.signup2_login_button);


    }

    public void ayah4 (View view)
    {
        Intent intent = new Intent(getApplicationContext(),SignUp.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(back_btn_2,"transition_back2_btn");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SignUp2nd.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void ayah5 (View view)
    {
        Intent intent = new Intent(getApplicationContext(),Login.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(back_btn_2,"transition_back2_btn");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SignUp2nd.this,pairs);
        startActivity(intent,options.toBundle());
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }


}