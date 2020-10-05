package com.example.asmpk01120;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaoDonHang extends AppCompatActivity {

    TextView ten, sdt, diaChi, ngay;
    EditText thuHo, nameNhan, sdtNhan, diaChiNhan;
    Button btnGuiHang;
    DatabaseHelper db;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_don_hang);

        ImageView imgback = findViewById(R.id.imgback);


//        ánh xạ
        ten = findViewById(R.id.txt_ten_taodon);
        sdt = findViewById(R.id.txt_sdt);
        diaChi = findViewById(R.id.txt_diachi);
        ngay = findViewById(R.id.txt_ngay);

        nameNhan = findViewById(R.id.edt_name);
        sdtNhan = findViewById(R.id.edt_phone);
        thuHo = findViewById(R.id.edt_money);
        diaChiNhan = findViewById(R.id.edt_addr);
        btnGuiHang = findViewById(R.id.btn_GuiHang);

        relativeLayout = findViewById(R.id.rll_nguoiGui);

//        ngày tháng hiện tại
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        calendar = Calendar.getInstance();

        Date date1 = new Date();
        String date = simpleDateFormat.format(date1);
        ngay.setText(date);

//        gán
        db = new DatabaseHelper(TaoDonHang.this, "new.sqlite", null, 1);
//        Truyền dữ liệu bằng intent
        Intent intent = getIntent();
        final String myname = intent.getStringExtra("ten");
        final Integer myId = intent.getIntExtra("id", 1);

        Cursor cursor = db.getMaNguoiDung(myId.toString().trim());
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                ten.setText(cursor.getString(2));
                sdt.setText("*   " + cursor.getString(3));
                diaChi.setText(cursor.getString(4));
            }
        }

        ImageView img = findViewById(R.id.imgabc);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaoDonHang.this, "Chức năng sắp được bổ sung!", Toast.LENGTH_SHORT).show();

            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaoDonHang.this);
                builder.setTitle("Chú ý");
                builder.setMessage("Dữ liệu sẽ mất nếu bạn đồng ý thoát khỏi màn hình?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TaoDonHang.this.onBackPressed();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

        btnGuiHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameNhan.getText().toString().trim();
                String phone = sdtNhan.getText().toString().trim();
                String adr = diaChiNhan.getText().toString().trim();
                String thuHo1 = thuHo.getText().toString().trim();
                String date = ngay.getText().toString().trim();

                if (name.equals("") || phone.equals("") || adr.equals("") || thuHo1.equals("")) {
                    Toast.makeText(TaoDonHang.this, "Không để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    long val = db.addHangGui(myId.toString().trim(), name, phone, adr, thuHo1, date,0);
                    Log.d("hai",name + "");
                    if (val > 0) {
                        Toast.makeText(TaoDonHang.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        TaoDonHang.this.onBackPressed();
                        long val2 = db.addThongBao("'" + ten.getText().toString().trim() + "' vừa thêm 1 đơn hàng đến người nhân '" + name + "' thành công!", date);
                        if (val2 > 0) {
                            Log.d("thongbao", "Thêm thông báo thành công");
                        } else Log.d("thongbao", "Thêm thông báo thất bại");
                        getGuiHang();
                    } else
                        Toast.makeText(TaoDonHang.this, "Thêm hàng gửi thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaoDonHang.this, "Đang được bổ sung!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getGuiHang() {
        SharedPreferences sharedPreferencesID = getSharedPreferences("IDnguoidung", MODE_PRIVATE);
        String id = sharedPreferencesID.getString("id", "").trim();
        SharedPreferences.Editor editor = sharedPreferencesID.edit();
        editor.putString("id", id);
        editor.commit();

        Cursor cursorCheck = db.GetData("SELECT * FROM GuiHang");
        if (cursorCheck.getCount() == 0) {
        } else {
            final Cursor cursor = db.GetData("SELECT * FROM GuiHang WHERE MaHangHoa = '" + id + "' ORDER BY Id DESC");
            while (cursor.moveToNext()) {

                final int idnv = cursor.getInt(0);
                Integer trangThai = cursor.getInt(7);

                if (trangThai == 0) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            db.QueryData("UPDATE GuiHang SET TrangThai = 1 Where Id = '" + idnv + "'");
                        }
                    }, 10000);
                }
            }
        }
    }

}