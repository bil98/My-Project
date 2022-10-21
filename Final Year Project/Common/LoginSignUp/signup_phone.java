package com.example.cuba2.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.cuba2.R;
import com.example.cuba2.user.verifyOTP;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class signup_phone extends AppCompatActivity {


    ScrollView scrollView;
    TextInputLayout phone;
    CountryCodePicker countryCodePicker;
    Button otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_phone);


        countryCodePicker=findViewById(R.id.countryCodeHolder);
        phone=findViewById(R.id.signup_phone);
        otp=findViewById(R.id.otp_button);

    }

    public void callOTP(View view)
    {
        if(!validatePhoneNumber())
        {
            return;
        }// Validation succeeded and now move to next screen to verify phone number and save data

        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");

        String _getUserEnteredPhoneNumber=phone.getEditText().toString().trim();
        String _phoneNo= "+" +countryCodePicker.getFullNumber()+ _getUserEnteredPhoneNumber;

        Intent intent=new Intent(getApplicationContext(), verifyOTP.class);

        intent.putExtra("fullName",_fullName);
        intent.putExtra("email",_email);
        intent.putExtra("username",_username);
        intent.putExtra("password",_password);
        intent.putExtra("phoneNo",_phoneNo);

        Pair[] pairs=new Pair[1];


        pairs[0]=new Pair<View,String>(otp,"transition_otp_btn");


        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(signup_phone.this,pairs);
        startActivity(intent,options.toBundle());
    }

    private boolean validatePhoneNumber() {
        String val = phone.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phone.setError("Enter valid phone number");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }
}