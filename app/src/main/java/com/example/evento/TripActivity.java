package com.example.evento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TripActivity extends AppCompatActivity {
    private Button mCreate;
    private EditText mName;
    private EditText mDescription;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        mName =(EditText) findViewById(R.id.name);
        mDescription = (EditText) findViewById(R.id.description);

        firebaseDatabase = FirebaseDatabase.getInstance("https://evento-cdf30-default-rtdb.europe-west1.firebasedatabase.app");

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Trip");

        mCreate = (Button) findViewById(R.id.create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new TripController(mDescription.getText().toString(), mName.getText().toString(),""));
                Intent intent = new Intent(TripActivity.this, MyActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}