package com.example.asmpk01120;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmpk01120.adpter.DatabaseHelper;

public class chitiettaikhoan extends AppCompatActivity {
    DatabaseHelper db;
    TextView txtHoTen, txtSDT, txtDiachi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiettaikhoan);
        txtHoTen = findViewById(R.id.txtHovaTennguoigui);
        txtSDT = findViewById(R.id.txtSDTnguoigui);
        txtDiachi = findViewById(R.id.txtDiaChinguoigui);
        db = new DatabaseHelper(this, "new.sqlite", null, 1);
        final String ID = this.getIntent().getStringExtra("ID").trim();


        getThongtin();
        findViewById(R.id.btnsuathongitn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DiaLogSua(Integer.parseInt(ID),txtHoTen.getText().toString().trim(),txtSDT.getText().toString().trim(),txtDiachi.getText().toString().trim());
            }
        });
    }

    private void getThongtin() {
        String ID = this.getIntent().getStringExtra("ID").trim();
        Cursor cursor = db.GetData("SELECT * FROM DiaChi WHERE MaNguoiDung = '" + ID + "'");
        while (cursor.moveToNext()) {
            txtHoTen.setText(cursor.getString(2));
            txtSDT.setText(cursor.getString(3));
            txtDiachi.setText(cursor.getString(4));
        }
    }

    public void DiaLogSua(final int id, String HoTen, final String sdt, final String adr) {
        final Dialog dialog = new Dialog(this, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogsuathongtin);

        final EditText edtname = dialog.findViewById(R.id.edthovaten);
        final EditText edtsdt = dialog.findViewById(R.id.edttendangnhap);
        final EditText addr = dialog.findViewById(R.id.edtmatkhaumoi);

        Button btnXacNhan = dialog.findViewById(R.id.btncapnhat);

        edtname.setText(HoTen);
        edtsdt.setText(sdt);
        addr.setText(adr);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtname.getText().toString().trim();
                String sdtMoi = edtsdt.getText().toString().trim();
                String addrMoi = addr.getText().toString().trim();

                db.QueryData("UPDATE DiaChi SET Ten = '" + tenMoi + "', Phone = '" + sdtMoi + "', DiaChi = '" + addrMoi + "' Where MaNguoiDung = '" + id + "'");
                dialog.dismiss();
                getThongtin();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }


}