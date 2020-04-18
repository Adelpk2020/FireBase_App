package com.example.firebasea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FirebaseCrashReporting extends AppCompatActivity {

    private static final String TAG = "FirebaseCrashReporting";

    private Button btnError1, btnError2, btnError3;

    private EditText mText1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_crash_reporting);
        btnError1 = (Button) findViewById(R.id.btnError1);
        btnError2 = (Button) findViewById(R.id.btnError2);
        btnError3 = (Button) findViewById(R.id.btnError3);
        mText1 = (EditText) findViewById(R.id.editText);

        Log.d(TAG, "onCreate: starting.");
        FirebaseCrash.log("Activity Created");

        btnError1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseCrash.log("btnError1 Clicked.");
                String text=null;
                mText1.setText(text.toString());
            }
        });

        btnError2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseCrash.log("btnError2 Clicked.");
                String filepath = "sdcard/made-up/filepath/";
                try {
                    File file =new File(filepath);
                    InputStream inputStream=new FileInputStream(file);
                    inputStream.read();
                } catch (FileNotFoundException e) {
                    FirebaseCrash.report(new Exception(
                            "FileNotFoundException in btnError2. Probably the filepath:" + filepath
                    ));
                } catch (IOException e) {
                    FirebaseCrash.report( new Exception(
                            e.toString()
                    ));
                }
            }
        });

        btnError3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseCrash.log("btnError3 Clicked.");
                ArrayList<String> list =new ArrayList<>();
                list.add("String 1");
                list.add("String 2");
                list.add("String 3");

                for (int x =0 ;x<=list.size();x++){
                    Log.d(TAG, "onClick: : List Item"+list.get(x));
                }
            }
        });
    }
}
