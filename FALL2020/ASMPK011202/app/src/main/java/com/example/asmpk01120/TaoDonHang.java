package com.example.asmpk01120;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaoDonHang extends AppCompatActivity {

    TextView ten, sdt, diaChi, ngay;
    EditText tienThuHo, hoVaTenNguoiNhan, SDTNguoiNhan, diaChiNguoiNhan, edt_moTa_taoDonHang, edt_giaTriHangHoa, edt_soLuong, edt_trongLuong;
    RadioButton radioNhan_taoDonHang, radioGui_taoDonHang;
    CheckBox noiNhan;
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

//        Validate form
        final String vali_hoTen = "^[A-ZẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴa-zàáãạảăắằẳẵặâấầẩẫậèéẹẻẽêềếểễệđìíĩỉịòóõọỏôốồổỗộơớờởỡợùúũụủưứừửữựỳỵỷỹý ]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
        final String vali_sdt = "^[09|03|07|08|05]+([0-9]{7})\b*$";
        final String vali_diachi = "^[([0-9A-ZẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴa-zàáãạảăắằẳẵặâấầẩẫậèéẹẻẽêềếểễệđìíĩỉịòóõọỏôốồổỗộơớờởỡợùúũụủưứừửữựỳỵỷỹý ']+\\\\\\\\s?\\\\\\\\b){2,}]*$";
        final String vali_giatri = "^[0-9]+(\\.[0-9]{1,2})?$";

//        ánh xạ
//        người gửi
        ten = findViewById(R.id.txt_ten_taodon);
        sdt = findViewById(R.id.txt_sdt);
        diaChi = findViewById(R.id.txt_diachi);
        ngay = findViewById(R.id.txt_ngay);
//      người nhận

        edt_moTa_taoDonHang = findViewById(R.id.edt_moTa_taoDonHang);
        edt_giaTriHangHoa = findViewById(R.id.edt_giaTriHangHoa);
        edt_soLuong = findViewById(R.id.edt_soLuong);
        edt_trongLuong = findViewById(R.id.edt_trongLuong);
        hoVaTenNguoiNhan = findViewById(R.id.edt_hoVaTen);
        SDTNguoiNhan = findViewById(R.id.edt_SDTNguoiNhan);
        tienThuHo = findViewById(R.id.edt_money);
        diaChiNguoiNhan = findViewById(R.id.edt_diaChiNguoiNhan);
        btnGuiHang = findViewById(R.id.btn_GuiHang);
        noiNhan = findViewById(R.id.chk_noiNhan);
        radioGui_taoDonHang = findViewById(R.id.radioGui_taoDonHang);
        radioNhan_taoDonHang = findViewById(R.id.radioNhan_taoDonHang);


        relativeLayout = findViewById(R.id.rll_nguoiGui);

//        ngày tháng hiện tại
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

//        nếu date1 sai thì dùng cái này
        calendar = Calendar.getInstance();
//        Tự thêm ngày
        Date date1 = new Date();
        String date = simpleDateFormat.format(date1);
        ngay.setText(date);

//        Tạo database
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
//        vali textchange
        tienThuHo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(tienThuHo.getText().toString().trim().equals("")){
                    tienThuHo.setError("Không được để trống!");
                }else if(Integer.valueOf(tienThuHo.getText().toString().trim()) < 1000){
                    tienThuHo.setError("Không được nhập nhỏ hơn 1000đ");
                }else if(s.length() > 15){
                    tienThuHo.setError("Giá trị không lớn hơn 9.999.999đ");
                }else tienThuHo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_soLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_soLuong.getText().toString().trim().equals("")) {
                    edt_soLuong.setError("Không được để trống!");
                } else if (s.length() > 3) {
                    edt_soLuong.setError("Không vượt quá 999 cái!");
                } else edt_soLuong.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_trongLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_trongLuong.getText().toString().trim().equals("")) {
                    edt_trongLuong.setError("Không được để trống!");
                } else if (s.length() > 2) {
                    edt_trongLuong.setError("Không nặng quá 99kg!");
                } else edt_trongLuong.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_giaTriHangHoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_giaTriHangHoa.getText().toString().trim().equals("")) {
                    edt_giaTriHangHoa.setError("Không được để trống!");
                } else if (!edt_giaTriHangHoa.getText().toString().trim().matches(vali_giatri)) {
                    edt_giaTriHangHoa.setError("Nhập đúng định dạng hộ cái!");
                } else if (s.length() > 7) {
                    edt_giaTriHangHoa.setError("Không lớn hơn 7 sô!");
                } else edt_giaTriHangHoa.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        diaChiNguoiNhan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (diaChiNguoiNhan.getText().toString().trim().equals("")) {
                    diaChiNguoiNhan.setError("Không được để trống!");
                } else if (!diaChiNguoiNhan.getText().toString().trim().matches(vali_diachi)) {
                    diaChiNguoiNhan.setError("Nhập đúng định dạng hộ cái!");
                } else if (s.length() > 100) {
                    diaChiNguoiNhan.setError("Không lớn hơn 100 kí tự!");
                } else diaChiNguoiNhan.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        SDTNguoiNhan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (SDTNguoiNhan.getText().toString().trim().equals("")) {
                    SDTNguoiNhan.setError("Không được để trống!");
                } else if (!SDTNguoiNhan.getText().toString().trim().matches(vali_sdt)) {
                    SDTNguoiNhan.setError("Nhập đúng định dạng hộ cái!");
                } else if (s.length() > 11) {
                    SDTNguoiNhan.setError("Không quá 11 số!");
                } else SDTNguoiNhan.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        hoVaTenNguoiNhan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hoVaTenNguoiNhan.getText().toString().trim().equals("")) {
                    hoVaTenNguoiNhan.setError("Không được để trống!");
                } else if (!hoVaTenNguoiNhan.getText().toString().trim().matches(vali_hoTen)) {
                    hoVaTenNguoiNhan.setError("Nhập đúng định dạng dùm cái!");
                } else if (s.length() > 30) {
                    hoVaTenNguoiNhan.setError("Không quá 30 kí tự!");
                } else hoVaTenNguoiNhan.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                String hovatennguoinhan = hoVaTenNguoiNhan.getText().toString().trim();
                String sdtnguoinhan = SDTNguoiNhan.getText().toString().trim();
                String diachinguoinhan = diaChiNguoiNhan.getText().toString().trim();
                String tienthuho = tienThuHo.getText().toString().trim();
                String giatrihanghoa = edt_giaTriHangHoa.getText().toString().trim();
                String motahanghoa = edt_moTa_taoDonHang.getText().toString().trim();
                String trongluonghanghoa = edt_trongLuong.getText().toString().trim();
                String soluonghanghoa = edt_soLuong.getText().toString().trim();
                String noinhan = "";
                int tiencuoc = 0;
                int tienthuhochinh = 0;
