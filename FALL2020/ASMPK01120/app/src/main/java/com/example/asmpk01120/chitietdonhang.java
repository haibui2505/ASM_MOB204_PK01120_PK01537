package com.example.asmpk01120;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.asmpk01120.adpter.DatabaseHelper;

public class chitietdonhang extends AppCompatActivity {

    DatabaseHelper db;
    TextView txtHoTen, txtSDT, txtDiachi, txtSoluong, txtTrongLuong, txtNgaygui, txtGiatri, txtThuho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdonhang);
        txtHoTen = findViewById(R.id.txtHovaTen);
        txtSDT = findViewById(R.id.txtSDT);
        txtDiachi = findViewById(R.id.txtDiaChi);
        txtGiatri = findViewById(R.id.txtGiaTri);
        txtSoluong = findViewById(R.id.txtSoLuong);
        txtTrongLuong = findViewById(R.id.txtTrongLuong);
        txtThuho = findViewById(R.id.txtTien);
        txtNgaygui = findViewById(R.id.txtNgayGui);
        db = new DatabaseHelper(this, "new.sqlite", null, 1);

        String ID = this.getIntent().getStringExtra("ID").trim();
        Cursor cursor = db.GetData("SELECT * FROM GuiHang WHERE Id = '" + ID + "'");
        while (cursor.moveToNext()) {
            txtHoTen.setText(cursor.getString(2));
            txtSDT.setText(cursor.getString(3));
            txtDiachi.setText(cursor.getString(4));
            txtGiatri.setText(cursor.getString(8));
            txtSoluong.setText(cursor.getString(10));
            txtTrongLuong.setText(cursor.getString(9));
            txtThuho.setText(cursor.getString(5));
            txtNgaygui.setText(cursor.getString(6));
        }

    }

}