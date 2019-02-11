package com.example.a1nf0rmed.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.*;

import com.google.android.gms.location.places.*;
import com.google.android.gms.tasks.*;

public class MainActivity extends AppCompatActivity {
    protected GeoDataClient mGeoDataClient;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    SharedPreferences prefs = null;

    // Method that adds items to the drawer menu
    private void addDrawerItems() {
        String[] osArray = { "Detail Search", "Account", "Settings", "About", "Help" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Basically load app and check if it's the first run */
        // Load prefs
        try {
            prefs = getSharedPreferences("com.internxtstudio.Emergency", MODE_PRIVATE);

            Intent intent = new Intent(this, mainIntro.class);
            startActivity(intent);
            finish();
        } catch(Exception e) {
            Toast.makeText(MainActivity.this, "Oh! boy Oh! boy", Toast.LENGTH_SHORT).show();
        }

        SmsManager smsManager = SmsManager.getDefault();


        //smsManager.sendTextMessage("9844440544", null, "Hello, I've setup Emergency on my Phone!", null, null);
        /*
        // Check if the preference exists
        if(prefs.contains("firstrun")) {
            // Check for first run
            if(prefs.getBoolean("firstrun", true)) {
                prefs.edit().putBoolean("firstrun", false).commit();

                // Initiate the intro activity
                // TODO Call intro activity intent

                Intent intent = new Intent(this, mainIntro.class);
                startActivity(intent);
                finish();
            } else {
                // Call the normal activity
                // TODO Call the quick view activity intent

                Toast.makeText(MainActivity.this, "Oh! boy", Toast.LENGTH_SHORT).show();
            }
        } else {
            prefs.edit().putBoolean("firstrun", true);
            Intent intent = new Intent(this, mainIntro.class);
            startActivity(intent);
            finish();
        }*/


        //setContentView(R.layout.activity_main);

        /*
        // Construct a GeoDataClient
        mGeoDataClient = Places.getGeoDataClient(this);
        /*
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        locationAPI handler = new locationAPI(getString(R.string.api_key));
        PlaceData pobj = handler.getPlaceSearchResult("hospital");

        TextView textView = (TextView)findViewById(R.id.main_hospital);
        //textView.setText(pobj.name);

        Toast.makeText(MainActivity.this, pobj.name, Toast.LENGTH_SHORT).show();

        //exitMate();
        */

    }

    public void exitMate() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void checkLocation(View v){
        TextView textView = (TextView)findViewById(R.id.main_hospital);
    }


}
