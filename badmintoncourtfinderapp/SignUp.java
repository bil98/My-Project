package com.example.badmintoncourtfinderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SignUp extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    StorageReference StorageReference;
    FirebaseDatabase reference;
    DatabaseReference myref;
    FirebaseAuth mAuth;
    ImageView userimage;
    Button gallery, camera, register;
    TextInputLayout fullname, email, password, username, phonenum;
    String currentPhotoPath;
    String uniqueId = UUID.randomUUID().toString();
    Uri contentUri;
    boolean isImageAdded=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        StorageReference = FirebaseStorage.getInstance().getReference().child("Users picture");
        reference = FirebaseDatabase.getInstance();
        myref = reference.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        userimage = findViewById(R.id.userpic);
        gallery = findViewById(R.id.gallery);
        camera = findViewById(R.id.camera);
        register = findViewById(R.id.register);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        phonenum = findViewById(R.id.phone);



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                askCameraPermissions();
                Toast.makeText(SignUp.this, "Camera is open", Toast.LENGTH_SHORT).show();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,GALLERY_REQUEST_CODE);         }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname = fullname.getEditText().getText().toString();
                final String uname = username.getEditText().getText().toString();
                final String eemail = email.getEditText().getText().toString();
                final String pass = password.getEditText().getText().toString();
                final String phone = phonenum.getEditText().getText().toString();

                if( !TextUtils.isEmpty(fname) && !TextUtils.isEmpty(uname) &&  !TextUtils.isEmpty(eemail) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phone))
                {

                    Toast.makeText(getApplicationContext(),"Fill up the forms",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    //final String authID = task.getResult().getUser().getUid();
                    myref.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                Toast.makeText(getApplicationContext(),"User already exists",Toast.LENGTH_SHORT).show();


                            }
                            else{
                                StorageReference.child(phone+".jpg").putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        StorageReference.child(phone+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String userimageurl = uri.toString();
                                                RegisterConstructor regCons = new RegisterConstructor(uniqueId, fname,uname,eemail,pass,phone,userimageurl);
                                                myref.child(phone).setValue(regCons);
                                                Toast.makeText(getApplicationContext(),"User created succesfully",Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(getApplicationContext(),Home2.class);
                                                i.putExtra("userid",phone);
                                                startActivity(i);
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"User failed to be created",Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, SignUp.CAMERA_PERM_CODE);

        }else
        {
            dispatchTakePictureIntent();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Need Camera permission",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void registerUser(View view){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST_CODE && data!=null)
        {
            contentUri=data.getData();
            isImageAdded=true;
            userimage.setImageURI(contentUri);

        }
        if(requestCode==CAMERA_REQUEST_CODE )
        {
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                userimage.setImageURI(Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}
