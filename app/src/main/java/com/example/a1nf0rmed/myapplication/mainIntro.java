package com.example.a1nf0rmed.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragment;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class mainIntro extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide1)
                        .buttonsColor(R.color.slide2)
                        .image(R.drawable.slide1)
                        .title("Emergencies can be scary")
                        .description("We are here to help")
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide2)
                        .buttonsColor(R.color.slide3)
                        .image(R.drawable.slide2)
                        .title("Quick Access to Emergency Services")
                        .description("We provide a quick view of all the available emergency services")
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide3)
                        .buttonsColor(R.color.slide4)
                        .image(R.drawable.cop)
                        .title("Find Nearby Police Stations")
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide4)
                        .buttonsColor(R.color.slide3)
                        .image(R.drawable.fire_station)
                        .title("Find Nearby Fire Stations")
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide3)
                        .buttonsColor(R.color.slide4)
                        .image(R.drawable.hospital)
                        .title("Find Nearby Hospitals")
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide4)
                        .buttonsColor(R.color.slide3)
                        .title("Find Nearby Ambulances")
                        .image(R.drawable.medical)
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide3)
                        .buttonsColor(R.color.slide4)
                        .image(R.drawable.search_deep)
                        .title("Deep Search")
                        .description("Get info on emergency centers within a radius of 20km")
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide4)
                        .buttonsColor(R.color.slide3)
                        .image(R.drawable.panic)
                        .title("Panic Mode")
                        .description("When your phone signal is low, we send an automated message to your family and friends")
                        .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide4)
                        .buttonsColor(R.color.slide5)
                        .possiblePermissions(new String[]{Manifest.permission.READ_SMS})
                        .neededPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                        .title("Setup Permissions")
                        .description("Click Allow on the selected permissions to enable proper functioning of the application")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mainIntro.this, "We provide solutions to make you take care of yourself", Toast.LENGTH_SHORT).show();
                    }
                }, "Work with love"));

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide6)
                        .buttonsColor(R.color.slide5)
                        .image(R.drawable.space_ship_art1)
                        .title("Let's setup your account!")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mainIntro.this, "Starting Account Setup intent", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mainIntro.this, setupAcount.class);
                        startActivity(intent);
                    }
                }, "Get Started"));
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent intent = new Intent(this, setupAcount.class);
        startActivity(intent);
    }

}
