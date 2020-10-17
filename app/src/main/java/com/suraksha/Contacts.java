package com.suraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.provider.Contacts.SettingsColumns.KEY;

public class Contacts extends AppCompatActivity {


    TextView show3;
    TextView show1;
    TextView show2;

    EditText n1;
    EditText n3;
    EditText n2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference nd1 = database.getReference("Number 1 ");
    DatabaseReference nd2 = database.getReference("Number 2  ");
    DatabaseReference nd3 = database.getReference("Number 3 ");

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;
    public static final String MYPREFERENCES = "MyPreferences_001";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);



        n1 = (EditText) findViewById(R.id.phonenumber1);
        n2 = (EditText) findViewById(R.id.phonenumber2);
        n3 = (EditText) findViewById(R.id.phonenumber3);

         show1 = (TextView)findViewById(R.id.number1);
         show2 = (TextView)findViewById(R.id.number2);
         show3 = (TextView)findViewById(R.id.number3);



        show1.setText(getValue1());
        show2.setText(getValue2());
        show3.setText(getValue3());


        CardView click = (CardView) findViewById(R.id.set);



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!n1.getText().toString().isEmpty()&&(!n2.getText().toString().isEmpty())&&(!n3.getText().toString().isEmpty())) {
                    String first_number = n1.getText().toString();
                    String second_number = n2.getText().toString();
                    String third_number = n3.getText().toString();

                    show1.setText(first_number);
                    show2.setText(second_number);
                    show3.setText(third_number);
                    show1.setText(getValue1());
                    show2.setText(getValue2());
                    show3.setText(getValue3());

                    saveFromEditText1(n1.getText().toString());
                    saveFromEditText2(n2.getText().toString());
                    saveFromEditText3(n3.getText().toString());


                    startActivity(new Intent(getApplicationContext(), MainActivity.class));



                } else {
                    Toast.makeText(Contacts.this, "Please Enter Numbers", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private String getValue1() {
        SharedPreferences sharedPref1 = getSharedPreferences("KEY1",Context.MODE_PRIVATE);
        String savedValue = sharedPref1.getString("KEY1", ""); //the 2 argument return default value

        return savedValue;
    }
    private String getValue2() {
        SharedPreferences sharedPref2 = getSharedPreferences("KEY2",Context.MODE_PRIVATE);
        String savedValue2 = sharedPref2.getString("KEY2", ""); //the 2 argument return default value

        return savedValue2;
    }

    private String getValue3() {
        SharedPreferences sharedPref3 = getSharedPreferences("KEY3",Context.MODE_PRIVATE);
        String savedValue3 = sharedPref3.getString("KEY3", ""); //the 2 argument return default value

        return savedValue3;
    }
    private void saveFromEditText1(String text) {
        SharedPreferences sharedPref1 = getSharedPreferences("KEY1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPref1.edit();
        editor1.putString("KEY1", text);
        editor1.apply();
    }
    private void saveFromEditText2(String text2) {
        SharedPreferences sharedPref2 = getSharedPreferences("KEY2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putString("KEY2", text2);
        editor2.apply();
    }

    private void saveFromEditText3(String text3) {
        SharedPreferences sharedPref3 = getSharedPreferences("KEY3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPref3.edit();
        editor3.putString("KEY3", text3);
        editor3.apply();
    }


}