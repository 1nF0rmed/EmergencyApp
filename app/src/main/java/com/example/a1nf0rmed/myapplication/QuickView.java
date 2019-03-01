package com.example.a1nf0rmed.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuickView extends AppCompatActivity {

    private void intentMap(String url) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void intentCall(String no) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + no));
            startActivity(intent);
        } catch(SecurityException e) {
            Toast.makeText(QuickView.this, "Unable to make the call", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_view);

        Button b1 = (Button) findViewById(R.id.map_fire);
        Button b[] = {(Button) findViewById(R.id.map_fire),  (Button) findViewById(R.id.map_hospital),  (Button) findViewById(R.id.map_ambulance),  (Button) findViewById(R.id.map_police)};
        Button c[] = {(Button) findViewById(R.id.call_fire), (Button) findViewById(R.id.call_hospital), (Button) findViewById(R.id.call_ambulance), (Button) findViewById(R.id.call_police_station)};
        final String m_url[] = {"https://maps.google.com/?cid=5583190909727657170", "https://maps.google.com/?cid=10267385577627469262", "https://maps.google.com/?cid=18301459457400113281", "https://maps.google.com/?cid=9745019549641329363"};
        final String c_no[] = {"080 2297 1543", "1860 500 1066", "093431 80000", "080 2294 2573"};

        for(int i=0;i<4;i++) {
            final String url = m_url[i];
            final String no = c_no[i];
            b[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intentMap(url);
                }
            });

            c[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intentCall(no);
                }
            });
        }
    }



}
