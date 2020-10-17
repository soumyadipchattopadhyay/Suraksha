package com.suraksha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro {

    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_PHONE = 1;
    private static final int REQUEST_SMS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        addSlide(AppIntroFragment.newInstance("Suraksha", "Welcome to 'Suraksha : a Women Safety app assists you in case of emergency " , R.drawable.icon,ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("Live Location Tracking", "This app tracks your live location on press of the 'S.O.S.' button on the home screen and sends the data to servers to follow-up your location", R.drawable.loc, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("SOS Messaging", "With just a click on the S.O.S. button, the app sends and emergency button along with your location to the nearest emergency contacts that you have saved earlier. ",R.drawable.mess, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("Nearest Hospitals, Police Station and many more", "Police stations, Hospitals and Medicine shops nearest to your location are also available to help you any time. Emergency Numbers are also provided in this app for getting assistance", R.drawable.conthos, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
    }

    @Override
    public void onDonePressed(Fragment currentFragment){
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment){
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}