package com.example.ma18androidauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    TextView emailView;
    TextView passwordView;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailView = findViewById(R.id.emailText);
        passwordView = findViewById(R.id.passwordText);

        auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Item ost = new Item("ost", false);
        Item paj = new Item("paj", false);

        CollectionReference itemRef = db.collection("items");

//        itemRef.add(ost);
//        itemRef.add(paj);

        //auth.signOut();

        FirebaseUser user = auth.getCurrentUser();
        if ( user != null) {
            Log.d("!!!","loggdIn: " + user.getEmail());
            goToAddItemActivity();

        } else {
            Log.d("!!!", "no user");
        }


    }


    void goToAddItemActivity() {
        Intent intent = new Intent(this, AddItems.class);

        startActivity(intent);
    }

    private void createAccount (String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful()) {
                    Log.d("!!!", "user created");

                } else {
                    Log.d("!!!", "create user failed" , task.getException());

                }

            }
        });


    }


    private void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("!!!", "Sign in success");
                    goToAddItemActivity();
                    // go to add Item activity
                } else {
                    Log.d("!!!", "sign in failed");
                    Toast.makeText(MainActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();


                }
            }
        });



    }




    public void submit(View view) {
        Log.d("!!!", "email: " + emailView.getText());
        Log.d("!!!", "password: " + passwordView.getText());
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        signIn(email, password);

        //createAccount(email, password);
    }



}
