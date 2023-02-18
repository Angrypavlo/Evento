package com.example.evento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TripInfoActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mName, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_info);
        mName =(EditText) findViewById(R.id.name);
        mDescription = (EditText) findViewById(R.id.description);
    }
}