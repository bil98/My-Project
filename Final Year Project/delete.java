package com.example.cuba2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cuba2.Common.LoginSignUp.Login;
import com.example.cuba2.Common.LoginSignUp.StartUpScreen;
import com.example.cuba2.user.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class delete extends AppCompatActivity {

    Button deleted;
    ImageView back;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        back=findViewById(R.id.delete_button);
        deleted=findViewById(R.id.deleteb);

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        String finaluser = firebaseUser.getUid();

        deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(delete.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Delete this account will result in completely removing your account from the system");
                dialog.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(delete.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(delete.this,Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(delete.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        deleteUser(finaluser);
                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog= dialog.create();
                alertDialog.show();
            }
        });


    }

    public void bbutton(View view) {
        Intent intent = new Intent(getApplicationContext(), UserDashboard.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View, String>(findViewById(R.id.delete_button), "transition_delete");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(delete.this, pairs);
        startActivity(intent, options.toBundle());
    }

    public void deleteUser(String finaluser)
    {
        DatabaseReference  reference= FirebaseDatabase.getInstance().getReference("user").child(finaluser);

        reference.removeValue();
    }


}