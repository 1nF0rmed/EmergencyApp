package com.example.a1nf0rmed.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class setupAcount extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private String number;
    private String name;

    private Handler updateUIHandler = null;
    // Message type code.
    private final static int MESSAGE_UPDATE_TEXT_CHILD_THREAD =1;

    private void hideView(final View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        //use this to make it longer:  animation.setDuration(1000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });

        view.startAnimation(anim);
    }

    private void showView(final View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        //use this to make it longer:  animation.setDuration(1000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
        });

        view.startAnimation(anim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if( requestCode==REQUEST_CODE ) {
            if ( resultCode==RESULT_OK ) {
                Uri uri = intent.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                number = cursor.getString(numberColumnIndex);
                Toast.makeText(this, "Number: "+number, Toast.LENGTH_SHORT).show();

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                name = cursor.getString(nameColumnIndex);

                Log.d("TEST", "ZZZ number : " + number +" , name : "+name);
            }
        }
    }

    private void startContactIntent() {
        Uri uri = Uri.parse("content://contacts");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);

        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public Runnable Call_Huge_Code()
    {
        return new Runnable()
        {
            public void run()
            {
                Log.i("Runnable", "Waiting for update");
            }
        };
    }

    /* Update ui text.
    private void updateText()
    {
        String userInputText = changeTextEditor.getText().toString();
        changeTextTextView.setText(userInputText);
    }

    /* Create Handler object in main thread.
    private void createUpdateUiHandler()
    {
        if(updateUIHandler == null)
        {
            updateUIHandler = new Handler()
            {
                @Override
                public void handleMessage(Message msg) {
                    // Means the message is sent from child thread.
                    if(msg.what == MESSAGE_UPDATE_TEXT_CHILD_THREAD)
                    {
                        // Update ui in main thread.
                        updateText();
                    }
                }
            };
        }
    }*/

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_acount);

        // Username layout
        final ConstraintLayout user = (ConstraintLayout) findViewById(R.id.constraintLayout3);

        // Password layout is setup to be not visible until username is given
        final ConstraintLayout passwd = (ConstraintLayout) findViewById(R.id.constraintLayout5);
        passwd.setVisibility(View.GONE);

        // Contact Layout
        final ConstraintLayout contact = (ConstraintLayout) findViewById(R.id.constraintLayout6);
        contact.setVisibility(View.GONE);

        /* Setup the click listeners */
        // Setup Next click
        Button next = (Button) findViewById(R.id.setup_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Call next layout visible animation
                hideView(user);
                showView(passwd);
            }
        });

        // Setup Done click
        Button done = (Button) findViewById(R.id.setup_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideView(passwd);
                showView(contact);
            }
        });

        // Finish button click listener
        Button b1 = (Button) findViewById(R.id.setup_next2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setupAcount.this, QuickView.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
