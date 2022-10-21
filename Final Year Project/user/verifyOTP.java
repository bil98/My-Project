package com.example.cuba2.user;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.cuba2.Common.LoginSignUp.SignUp;
import com.example.cuba2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyOTP extends AppCompatActivity {

    ImageView backu;
    PinView pinFromUser;
    FirebaseAuth mAuth;
    String codeBySystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        backu=findViewById(R.id.backu);
        pinFromUser=findViewById(R.id.pin_view);

        String _phoneNo=getIntent().getStringExtra("phoneNo");

        sendVerificationCodeToUser(_phoneNo);

    }

    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,60,TimeUnit.SECONDS, (Activity) TaskExecutors.MAIN_THREAD,mCallbacks);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();

            if (code!=null)
            {
                pinFromUser.setText(code);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(verifyOTP.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(verifyOTP.this, "verification completed", Toast.LENGTH_SHORT).show();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(verifyOTP.this, "verification not complete  try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void callVerifyOTP (View view)

    {
        String code=pinFromUser.getText().toString();
        if(!code.isEmpty())
        {
            verifyCode(code);
        }
    }

    public void goToHomeFromOTP(View view)
    {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);

        Pair[] pairs=new Pair[1];

        pairs[0]=new Pair<View,String>(backu,"dex_backu");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(verifyOTP.this,pairs);
        startActivity(intent,options.toBundle());
    }
}