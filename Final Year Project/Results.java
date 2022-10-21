package com.example.cuba2;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuba2.user.UserDashboard;
import com.example.cuba2.user.user_profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Results extends AppCompatActivity {


    // instances for widgets in the result activity
    TextView bmrValue,tdeeValue,bmiValue,bmiDetail,tryingToView;
    Button historyButton,logButton;
    double bmi=0.0,uWeight=0.0;
    int bmr=0,tdee=0,inCalories=0;
    String ttmsg="";
    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reference= FirebaseDatabase.getInstance().getReference("user");
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser users = firebaseAuth.getCurrentUser();
        bmrValue = (TextView) findViewById(R.id.bmrValue);
        tdeeValue = (TextView) findViewById(R.id.tdeeValue);
        bmiValue = (TextView) findViewById(R.id.bmiValue);
        bmiDetail = (TextView) findViewById(R.id.bmiDetail);
        logButton = (Button) findViewById(R.id.logButton);
        tryingToView = (TextView) findViewById(R.id.tryingToView);

        String finaluser = users.getUid();

        // get data from intent
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if(extrasBundle != null){

            bmr = (int)extrasBundle.getDouble("bmr");
            tdee = (int)extrasBundle.getDouble("tdee");
            bmi = extrasBundle.getDouble("bmi");
            uWeight = Math.round(extrasBundle.getDouble("weight")*100.0)/100.0;
            ttmsg = extrasBundle.getString("tt");


        }

        String msg;
        if (bmi < 18.5) {
            msg = "Under Weight";
        } else if (bmi < 24.9) {
            msg =  "Normal Weight";
        } else if (bmi < 29.9) {
            msg = "Over Weight";
        } else if (bmi > 40) {
            msg = "Obese";
        } else {
            msg = "an inhuman";
            bmi=0.0;
        }

        String ttmsg_new;

        if (ttmsg.equals("Loose weight")){
            inCalories = tdee - 500;
            if(inCalories<bmr) inCalories=bmr;
            ttmsg_new  = "To loose weight you should limit your intake to " + inCalories + " calories";
        }else if (ttmsg.equals("Gain weight")){
            inCalories = tdee + 500;
            ttmsg_new = "To gain weight you should increase your intake to " + inCalories + " calories";
        }else if (ttmsg.equals("Maintain weight")){
            inCalories=tdee;
            ttmsg_new = "To maintain weight your intake should be " + inCalories + " calories";
        } else
            ttmsg_new = "Something went wrong";

        // populate results
        bmrValue.setText(""+bmr);
        tdeeValue.setText(""+tdee);
        bmiValue.setText(""+bmi);
        bmiDetail.setText(msg);
        tryingToView.setText(ttmsg_new);





    }


    public void ayah8 (View view)
    {
        FirebaseUser users = firebaseAuth.getCurrentUser();
        final String finaluser = users.getUid();

        String bmr1 = bmrValue.getText().toString();
        String tdee1= tdeeValue.getText().toString();
        String bmi1=bmiValue.getText().toString();
        String bmi2=bmiDetail.getText().toString();
        String tty=tryingToView.getText().toString();
        DatabaseReference postsRef = reference.child(finaluser);

        postsRef.child("BMR").setValue(bmr1);
        DatabaseReference newPostRef = postsRef.child("health info");
        DatabaseReference r = newPostRef.push();
        final String key=r.getKey();


        r.child("tdee").setValue(tdee1);
        r.child("bmi").setValue(bmi1);
        r.child("bmidet").setValue(bmi2);




        Toast.makeText(getApplicationContext(),"Result add successfully",Toast.LENGTH_SHORT).show();


        finish();
        Intent i = new Intent(getApplicationContext(), UserDashboard.class);
        startActivity(i);
    }

    public void ayah9 (View view)
    {
        FirebaseUser users = firebaseAuth.getCurrentUser();
        final String finaluser = users.getUid();


        String bmr=bmrValue.getText().toString();
        String tty=tryingToView.getText().toString();



            reference.child(finaluser).child("BMR").setValue(bmr);
            reference.child(finaluser).child("TTY").setValue(tty);




        Toast.makeText(getApplicationContext(),"Update successfully",Toast.LENGTH_SHORT).show();


    }


}
