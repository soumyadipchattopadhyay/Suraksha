package com.suraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Helplines extends AppCompatActivity {
    String number1;
    String number2;
    String number3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helplines);

        number1=getValue1();
        number2=getValue2();
        number3=getValue3();

        findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("100");
            }
        });

        findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("1091");
            }
        });

        findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("1098");
            }
        });

        findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("112");
            }
        });

        findViewById(R.id.card5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("1091");
            }
        });

        findViewById(R.id.card6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("102");
            }
        });
        findViewById(R.id.card7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(number1);
            }
        });
        findViewById(R.id.card8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(number2);
            }
        });

        findViewById(R.id.card9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(number3);
            }
        });

        findViewById(R.id.card10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("1073");
            }
        });
    }

    private void call(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private String getValue1() {
        SharedPreferences sharedPref1 =  getSharedPreferences("KEY1", Context.MODE_PRIVATE);
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
}