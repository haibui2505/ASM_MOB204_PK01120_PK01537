package com.example.asmpk01120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.asmpk01120.adpter.DatabaseHelper;
import com.example.asmpk01120.service.Broadcast;
import com.example.asmpk01120.service.MyService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TrangChu extends AppCompatActivity {

    DatabaseReference reference, reference1;
    FloatingActionButton fab, fabAddNew, fabContact;
    boolean spr = false;
    Cursor cursor;
    DatabaseHelper db;
    BroadcastReceiver receiver = new Broadcast();
    Integer myId = 0;
    SharedPreferences sharedPreferences;
    private int notificationID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemReselectedListener);

        fab = findViewById(R.id.fab);
        fabAddNew = findViewById(R.id.fabAddNew);
        fabContact = findViewById(R.id.fabContact);

        final Intent intent = this.getIntent();
        myId = intent.getIntExtra("id", 1);
        checkTinhTrang();

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
                        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
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

    private void checkTinhTrang() {
        getHang();
        reference = FirebaseDatabase.getInstance().getReference("tinhTrang");

        Query query = reference.orderByChild("tinhTrangg");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (Integer.valueOf(myId) != 0) {
                    String ma_khachHang = snapshot.child("donHang").getValue(String.class);
                    if (ma_khachHang != null) {
                        int i;
                        for (i = Integer.valueOf(ma_khachHang.charAt(0)); i > 0; i--) {
                            String ten_nguoinhan = snapshot.child(String.valueOf(myId)).child(String.valueOf(i)).child("tenNguoiDung").getValue(String.class);
                            if (ten_nguoinhan != null) {
                                Intent intent = new Intent(TrangChu.this, MyService.class);
                                intent.putExtra("notifi", notificationID);
                                intent.putExtra("mess", "Đơn hàng tới người nhận '" + ten_nguoinhan + "' vừa được cập nhât !");
                                ContextCompat.startForegroundService(TrangChu.this, intent);
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getHang() {
//        cursor = db.GetData("SELECT * FROM GuiHang WHERE MaNguoiDung = '" + myId + "' ORDER BY Id DESC");
//        while (cursor.moveToNext()){
//
//        }
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