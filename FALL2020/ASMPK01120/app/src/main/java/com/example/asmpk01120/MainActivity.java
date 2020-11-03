package com.example.asmpk01120;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asmpk01120.adpter.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    public static boolean refreshDisplay = true;
    public static String sPref = null;
    public  static boolean logincheck = true;
    public static DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Thread bamgio = new Thread() {
            public void run() {
                try {
                    sleep(0);

                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(MainActivity.this, DangNhap.class);

                    startActivity(intent);
                }
            }
        };
        bamgio.start();


    }


}