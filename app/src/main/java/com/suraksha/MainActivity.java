package com.suraksha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textName, textEmail;
    FirebaseAuth mAuth;
    boolean doubleBackToExitPressedOnce = false;
    String email;
    Button btnGetLocation;
    double  soslatitude, longi, lati;
    double  soslongitude;
    String latitude, longitude;




    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String id;


    String number1;
    String number2;
    String number3;


    private InterstitialAd mInterstitialAd;




    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE
    };



    /**************************DOUBLE BACK TO EXIT*********************************************/
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;

        }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



        /************************************LOAD ADD*******************************/

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8234290951221357/7054438912");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        findViewById(R.id.card6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Developer.class);
                    startActivity(intent);
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, Developer.class);
                            startActivity(intent);
                        }
                    }



                    @Override
                    public void onAdClosed() {

                        Intent intent = new Intent(MainActivity.this, Developer.class);
                        startActivity(intent);


                    }
                });



            }
        });

        findViewById(R.id.card5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Contacts.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/nearest+police+station"));
                startActivity(i);
            }
        });

        findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/nearest+medicine+shop"));
                startActivity(i);
            }
        });

        findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/nearest+hospitals"));
                startActivity(i);
            }
        });

        findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Helplines.class);
                startActivity(intent);
            }
        });

        /**************************FIREBASE AUTH*********************************************/
        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.imageView);
        textName = findViewById(R.id.textViewName);
        // textEmail = findViewById(R.id.textViewEmail);



        FirebaseUser user = mAuth.getCurrentUser();

        id = user.getUid();
        DatabaseReference idofuser = database.getReference("Emergency").child(id);
        email = user.getEmail();
        idofuser.child("Email").setValue(email);
        idofuser.child("Name").setValue(user.getDisplayName());
        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(imageView);

        textName.setText(user.getDisplayName());



        /**************************FIREBASE AUTH*********************************************/



        number1=getValue1();
        number2=getValue2();
        number3=getValue3();


        if(number3==null && number2==null && number1==null)
        {
            Toast.makeText(MainActivity.this, "Please Set your EMERGENCY CONTACT NUMBERS first", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Your Emergency Contacs are : "+number1+" ,"+number2+" ,"+number3, Toast.LENGTH_LONG).show();
        }








        /**************************SOS BUTTON ON CLICKED*********************************************/
        btnGetLocation = findViewById(R.id.btnGetLocation);

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**************************GETTING LOCATION*********************************************/

                LocationManager myLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                LocationListener myLocListener = new MyLocationListener();


                myLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocListener);



                LocationManager smslocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                LocationListener smslocationlistener = new smsloc();


                smslocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 180000, 0, smslocationlistener);



                /**************************SHOWING LOCATION TRACKING NOTIFICATION*********************************************/
                notificationDialog();





                /**************************CALLING POLICE*******************************************
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:9674361512"));
                startActivity(intent);


        */
            }
        });
    }



    /**************************METHODS*********************************************/

public class MyLocationListener implements LocationListener{
        public void onLocationChanged(Location loc) {


            /**************************SENDING DATA TO FIREBASE*********************************************/

            longitude = String.format("%f",loc.getLongitude());
            latitude = String.format("%f",loc.getLatitude());

            FirebaseUser user = mAuth.getCurrentUser();


            id = user.getUid();

            DatabaseReference idofuser = database.getReference("Emergency").child(id);

            idofuser.child("Latitude").setValue(latitude);
            idofuser.child("Longitude").setValue(longitude);

        }
        public void onProviderDisabled(String arg0) {

        }
        public void onProviderEnabled(String provider) {

        }
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }

    public class smsloc implements LocationListener{
        public void onLocationChanged(Location loc) {


            longi = loc.getLongitude();
            lati = loc.getLatitude();

            number1=getValue1();
            number2=getValue2();
            number3=getValue3();


            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number1, null, "I need help. My location is : https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude, null, null);

            SmsManager smsManager1 = SmsManager.getDefault();
            smsManager1.sendTextMessage(number2, null, "I need help. My location is : https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude, null, null);

            SmsManager smsManager2 = SmsManager.getDefault();
            smsManager2.sendTextMessage(number3, null, "I need help. My location is :  https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude, null, null);


        }
        public void onProviderDisabled(String arg0) {

        }
        public void onProviderEnabled(String provider) {

        }
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }



    /**************************CHECKING FIREBASE AUTH*********************************************/
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }


    }


    /**************************LOCATION PERMISISON CHEKING*********************************************/

        protected void onResume () {
            super.onResume();


        }

    private void notificationDialog() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "suraksha";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            //  notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon)
                .setTicker("Suraksha")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Live Location Tracking Started")
                .setContentText("Your location is being tracked and SOS Message has been sent to your contact.")
                .setContentInfo("Information");
        notificationManager.notify(1, notificationBuilder.build());
    }


    private String getValue1() {
        SharedPreferences sharedPref1 =  getSharedPreferences("KEY1",Context.MODE_PRIVATE);
        String savedValue = sharedPref1.getString("KEY1", ""); //the 2 argument return default value

        return savedValue;
    }
    private String getValue2() {
        SharedPreferences sharedPref2 =  getSharedPreferences("KEY2",Context.MODE_PRIVATE);
        String savedValue2 = sharedPref2.getString("KEY2", ""); //the 2 argument return default value

        return savedValue2;
    }

    private String getValue3() {
        SharedPreferences sharedPref3 =  getSharedPreferences("KEY3",Context.MODE_PRIVATE);
        String savedValue3 = sharedPref3.getString("KEY3", ""); //the 2 argument return default value

        return savedValue3;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



}
