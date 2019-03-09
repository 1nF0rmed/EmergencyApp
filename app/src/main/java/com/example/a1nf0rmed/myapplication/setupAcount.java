package com.example.a1nf0rmed.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

public class setupAcount extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private String number;
    private String name;

    private Handler updateUIHandler = null;
    // Message type code.
    private final static int MESSAGE_UPDATE_TEXT_CHILD_THREAD =1;

    private FirebaseFirestore db;
    private String gl_user_name, password;

    private void hideView(final View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_animation);
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
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_animation);
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

        db = FirebaseFirestore.getInstance();

        // Username layout
        final ConstraintLayout user = (ConstraintLayout) findViewById(R.id.constraintLayout3);

        // Password layout is setup to be not visible until username is given
        final ConstraintLayout passwd = (ConstraintLayout) findViewById(R.id.constraintLayout5);
        passwd.setVisibility(View.GONE);

        // Contact Layout
        final ConstraintLayout contact = (ConstraintLayout) findViewById(R.id.constraintLayout6);
        //contact.setVisibility(View.GONE);

        /* Setup the click listeners */
        final Button b1 = (Button) findViewById(R.id.run_login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setupAcount.this,  LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        showView(b1);

        // Setup Next click
        Button next = (Button) findViewById(R.id.setup_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Check if username is valid
                final EditText user_name = (EditText) findViewById(R.id.signup_user);
                user_name.setFocusable(false);
                final String user_s = user_name.getText().toString();

                final ProgressBar pbar = (ProgressBar) findViewById(R.id.progressBarUser);
                showView(pbar);

                // Run the user name through the user_info firestore collection to check if there is a valid document
                CollectionReference usersColRef = db.collection("user_info");
                Query userQuery = usersColRef.whereEqualTo("userID", user_s);
                userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            hideView(pbar);
                            try {
                                if (task.getResult().size() > 0) {
                                    Toast.makeText(setupAcount.this, "Username is already taken!", Toast.LENGTH_SHORT).show();
                                } else {
                                    gl_user_name = user_s;
                                    hideView(user);
                                    showView(passwd);

                                    // Hide login button
                                    hideView(b1);
                                }
                            } catch (Exception e) {
                                hideView(user);
                                showView(passwd);

                                // Hide login button
                                hideView(b1);
                            }
                        } else {
                            Toast.makeText(setupAcount.this, "Unable to connect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // TODO Store the given user name

                // TODO Call next layout visible animation
                /*hideView(user);
                showView(passwd);

                // Hide login button
                hideView(b1);*/

                user_name.setFocusable(true);
            }
        });

        // Setup Done click
        Button done = (Button) findViewById(R.id.setup_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText password = (EditText) findViewById(R.id.signup_password);
                if(password.getText().toString().isEmpty()) {
                    Toast.makeText(setupAcount.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                hideView(passwd);
                showView(contact);

                // Start the register phone number intent
                Intent intent = new Intent(setupAcount.this, RegisterNumber.class);
                intent.putExtra("user_name", gl_user_name);
                startActivity(intent);
                finish();
            }
        });


    }

}
