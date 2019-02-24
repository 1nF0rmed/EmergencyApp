package com.example.a1nf0rmed.myapplication;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthProvider;

public class RegisterNumber extends AppCompatActivity {

    // Callback for the firebase
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    private void callSetupDoneIntent() {
        Intent intent = new Intent(RegisterNumber.this, workDone.class);
        startActivity(intent);
        finish();
    }

    private void checkAuth(Button gen_otp, Button verify_otp, final EditText contact_number, EditText otp, final ConstraintLayout number1, final ConstraintLayout number2, final int flag) {
        // Temporarily call and load the numbers
        gen_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO call and generate OTP using firebase
                Toast.makeText(RegisterNumber.this, "Generating OTP for verification", Toast.LENGTH_SHORT).show();
            }
        });

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO authenticate the OTP with firebase
                Toast.makeText(RegisterNumber.this, "Number: "+contact_number.getText().toString()+" Verified!", Toast.LENGTH_SHORT).show();
                // Load next Layout on next button click
                if(flag==0) {
                    hideView(number1);
                    showView(number2);

                    // next button
                    showView((Button) findViewById(R.id.setup_next));
                } else {
                    hideView(number2);
                    callSetupDoneIntent();
                }
            }
        });
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        // TODO Show number1 view and hide number2 view
        // Layouts
        final ConstraintLayout number1 = (ConstraintLayout) findViewById(R.id.number1);
        final ConstraintLayout number2 = (ConstraintLayout) findViewById(R.id.number2);

        final Button setup_next = (Button) findViewById(R.id.setup_next);

        // Hide layout 2 initially
        hideView(number2);
        hideView(setup_next);

        /* Verify the OTP for number 1 */
        // Base number, and otp buttons
        Button gen_otp, verify_otp;
        final EditText contact_number, auth_otp, contact_number1, auth_otp1;

        // Run auth on number 1
        gen_otp = (Button) findViewById(R.id.gen_otp0);
        verify_otp = (Button) findViewById(R.id.otp_verify0);

        contact_number = (EditText) findViewById(R.id.editText_contact_0);
        auth_otp = (EditText) findViewById(R.id.editText_otp_number0);

        checkAuth(gen_otp, verify_otp, contact_number, auth_otp, number1, number2, 0);

        // Run auth on number 2
        gen_otp = (Button) findViewById(R.id.gen_otp1);
        verify_otp = (Button) findViewById(R.id.otp_verify1);

        contact_number1 = (EditText) findViewById(R.id.editText_contact_1);
        auth_otp1 = (EditText) findViewById(R.id.editText_otp_number1);

        checkAuth(gen_otp, verify_otp, contact_number, auth_otp, number1, number2, 1);
    }
}
