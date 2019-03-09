package com.example.a1nf0rmed.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class PanicActivity extends AppCompatActivity {

    private void TimeToastNotification(String time) {
        Toast.makeText(PanicActivity.this, "Initiating AUTO-PANIC in "+time, Toast.LENGTH_SHORT).show();
    }

    private void sendMessage() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+91 9844440544", null, "Hello, my phone signal has dropped below 30%.", null, null);
        smsManager.sendTextMessage("+91 9844440544", null, "my last know location: https://www.google.com/maps/search/?api=1&query=12.94101898,77.5657647", null, null);
        smsManager.sendTextMessage("+91 8748033844", null, "Hello, my phone signal has dropped below 30%.", null, null);
        smsManager.sendTextMessage("+91 8748033844", null, "my last know location: https://www.google.com/maps/search/?api=1&query=12.94101898,77.5657647", null, null);

        Toast.makeText(PanicActivity.this, "Sent!!!", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic);

        Button b = (Button) findViewById(R.id.panic);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(PanicActivity.this, "Sending!!!", Toast.LENGTH_SHORT).show();

                try {
                    sendMessage();
                } catch (Exception e) {
                    Toast.makeText(PanicActivity.this, "Permission not available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Timer Initiative
        final Handler mhandler = new Handler();

        final int MAX_TIME_LIMIT = 2; // TODO Get from storage/user requriements

        for (int i = 0; i < MAX_TIME_LIMIT; i++) {
            final int t_left = MAX_TIME_LIMIT - i;
            try {
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TimeToastNotification((t_left) + " minutes");
                    }
                }, i*60*1000);
            } catch (Exception e) {
                Toast.makeText(PanicActivity.this, "Unable to schedule multiple timers", Toast.LENGTH_SHORT).show();
            }
        }

        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage();
            }
        }, 2*60*1000);

    }

}
