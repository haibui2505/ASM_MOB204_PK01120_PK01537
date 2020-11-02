package com.example.asmpk01120;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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
//                    Intent intent  = new Intent(MainActivity.this,TrangChu.class);

                    startActivity(intent);
                }
            }
        };
        bamgio.start();


    }


}