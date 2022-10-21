package com.example.cuba2.Common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuba2.R;
import com.example.cuba2.user.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {


    Button login_btn,forgetbtn;
    TextInputLayout email,password;
    DatabaseReference reference;
    FirebaseAuth mAuth,fauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);

        login_btn=findViewById(R.id.login);





    }
    public void backbutton(View view)
    {
        Intent intent=new Intent(getApplicationContext(),StartUpScreen.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.login_back_button),"transition_login_back");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void create_button(View view)
    {
        Intent intent=new Intent(getApplicationContext(),SignUp.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(findViewById(R.id.create_button),"transition_create");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void loginUser(View v){

        if (!validateEmail() | !validatePassword()) {
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(email.getEditText().getText().toString().trim(), password.getEditText().getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"User logged in succesfully",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(), UserDashboard.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"User could not be login",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    private Boolean validateEmail(){
        String val = email.getEditText().getText().toString();

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}