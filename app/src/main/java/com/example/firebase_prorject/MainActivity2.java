package com.example.firebase_prorject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText first,middel,last,born;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        first=(EditText) findViewById(R.id.firstName);
        middel=(EditText)findViewById(R.id.lastName);
        last=(EditText) findViewById(R.id.middelName);
        born=(EditText)findViewById(R.id.born);



    }


    public void creat(View view) {
        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", first.getText().toString());
        user.put("middle", middel.getText().toString());
        user.put("last", last.getText().toString());
        user.put("born", Integer.parseInt(born.getText().toString()));

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> Toast.makeText(MainActivity2.this, "Added Successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}