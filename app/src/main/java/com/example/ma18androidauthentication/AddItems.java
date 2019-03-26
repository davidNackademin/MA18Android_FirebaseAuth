package com.example.ma18androidauthentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class AddItems extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView itemNameView;
    CollectionReference itemRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        itemNameView = findViewById(R.id.editText);
        //itemRef = db.collection("items");
        FirebaseUser user = auth.getCurrentUser();

        if ( user != null) {
            itemRef = db.collection("users").document(user.getUid()).collection("items");


        }

    }

    public void addItem(View view) {
        Item item = new Item(itemNameView.getText().toString(), false);
        if (itemRef != null) {
            itemRef.add(item).addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful() ) {
                        Log.d("!!!", "write succes");

                    } else {
                        Log.d("!!!", "Write failed");
                    }
                }
            });
        }

    }

}
