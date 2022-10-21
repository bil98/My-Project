package com.example.cuba2.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuba2.HelperClasses.HomeAdapter.SignUpGetSet;
import com.example.cuba2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class SignUp extends AppCompatActivity {


    ImageView backBtn;
    Button next, login;
    TextView titleText;
    TextInputLayout email, username, fullname, password, phone;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String uniqueId = UUID.randomUUID().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        //reference = rootNode.getReference("user");
        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);

        email = findViewById(R.id.signup_email);
        username = findViewById(R.id.signup_username);
        fullname = findViewById(R.id.signup_fullname);
        password = findViewById(R.id.signup_password);
        phone = findViewById(R.id.signup_phone);


        // Write a message to the database
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");*/

    }

    public void ayah1(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, options.toBundle());
    }

    public void ayah2(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(login, "transition_login_btn");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, options.toBundle());
    }


    public void callNextSignupScreen(View view) {
        if (!validateFullname() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        } else {

            final String fname = fullname.getEditText().getText().toString();
            final String uname = username.getEditText().getText().toString();
            final String eemail = email.getEditText().getText().toString();
            final String pass = password.getEditText().getText().toString();


            mAuth.createUserWithEmailAndPassword(eemail, pass)
                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                SignUpGetSet user = new SignUpGetSet(fname, uname, eemail, pass);

                                FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("User Info")
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "Registration Completed!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SignUp.this, "boleh belah", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                            Intent i = new Intent(getApplicationContext(), StartUpScreen.class);
                            startActivity(i);

                        }

                    });
        }

        //Pass all field to the next activity


        // Add Transition

    }

    private boolean validateFullname() {
        String val = fullname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullname.setError("field cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();

        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("field cannot be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("username is too large!");
            return false;

        } else if (!val.matches(checkspaces)) {
            username.setError("no white spaces are allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{0,4}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;

        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
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