//                check xem người nhận sẽ nhận tại nhà hay bưu cục
                if (noiNhan.isChecked()) {
                    noinhan = "Nhận tại bưu cục";
                } else {
                    noinhan = "Nhận tại địa chỉ";
                }
//                người gửi trả cước hay người nhận trả cước
                if (radioGui_taoDonHang.isChecked()) {
                    tiencuoc = 0;
                    tienthuhochinh = Integer.parseInt(tienthuho);
                } else if (radioNhan_taoDonHang.isChecked()) {
                    tiencuoc = 30000;
                    tienthuhochinh = tiencuoc + Integer.parseInt(tienthuho);
                }

                String date = ngay.getText().toString().trim();

                if (hovatennguoinhan.equals("") || sdtnguoinhan.equals("") || diachinguoinhan.equals("") || tienthuho.equals("")) {
                    Toast.makeText(TaoDonHang.this, "Không để trống!", Toast.LENGTH_SHORT).show();
                } else {
//                    thêm vào db HangGui
                    long val = db.addHangGui(myId.toString().trim(), hovatennguoinhan, sdtnguoinhan, diachinguoinhan, motahanghoa, giatrihanghoa, trongluonghanghoa, soluonghanghoa, noinhan, String.valueOf(tiencuoc), String.valueOf(tienthuhochinh), date, 0);

                    if (val > 0) {
                        Toast.makeText(TaoDonHang.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        TaoDonHang.this.onBackPressed();
                        long val2 = db.addThongBao("'" + ten.getText().toString().trim() + "' vừa thêm 1 đơn hàng đến người nhân '" + hovatennguoinhan + "' thành công!", date);
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
            final Cursor cursor = db.GetData("SELECT * FROM GuiHang WHERE MaNguoiDung = '" + id + "' ORDER BY Id DESC");
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