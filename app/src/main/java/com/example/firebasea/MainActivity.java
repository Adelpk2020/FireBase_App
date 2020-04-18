package com.example.firebasea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etEmail,etPassword;
    Button btnSignIn , btnSignOut,btnAddItem , btnViewItems,btnUpload,btnAddToDB,btncrachreporting,
    btnGoogleSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etEmail=findViewById(R.id.email_et);
        etPassword=findViewById(R.id.password_et);
        btnSignOut=findViewById(R.id.email_sign_out_button);
        btnSignIn=findViewById(R.id.email_sign_in_button);
        btnAddItem=findViewById(R.id.add_item_button);
        btnViewItems=findViewById(R.id.view_items_button);
        btnUpload=findViewById(R.id.upload_button);
        btnAddToDB=findViewById(R.id.user_information);
        btncrachreporting=findViewById(R.id.crach_rep_btn);
        btnGoogleSignIn=findViewById(R.id.google_signin_btn);

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/NEWS");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                toastMessage("Successfully signed in with: " + user.getEmail());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
                toastMessage("Successfully signed out.");
            }
        }
    };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =etEmail.getText().toString();
                String pass=etPassword.getText().toString();
                if (!email.equals("")&&!pass.equals("")){
                    mAuth.signInWithEmailAndPassword(email,pass);
                }else {
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                toastMessage("Signing Out.....");
            }
        });
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Switching Activities.");
                Intent intent = new Intent(MainActivity.this, AddItemsToDatabase.class);
                startActivity(intent);
            }
        });

        btnViewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ViewDatabase.class);
                startActivity(intent);

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });

        btnAddToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,AddToDatabase.class);
                startActivity(intent);
            }
        });
        btncrachreporting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,FirebaseCrashReporting.class);
                startActivity(intent);
            }
        });

        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,GoogleSignIn.class);
                startActivity(intent);
            }
        });


    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
