package com.example.evento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class MyActivity extends Activity {

    private ArrayList<String> al;
    private HashMap<String, String> descriptionList = new HashMap<String, String>();
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    private TripController tripController;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private FirebaseAuth mAuth;

    public MyActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTrips();

        al = new ArrayList<>();
        //al.add("I");
//        al.add("c++");
//        al.add("css");
//        al.add("javascript");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(MyActivity.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(MyActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                }
        });



        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                CreateNewDialog(dataObject);

                System.out.println(dataObject.toString());
                makeToast(MyActivity.this, "Clicked!");


            }
        });

    }
    public void CreateNewDialog(Object dataObject){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.trip_info,null);

        TextView name = (TextView) popupView.findViewById(R.id.name);
        TextView description = (TextView) popupView.findViewById(R.id.name);
        Button close = (Button) popupView.findViewById(R.id.doSmthButton);

        name.setText(dataObject.toString());
        description.setText(descriptionList.get(name.toString()));
        System.out.println(descriptionList);

        dialogBuilder.setView(popupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    public void getTrips(){
        DatabaseReference trips = FirebaseDatabase.getInstance("https://evento-cdf30-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Trip");
        trips.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Log.i("Firebase",                               // 👈 Log the key and value
                            "Reading Member2 from "+snapshot.getKey() // 👈 to know where the
                                    +", value="+snapshot.getValue()           // 👈 problem is in your
                    );
                    tripController = snapshot.getValue(TripController.class);
                        descriptionList.put(tripController.getName(), tripController.getDescription());
                    al.add(tripController.getName());
                    arrayAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void logoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MyActivity.this, LoginRegistration.class);
        startActivity(intent);
        finish();
        return;
    }

    public void createTrip(View view) {
        Intent intent = new Intent(MyActivity.this, TripActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}