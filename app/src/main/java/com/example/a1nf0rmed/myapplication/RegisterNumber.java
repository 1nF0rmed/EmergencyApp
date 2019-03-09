package com.example.a1nf0rmed.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.DrawableMarginSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterNumber extends AppCompatActivity {

    // Callback for the firebase
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    FirebaseAuth auth;
    private String verificationCode;
    private int res=0;

    private void callSetupDoneIntent() {
        Intent intent = new Intent(RegisterNumber.this, workDone.class);
        startActivity(intent);
        finish();
    }

    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(RegisterNumber.this,"verification completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegisterNumber.this,"verification failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(RegisterNumber.this,"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void SigninWithPhone(PhoneAuthCredential credential, final ConstraintLayout number1, final ConstraintLayout number2,final EditText contact_number,final int flag) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(RegisterNumber.this,"CORRECT OTP!!!!",Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterNumber.this, "Number: " + contact_number.getText().toString() + " Verified!", Toast.LENGTH_SHORT).show();
                            if(flag==0){
                                hideView(number1);
                                showView(number2);

                                // next button
                                showView((Button) findViewById(R.id.setup_next));
                            } else {
                                hideView(number2);
                                callSetupDoneIntent();
                            }
                            //finish();
                        } else {
                            Toast.makeText(RegisterNumber.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void genOTP(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                RegisterNumber.this,
                mCallback
        );
    }

    private void checkAuth(final Button gen_otp, Button verify_otp, final EditText contact_number, final EditText otp_field, final ConstraintLayout number1, final ConstraintLayout number2, final int flag) {
        // Temporarily call and load the numbers
        gen_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO call and generate OTP using firebase
                String phoneNumber = contact_number.getText().toString();
                genOTP(phoneNumber);
                Toast.makeText(RegisterNumber.this, "Generating OTP for verification", Toast.LENGTH_SHORT).show();

            }
        });

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO authenticate the OTP with firebase

                // Load next Layout on next button click
                String otp = otp_field.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                SigninWithPhone(credential, number1, number2, contact_number, flag);
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

        StartFirebaseLogin();

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
