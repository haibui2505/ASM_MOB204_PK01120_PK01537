package com.example.asmpk01120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TrangChu extends AppCompatActivity {

    FloatingActionButton fab, fabAddNew, fabContact;
    boolean spr = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemReselectedListener);

        fab = findViewById(R.id.fab);
        fabAddNew = findViewById(R.id.fabAddNew);
        fabContact = findViewById(R.id.fabContact);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spr == false) {
                    Show();
                    spr = true;
                } else {
                    Hide();
                    spr = false;
                }
            }
        });
        fabContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogXoa = new AlertDialog.Builder(TrangChu.this);
                dialogXoa.setMessage("Bạn muốn xác nhận đăng xuất?");
                dialogXoa.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("name");
                        editor.remove("pass");
                        editor.remove("check");
                        editor.commit();
                        TrangChu.this.onBackPressed();
                    }
                });
                dialogXoa.setNegativeButton("Hủy!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogXoa.show();

            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemReselectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new TrangChu_Frag();
                    break;
                case R.id.nav_don:
                    fragment = new DonHang_Frag();
                    break;
                case R.id.nav_money:
                    fragment = new TienHang_Frag();
                    break;
                case R.id.nav_note:
                    fragment = new ThongBao_Frag();
                    break;
                case R.id.nav_danh:
                    fragment = new DanhMuc_Frag();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    fragment).commit();
            return true;
        }
    };

    private void Show() {
        fabAddNew.show();
        fabContact.show();
    }

    private void Hide() {
        fabContact.hide();
        fabAddNew.hide();
    }
}