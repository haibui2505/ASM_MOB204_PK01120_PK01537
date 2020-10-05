package com.example.asmpk01120;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiaChi extends AppCompatActivity {

    TextView textView;
    Button btnThemDiaChi;
    EditText sdt, diachi;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);

        //Ánh xạ
        textView = findViewById(R.id.txt_ten_taodon);
        ImageView imgback = findViewById(R.id.imgback);

        btnThemDiaChi = findViewById(R.id.btn_themDiaChi);
        sdt = findViewById(R.id.edit_sdt);
        diachi = findViewById(R.id.edit_diachi);
        db = new DatabaseHelper(DiaChi.this, "new.sqlite", null, 1);

//        gán


//        Truyền dữ liệu bằng intent
        Intent intent = getIntent();
        final String myname = intent.getStringExtra("ten");
        final Integer myId = intent.getIntExtra("id",1);
        textView.setText(myname);

        //Sự kiện onClick
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaChi.this.onBackPressed();
            }
        });
        btnThemDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sdt.getText().toString().trim().equals("") || diachi.getText().toString().trim().equals("")){
                    Toast.makeText(DiaChi.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                }else {
                    long val = db.addDiaChi(myId.toString().trim(), myname, sdt.getText().toString().trim(), diachi.getText().toString());
                    if(val > 0){
                        Toast.makeText(DiaChi.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        DiaChi.this.onBackPressed();
                    }else Toast.makeText(DiaChi.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();

                }
            }
        });




    }
}