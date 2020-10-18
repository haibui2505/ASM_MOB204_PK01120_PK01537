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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class TaoDonHang extends AppCompatActivity {

    TextView ten, sdt, diaChi, ngay;
    EditText tienThuHo, hoVaTenNguoiNhan, diaChiNguoiNhan, edt_moTa_taoDonHang, edt_giaTriHangHoa, edt_soLuong, edt_trongLuong;
    RadioButton radioNhan_taoDonHang, radioGui_taoDonHang;
    CheckBox noiNhan;
    AutoCompleteTextView SDTNguoiNhan;
    Button btnGuiHang;
    DatabaseHelper db;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    RelativeLayout relativeLayout;
    Spinner spinner, spinner2;
    private ArrayAdapter arrayAdapter;
    ArrayList<String> list;
    String tenThanhPho, tenHuyen;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_don_hang);
        ImageView imgback = findViewById(R.id.imgback);

//        Validate form
        final String vali_hoTen = "^[A-ZẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴa-zàáãạảăắằẳẵặâấầẩẫậèéẹẻẽêềếểễệđìíĩỉịòóõọỏôốồổỗộơớờởỡợùúũụủưứừửữựỳỵỷỹý ]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
        final String vali_sdt = "^[09|03|07|08|05]+([0-9]{8})\b*$";
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
        //       autocomplete
        SDTNguoiNhan.setAdapter(arrayAdapter);
        SDTNguoiNhan.setThreshold(1);
        spnner_city();
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
                if (tienThuHo.getText().toString().trim().equals("")) {
                    tienThuHo.setError("Không được để trống!");
                } else if (Integer.valueOf(tienThuHo.getText().toString().trim()) < 1000) {
                    tienThuHo.setError("Không được nhập nhỏ hơn 1000đ");
                } else if (s.length() > 15) {
                    tienThuHo.setError("Giá trị không lớn hơn 9.999.999đ");
                } else tienThuHo.setError(null);
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
                submitForm();

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaoDonHang.this, "Đang được bổ sung!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void submitForm() {

        String tencv = SDTNguoiNhan.getText().toString().trim();
        db.QueryData("INSERT INTO AUTOPHONE VALUES(null, '" + tencv + "')");
        Cursor dataDanhSach = db.GetData("SELECT * FROM AUTOPHONE");
        while (dataDanhSach.moveToNext()) {
            String ten = dataDanhSach.getString(1);
            arrayAdapter.clear();
            arrayAdapter.add(ten);
        }

    }

    private void spnner_city() {
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);

        String[] tinhThanh = {"--Chọn tỉnh thành--", "Hà Nội", "TP HCM", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên", "Bình Dương"};
        String[] chonTinhThanh = {"--Chọn huyện, quận--"};
        String[] haNoi = {"Quận Ba Đình", "Quận Bắc Từ Liê", "Quận Cầu Giấy", "Quận Đống Đa", "Quận Hà Đông", "Quận Hai Bà Trưng", "Quận Hoàn Kiếm", "Quận Hoàng Mai", "Quận Long Biên", "Quận Nam Từ Liêm", "Quận Tây Hồ", "Quận Thanh Xuân", "Thị xã Sơn Tây", "Huyện Ba Vì", "Huyện Chương Mỹ", "Huyện Đan Phượng", "Huyện Đông Anh", "Huyện Gia Lâm", "Huyện Hoài Đức", "Huyện Mê Linh", "Huyện Mỹ Đức", "Huyện Phú Xuyên", "Huyện Phúc Thọ", "Huyện Quốc Oai", "Huyện Sóc Sơn", "uyện Thạch Thất", "Huyện Thanh Oai", "Huyện Thanh Trì", "Huyện Thường Tín", "Huyện Ứng Hoà"};
        String[] hcm = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6", "Quận 7", "Quận 8", "Quận 9", "Quận 10", "Quận 11", "Quận 12", "Quận Thủ Đức", "Quận Bình Thạnh", "Quận Gò Vấp", "Quận Phú Nhuận", "Quận Tân Phú", "Quận Bình Tân", "Quận Tân Bình", "Huyện Nhà Bè", "Huyện Bình Chánh", "Huyện Hóc Môn", "Huyện Củ Chi", "Huyện Cần Giờ"};
        String[] canTho = {"Quận Bình Thủy", "Quận Cái Răng", "Quận Ninh Kiều", "Quận Ô Môn", "Quận Thốt Nốt", "Huyện Cờ Đỏ", "Huyện Phong Điền", "Huyện Thới Lai", "Huyện Vĩnh Thạnh"};
        String[] daNang = {"Quận Hải Châu", "Quận Cẩm Lệ", "Quận Thanh Khê", "Quận Liên Chiểu", "Quận Ngũ Hành Sơn", "Quận Sơn Trà", "Huyện Hòa Vang", "Huyện Hoàng Sa"};
        String[] haiPhong = {"Quận Đồ Sơn", "Quận Dương Kinh", "Quận Hải An", "Quận Hồng Bàng", "Quận Kiến An", "Quận Lê Chân", "Quận Ngô Quyền", "Huyện An Dương", "Huyện An Lão", "Huyện Bạch Long Vĩ", "Huyện Cát Hải", "Huyện Kiến Thuỵ", "Huyện Thủy Nguyên", "Huyện Tiên Lãng", "Huyện Vĩnh Bảo"};
        String[] baRia = {"Thành phố Bà Rịa", "Thành phố Vũng Tàu", "Thị xã Phú Mỹ", "Huyện Châu Đức", "Huyện Côn Đảo", "Huyện Đất Đỏ", "Huyện Long Điền", "Huyện Xuyên Mộc"};
        String[] bacGiang = {"Thành phố Bắc Giang", "Huyện Hiệp Hoà", "Huyện Lạng Giang", "Huyện Lục Nam", "Huyện Lục Ngạn", "Huyện Sơn Động", "Huyện Tân Yên", "Huyện Việt Yên", "Huyện Yên Dũng", "Huyện Yên Thế"};
        String[] bacKan = {"Thành phố Bắc Kạn", "Huyện Ba Bể", "Huyện Bạch Thông", "Huyện Chợ Đồn", "Huyện Chợ Mới", "Huyện Na Rì", "Huyện Ngân Sơn", "Huyện Pác Nặm"};
        String[] bacLieu = {"Thành phố Bạc Liêu", "Thị xã Giá Rai", "Huyện Đông Hải", "Huyện Hòa Bình", "Huyện Hồng Dân", "Huyện Phước Long", "Huyện Vĩnh Lợi"};
        String[] bacNing = {"Thành phố Bắc Ninh", "Thị xã Từ Sơn", "Huyện Gia Bình", "Huyện Lương Tài", "Huyện Quế Võ", "Huyện Thuận Thành", "Huyện Tiên Du", "Huyện Yên Phong"};
        String[] benTre = {"Thành phố Bến Tre", "Huyện Ba Tri", "Huyện Bình Đại", "Huyện Châu Thành", "Huyện Chợ Lách", "Huyện Giồng Trôm", "Huyện Mỏ Cày Bắc", "Huyện Mỏ Cày Nam", "Huyện Thạnh Phú"};
        String[] binhDinh = {"Thành phố Quy Nhơn", "Thị xã An Nhơn", "Thị xã Hoài Nhơn", "Huyện An Lão", "Huyện Hoài Ân", "Huyện Phù Cát", "Huyện Phù Mỹ", "Huyện Tây Sơn", "Huyện Tuy Phước", "Huyện Vân Canh", "Huyện Vĩnh Thạnh"};
        String[] binhDuong = {"Thành phố Dĩ An", "Thành phố Thủ Dầu Một", "Thành phố Thuận An", "Thị xã Bến Cát", "Thị xã Tân Uyên", "Huyện Bắc Tân Uyên", "Huyện Bàu Bàng", "Huyện Dầu Tiếng", "Huyện Phú Giáo"};
        String[] binhPhuoc = {"Thành phố Đồng Xoài", "Thị xã Bình Long", "Thị xã Phước Long", "Huyện Bù Đăng", "Huyện Bù Đốp", "Huyện Bù Gia Mập", "Huyện Chơn Thành", "Huyện Đồng Phú", "Huyện Hớn Quản", "Huyện Lộc Ninh", "Huyện Phú Riềng"};
        String[] binhThuan = {"Thành phố Phan Thiết", "Thị xã La Gi", "Huyện Bắc Bình", "Huyện Đức Linh", "Huyện Hàm Tân", "Huyện Hàm Thuận Bắc", "Huyện Hàm Thuận Nam", "Huyện Phú Quý", "Huyện Tánh Linh", "Huyện Tuy Phong"};
        String[] caMau = {"Thành phố Cà Mau", "Huyện Cái Nước", "Huyện Đầm Dơi", "Huyện Năm Căn", "Huyện Ngọc Hiển", "Huyện Phú Tân", "Huyện Thới Bình", "Huyện Trần Văn Thời", "Huyện U Minh"};
        String[] caoBang = {"Thành phố Cao Bằng", "Huyện Bảo Lạc", "Huyện Bảo Lâm", "Huyện Hạ Lang", "Huyện Hà Quảng", "Huyện Hòa An", "Huyện Nguyên Bình", "Huyện Quảng Hòa", "Huyện Thạch An", "Huyện Trùng Khánh"};
        String[] daklak = {"Thành phố Buôn Ma Thuột", "Thị xã Buôn Hồ", "Huyện Buôn Đôn", "Huyện Cư Kuin", "Huyện Cư M’gar", "Huyện Ea H’leo", "Huyện Ea Kar", "Huyện Ea Súp", "Huyện Krông Ana", "Huyện Krông Bông", "Huyện Krông Búk", "Huyện Krông Năng", "Huyện Krông Pắk", "Huyện Lắk", "Huyện M’Đrắk"};
        String[] dakNong = {"Thành phố Gia Nghĩa", "Huyện Cư Jút", "Huyện Đắk Glong", "Huyện Đắk Mil", "Huyện Đắk R’lấp", "Huyện Đắk Song", "Huyện Krông Nô", "Huyện Tuy Đức"};
        String[] dienBien = {"Thành phố Điện Biên Phủ", "Thị xã Mường Lay", "Huyện Điện Biên", "Huyện Điện Biên Đông", "Huyện Mường Ảng", "Huyện Mường Chà", "Huyện Mường Nhé", "Huyện Nậm Pồ", "Huyện Tủa Chùa", "Huyện Tuần Giáo"};
        String[] dongNai = {"Thành phố Biên Hòa", "Thành phố Long Khánh", "Huyện Cẩm Mỹ", "Huyện Định Quán", "Huyện Long Thành", "Huyện Nhơn Trạch", "Huyện Tân Phú", "Huyện Thống Nhất", "Huyện Trảng Bom", "Huyện Vĩnh Cửu", "Huyện Xuân Lộc"};
        String[] dongThap = {"Thành phố Cao Lãnh", "Thành phố Sa Đéc", "Thị xã Hồng Ngự", "Huyện Cao Lãnh", "Huyện Châu Thành", "Huyện Hồng Ngự", "Huyện Lai Vung", "Huyện Lấp Vò", "Huyện Tam Nông", "Huyện Tân Hồng", "Huyện Thanh Bình", "Huyện Tháp Mười"};
        String[] giaLai = {"Thành phố Pleiku", "Thị xã An Khê", "Thị xã Ayun Pa", "Huyện Chư Păh", "Huyện Chư Prông", "Huyện Chư Pưh", "Huyện Chư Sê", "Huyện Đắk Đoa", "Huyện Đak Pơ", "Huyện Đức Cơ", "Huyện Ia Grai", "Huyện Ia Pa", "Huyện K’Bang", "Huyện Kông Chro", "Huyện Krông Pa", "Huyện Mang Yang", "Huyện Phú Thiện"};
        String[] haGiang = {"Thành phố Hà Giang", "Huyện Bắc Mê", "Huyện Bắc Quang", "Huyện Đồng Văn", "Huyện Hoàng Su Phì", "Huyện Mèo Vạc", "Huyện Quản Bạ", "Huyện Quang Bình", "Huyện Vị Xuyên", "Huyện Xín Mần", "Huyện Yên Minh"};
        String[] haNam = {"Thành phố Phủ Lý", "Thị xã Duy Tiên", "Huyện Bình Lục", "Huyện Kim Bảng", "Huyện Lý Nhân", "Huyện Thanh Liêm"};
        String[] haTinh = {"Thành phố Hà Tĩnh", "Thị xã Hồng Lĩnh", "Thị xã Kỳ Anh", "Huyện Can Lộc", "Huyện Cẩm Xuyên", "Huyện Đức Thọ", "Huyện Hương Khê", "Huyện Hương Sơn", "Huyện Kỳ Anh", "Huyện Lộc Hà", "Huyện Nghi Xuân", "Huyện Thạch Hà", "Huyện Vũ Quang"};
        String[] haiDuong = {"Thành phố Hải Dương", "Thành phố Chí Linh", "Thị xã Kinh Môn", "Huyện Bình Giang", "Huyện Cẩm Giàng", "Huyện Gia Lộc", "Huyện Kim Thành", "Huyện Nam Sách", "Huyện Ninh Giang", "Huyện Thanh Hà", "Huyện Thanh Miện", "Huyện Tứ Kỳ"};
        String[] hauGiang = {"Thành phố Vị Thanh", "Thành phố Ngã Bảy", "Thị xã Long Mỹ", "Huyện Châu Thành", "Huyện Châu Thành A", "Huyện Long Mỹ", "Huyện Phụng Hiệp", "Huyện Vị Thủy"};
        String[] hoaBinh = {"Thành phố Hòa Bình", "Huyện Cao Phong", "Huyện Đà Bắc", "Huyện Kim Bôi", "Huyện Lạc Sơn", "Huyện Lạc Thủy", "Huyện Lương Sơn", "Huyện Mai Châu", "Huyện Tân Lạc", "Huyện Yên Thủy"};
        String[] hungYen = {"Thành phố Hưng Yên", "Thị xã Mỹ Hào", "Huyện Ân Thi", "Huyện Khoái Châu", "Huyện Kim Động", "Huyện Phù Cừ", "Huyện Tiên Lữ", "Huyện Văn Giang", "Huyện Văn Lâm", "Huyện Yên Mỹ"};
        String[] khanhHoa = {"Thành phố Cam Ranh", "Thành phố Nha Trang", "Thị xã Ninh Hòa", "Huyện Cam Lâm", "Huyện đảo Trường Sa", "Huyện Diên Khánh", "Huyện Khánh Sơn", "Huyện Khánh Vĩnh", "Huyện Vạn Ninh"};
        String[] kienGiang = {"Thành phố Rạch Giá", "Thành phố Hà Tiên", "Huyện An Biên", "Huyện An Minh", "Huyện Châu Thành", "Huyện Giang Thành", "Huyện Giồng Riềng", "Huyện Gò Quao", "Huyện Hòn Đất", "Huyện Kiên Hải", "Huyện Kiên Lương", "Huyện Phú Quốc", "Huyện Tân Hiệp", "Huyện U Minh Thượng", "Huyện Vĩnh Thuận"};
        String[] konTum = {"Thành phố Kon Tum", "Huyện Đắk Glei", "Huyện Đắk Hà", "Huyện Đắk Tô", "Huyện Ia H’Drai", "Huyện Kon Plông", "Huyện Kon Rẫy", "Huyện Ngọc Hồi", "Huyện Sa Thầy", "Huyện Tu Mơ Rông"};
        String[] laiChau = {"Thành phố Lai Châu", "Huyện Mường Tè", "Huyện Nậm Nhùn", "Huyện Phong Thổ", "Huyện Sìn Hồ", "Huyện Tam Đường", "Huyện Tân Uyên", "Huyện Than Uyên"};
        String[] lamDong = {"Thành phố Bảo Lộc", "Thành phố Đà Lạt", "Huyện Bảo Lâm", "Huyện Cát Tiên", "Huyện Di Linh", "Huyện Đạ Huoai", "Huyện Đạ Tẻh", "Huyện Đam Rông", "Huyện Đơn Dương", "Huyện Đức Trọng", "Huyện Lạc Dương", "Huyện Lâm Hà"};
        String[] langSon = {"Thành phố Lạng Sơn", "Huyện Bắc Sơn", "Huyện Bình Gia", "Huyện Cao Lộc", "Huyện Chi Lăng", "Huyện Đình Lập", "Huyện Hữu Lũng", "Huyện Lộc Bình", "Huyện Tràng Định", "Huyện Văn Lãng", "Huyện Văn Quan"};
        String[] laoCai = {"Thành phố Lào Cai", "Thị xã Sa Pa", "Huyện Bắc Hà", "Huyện Bảo Thắng", "Huyện Bảo Yên", "Huyện Bát Xát", "Huyện Mường Khương", "Huyện Si Ma Cai", "Huyện Văn Bàn"};
        String[] longAn = {"Thành phố Tân An", "Thị xã Kiến Tường", "Huyện Bến Lức", "Huyện Cần Đước", "Huyện Cần Giuộc", "Huyện Châu Thành", "Huyện Đức Hòa", "Huyện Đức Huệ", "Huyện Mộc Hóa", "Huyện Tân Hưng", "Huyện Tân Thạnh", "Huyện Tân Trụ", "Huyện Thạnh Hóa", "Huyện Thủ Thừa", "Huyện Vĩnh Hưng"};
        String[] namDinh = {"Thành phố Nam Định", "Huyện Giao Thủy", "Huyện Hải Hậu", "Huyện Mỹ Lộc", "Huyện Nam Trực", "Huyện Nghĩa Hưng", "Huyện Trực Ninh", "Huyện Vụ Bản", "Huyện Xuân Trường", "Huyện Ý Yên"};
        String[] ngheAn = {"Thành phố Vinh", "Thị xã Cửa Lò", "Thị xã Hoàng Mai", "Thị xã Thái Hòa", "Huyện Anh Sơn", "Huyện Con Cuông", "Huyện Diễn Châu", "Huyện Đô Lương", "Huyện Hưng Nguyên", "Huyện Kỳ Sơn", "Huyện Nam Đàn", "Huyện Nghi Lộc", "Huyện Nghĩa Đàn", "Huyện Quế Phong", "Huyện Quỳ Châu", "Huyện Quỳ Hợp", "Huyện Quỳnh Lưu", "Huyện Tân Kỳ", "Huyện Thanh Chương", "2Huyện Tương Dương", "2Huyện Yên Thành"};
        String[] ninhBinh = {"Thành phố Ninh Bình", "Thành phố Tam Điệp", "Huyện Gia Viễn", "Huyện Hoa Lư", "Huyện Kim Sơn", "Huyện Nho Quan", "Huyện Yên Khánh", "Huyện Yên Mô"};
        String[] ninhThuan = {"Thành phố Phan Rang - Tháp Chàm", "Huyện Bác Ái", "Huyện Ninh Hải", "Huyện Ninh Phước", "Huyện Ninh Sơn", "Huyện Thuận Bắc", "Huyện Thuận Nam"};
        String[] phuTho = {"Thành phố Việt Trì", "Thị xã Phú Thọ", "Huyện Cẩm Khê", "Huyện Đoan Hùng", "Huyện Hạ Hòa", "Huyện Lâm Thao", "Huyện Phù Ninh", "Huyện Tam Nông", "Huyện Tân Sơn", "Huyện Thanh Ba", "Huyện Thanh Sơn", "Huyện Thanh Thủy", "Huyện Yên Lập"};
        String[] quangBinh = {"Thành phố Đồng Hới", "Thị xã Ba Đồn", "Huyện Bố Trạch", "Huyện Lệ Thủy", "Huyện Minh Hóa", "Huyện Quảng Ninh", "Huyện Quảng Trạch", "Huyện Tuyên Hóa"};
        String[] quangNam = {"Thành phố Hội An", "Thành phố Tam Kỳ", "Thị xã Điện Bàn", "Huyện Bắc Trà My", "Huyện Đại Lộc", "Huyện Đông Giang", "Huyện Duy Xuyên", "Huyện Hiệp Đức", "Huyện Nam Giang", "Huyện Nam Trà My", "Huyện Nông Sơn", "Huyện Núi Thành", "Huyện Phú Ninh", "Huyện Phước Sơn", "Huyện Quế Sơn", "Huyện Tây Giang", "Huyện Thăng Bình", "Huyện Tiên Phước"};
        String[] quangNgai = {"Thành phố Quảng Ngãi", "Thị xã Đức Phổ", "Huyện Ba Tơ", "Huyện Bình Sơn", "Huyện Lý Sơn", "Huyện Minh Long", "Huyện Mộ Đức", "Huyện Nghĩa Hành", "Huyện Sơn Hà", "Huyện Sơn Tây", "Huyện Sơn Tịnh", "Huyện Trà Bồng", "Huyện Tư Nghĩa"};
        String[] quangNinh = {"Thành phố Cẩm Phả", "Thành phố Hạ Long", "Thành phố Móng Cái", "Thành phố Uông Bí", "Thị xã Đông Triều", "Thị xã Quảng Yên", "Huyện Ba Chẽ", "Huyện Bình Liêu", "Huyện Cô Tô", "Huyện Đầm Hà", "Huyện Hải Hà", "Huyện Tiên Yên", "Huyện Vân Đồn"};
        String[] quangTri = {"Thành phố Đông Hà", "Thị xã Quảng Trị", "Huyện Cam Lộ", "Huyện Cồn Cỏ", "Huyện Đa Krông", "Huyện Gio Linh", "Huyện Hải Lăng", "Huyện Hướng Hóa", "Huyện Triệu Phong", "Huyện Vĩnh Linh"};
        String[] socTrang = {"Thành phố Sóc Trăng", "Thị xã Ngã Năm", "Thị xã Vĩnh Châu", "Huyện Châu Thành", "Huyện Cù Lao Dung", "Huyện Kế Sách", "Huyện Long Phú", "Huyện Mỹ Tú", "Huyện Mỹ Xuyên", "Huyện Thạnh Trị", "Huyện Trần Đề"};
        String[] sonLa = {"Thành phố Sơn La", "Huyện Bắc Yên", "Huyện Mai Sơn", "Huyện Mộc Châu", "Huyện Mường La", "Huyện Phù Yên", "Huyện Quỳnh Nhai", "Huyện Sông Mã", "Huyện Sốp Cộp", "Huyện Thuận Châu", "Huyện Vân Hồ", "Huyện Yên Châu"};
        String[] tayNinh = {"Thành phố Tây Ninh", "Thị xã Hòa Thành", "Thị xã Trảng Bàng", "Huyện Bến Cầu", "Huyện Châu Thành", "Huyện Dương Minh Châu", "Huyện Gò Dầu", "Huyện Tân Biên", "Huyện Tân Châu"};
        String[] thaiBinh = {"Thành phố Thái Bình", "Huyện Đông Hưng", "Huyện Hưng Hà", "Huyện Kiến Xương", "Huyện Quỳnh Phụ", "Huyện Thái Thụy", "Huyện Tiền Hải", "Huyện Vũ Thư"};
        String[] thaiNguyen = {"Thành phố Sông Công", "Thành phố Thái Nguyên", "Thị xã Phổ Yên", "Huyện Đại Từ", "Huyện Định Hóa", "Huyện Đồng Hỷ", "Huyện Phú Bình", "Huyện Phú Lương", "Huyện Võ Nhai"};
        String[] thanhHoa = {"Thành phố Sầm Sơn", "Thành phố Thanh Hóa", "Thị xã Bỉm Sơn", "Thị xã Nghi Sơn", "Huyện Bá Thước", "Huyện Cẩm Thủy", "Huyện Đông Sơn", "Huyện Hà Trung", "Huyện Hậu Lộc", "Huyện Hoằng Hóa", "Huyện Lang Chánh", "Huyện Mường Lát", "Huyện Nga Sơn", "Huyện Ngọc Lặc", "Huyện Như Thanh", "Huyện Như Xuân", "Huyện Nông Cống", "Huyện Quan Hóa", "Huyện Quan Sơn", "2Huyện Quảng Xương", "2Huyện Thạch Thành", "2Huyện Thiệu Hóa", "2Huyện Thọ Xuân", "2Huyện Thường Xuân", "2Huyện Triệu Sơn", "2Huyện Vĩnh Lộc", "2Huyện Yên Định"};
        String[] hue = {"Thành phố Huế", "Thị xã Hương Thủy", "Thị xã Hương Trà", "Huyện A Lưới", "Huyện Nam Đông", "Huyện Phong Điền", "Huyện Phú Lộc", "Huyện Phú Vang", "Huyện Quảng Điền"};
        String[] tienGiang = {"Thành phố Mỹ Tho", "Thị xã Cai Lậy", "Thị xã Gò Công", "Huyện Cái Bè", "Huyện Cai Lậy", "Huyện Châu Thành", "Huyện Chợ Gạo", "Huyện Gò Công Đông", "Huyện Gò Công Tây", "Huyện Tân Phú Đông", "Huyện Tân Phước"};
        String[] traVinh = {"Thành phố Trà Vinh", "Thị xã Duyên Hải", "Huyện Càng Long", "Huyện Cầu Kè", "Huyện Cầu Ngang", "Huyện Châu Thành", "Huyện Duyên Hải", "Huyện Tiểu Cần", "Huyện Trà Cú"};
        String[] tuyenQuanh = {"Thành phố Tuyên Quang", "Huyện Chiêm Hóa", "Huyện Hàm Yên", "Huyện Lâm Bình", "Huyện Na Hang", "Huyện Sơn Dương", "Huyện Yên Sơn"};
        String[] vinhLong = {"Thành phố Vĩnh Long", "Thị xã Bình Minh", "Huyện Bình Tân", "Huyện Long Hồ", "Huyện Mang Thít", "Huyện Tam Bình", "Huyện Trà Ôn", "Huyện Vũng Liêm"};
        String[] vinhPhuc = {"Thành phố Phúc Yên", "Thành phố Vĩnh Yên", "Huyện Bình Xuyên", "Huyện Lập Thạch", "Huyện Sông Lô", "Huyện Tam Đảo", "Huyện Tam Dương", "Huyện Vĩnh Tường", "Huyện Yên Lạc"};
        String[] yenBai = {"Thành phố Yên Bái", "Thị xã Nghĩa Lộ", "Huyện Lục Yên", "Huyện Mù Cang Chải", "Huyện Trạm Tấu", "Huyện Trấn Yên", "Huyện Văn Chấn", "Huyện Văn Yên", "Huyện Yên Bình"};
        String[] phuYen = {"Thành phố Tuy Hòa", "Thị xã Đông Hòa", "Thị xã Sông Cầu", "Huyện Đồng Xuân", "Huyện Phú Hòa", "Huyện Sơn Hòa", "Huyện Sông Hinh", "Huyện Tây Hòa", "Huyện Tuy An"};

        ArrayList<String> list = new ArrayList<>(Arrays.asList(tinhThanh));
        ArrayList<String> listChon = new ArrayList<>(Arrays.asList(chonTinhThanh));
        ArrayList<String> listhanoi = new ArrayList<>(Arrays.asList(haNoi));
        ArrayList<String> listHcm = new ArrayList<>(Arrays.asList(hcm));
        ArrayList<String> listcanTho = new ArrayList<>(Arrays.asList(canTho));
        ArrayList<String> listbinhPhuoc = new ArrayList<>(Arrays.asList(binhPhuoc));
        ArrayList<String> listDaNang = new ArrayList<>(Arrays.asList(daNang));
        ArrayList<String> listhaiPhong = new ArrayList<>(Arrays.asList(haiPhong));
        ArrayList<String> listbaRia = new ArrayList<>(Arrays.asList(baRia));
        ArrayList<String> listanGiang = new ArrayList<>(Arrays.asList(tienGiang));
        ArrayList<String> listbacGiang = new ArrayList<>(Arrays.asList(bacGiang));
        ArrayList<String> listbacCan = new ArrayList<>(Arrays.asList(bacKan));
        ArrayList<String> listbacLieu = new ArrayList<>(Arrays.asList(bacLieu));
        ArrayList<String> listbacNinh = new ArrayList<>(Arrays.asList(bacNing));
        ArrayList<String> listbenTre = new ArrayList<>(Arrays.asList(benTre));
        ArrayList<String> listbinhDinh = new ArrayList<>(Arrays.asList(binhDinh));
        ArrayList<String> listhoabinh = new ArrayList<>(Arrays.asList(hoaBinh));
        ArrayList<String> listkiengiang = new ArrayList<>(Arrays.asList(kienGiang));
        ArrayList<String> listhungyen = new ArrayList<>(Arrays.asList(hungYen));
        ArrayList<String> listkhanhhoa = new ArrayList<>(Arrays.asList(khanhHoa));
        ArrayList<String> listbinhDuong = new ArrayList<>(Arrays.asList(binhDuong));
        ArrayList<String> listbinhThuan = new ArrayList<>(Arrays.asList(binhThuan));
        ArrayList<String> listcaMau = new ArrayList<>(Arrays.asList(caMau));
        ArrayList<String> listcaoBang = new ArrayList<>(Arrays.asList(caoBang));
        ArrayList<String> listdaklak = new ArrayList<>(Arrays.asList(daklak));
        ArrayList<String> listdakNong = new ArrayList<>(Arrays.asList(dakNong));
        ArrayList<String> listdienBien = new ArrayList<>(Arrays.asList(dienBien));
        ArrayList<String> listdongNai = new ArrayList<>(Arrays.asList(dongNai));
        ArrayList<String> listdongThap = new ArrayList<>(Arrays.asList(dongThap));
        ArrayList<String> listgiaLai = new ArrayList<>(Arrays.asList(giaLai));
        ArrayList<String> listhaGiang = new ArrayList<>(Arrays.asList(haGiang));
        ArrayList<String> listhaNam = new ArrayList<>(Arrays.asList(haNam));
        ArrayList<String> listhaTinh = new ArrayList<>(Arrays.asList(haTinh));
        ArrayList<String> listhaiduong = new ArrayList<>(Arrays.asList(haiDuong));
        ArrayList<String> listhauGiang = new ArrayList<>(Arrays.asList(hauGiang));
        ArrayList<String> listkontum = new ArrayList<>(Arrays.asList(konTum));
        ArrayList<String> listlaichau = new ArrayList<>(Arrays.asList(laiChau));
        ArrayList<String> listlamdong = new ArrayList<>(Arrays.asList(lamDong));
        ArrayList<String> listlangson = new ArrayList<>(Arrays.asList(langSon));
        ArrayList<String> listlaocai = new ArrayList<>(Arrays.asList(laoCai));
        ArrayList<String> listlongan = new ArrayList<>(Arrays.asList(longAn));
        ArrayList<String> listnamdinh = new ArrayList<>(Arrays.asList(namDinh));
        ArrayList<String> listnghean = new ArrayList<>(Arrays.asList(ngheAn));
        ArrayList<String> listninhbinh = new ArrayList<>(Arrays.asList(ninhBinh));
        ArrayList<String> listninhthuan = new ArrayList<>(Arrays.asList(ninhThuan));
        ArrayList<String> listphutho = new ArrayList<>(Arrays.asList(phuTho));
        ArrayList<String> listquangbinh = new ArrayList<>(Arrays.asList(quangBinh));
        ArrayList<String> listquangnam = new ArrayList<>(Arrays.asList(quangNam));
        ArrayList<String> listquangngai = new ArrayList<>(Arrays.asList(quangNgai));
        ArrayList<String> listquangninh = new ArrayList<>(Arrays.asList(quangNinh));
        ArrayList<String> listquangtri = new ArrayList<>(Arrays.asList(quangTri));
        ArrayList<String> listsoctrang = new ArrayList<>(Arrays.asList(socTrang));
        ArrayList<String> listsonla = new ArrayList<>(Arrays.asList(sonLa));
        ArrayList<String> listtayninh = new ArrayList<>(Arrays.asList(tayNinh));
        ArrayList<String> listthaibinh = new ArrayList<>(Arrays.asList(thaiBinh));
        ArrayList<String> listthainguyen = new ArrayList<>(Arrays.asList(thaiNguyen));
        ArrayList<String> listthanhhoa = new ArrayList<>(Arrays.asList(thanhHoa));
        ArrayList<String> listhue = new ArrayList<>(Arrays.asList(hue));
        ArrayList<String> listtiengian = new ArrayList<>(Arrays.asList(tienGiang));
        ArrayList<String> listtravinh = new ArrayList<>(Arrays.asList(traVinh));
        ArrayList<String> listtuyenquang = new ArrayList<>(Arrays.asList(tuyenQuanh));
        ArrayList<String> listvinhlong = new ArrayList<>(Arrays.asList(vinhLong));
        ArrayList<String> listvinhphuc = new ArrayList<>(Arrays.asList(vinhPhuc));
        ArrayList<String> listyenbai = new ArrayList<>(Arrays.asList(yenBai));
        ArrayList<String> listphuyen = new ArrayList<>(Arrays.asList(phuYen));


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        final ArrayAdapter<String> adapterChon = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listChon);
        final ArrayAdapter<String> hanoi = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhanoi);
        final ArrayAdapter<String> hcmm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listHcm);
        final ArrayAdapter<String> cantho = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listcanTho);
        final ArrayAdapter<String> danang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDaNang);
        final ArrayAdapter<String> haiphong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhaiPhong);
        final ArrayAdapter<String> baria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbaRia);
        final ArrayAdapter<String> bacgiang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbacGiang);
        final ArrayAdapter<String> backan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbacCan);
        final ArrayAdapter<String> baclieu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbacLieu);
        final ArrayAdapter<String> bacninh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbacNinh);
        final ArrayAdapter<String> bentre = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbenTre);
        final ArrayAdapter<String> binhdinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbinhDinh);
        final ArrayAdapter<String> binhduong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbinhDuong);
        final ArrayAdapter<String> binhphuoc = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbinhPhuoc);
        final ArrayAdapter<String> binhthuan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbinhThuan);
        final ArrayAdapter<String> camau = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listcaMau);
        final ArrayAdapter<String> caobang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listcaoBang);
        final ArrayAdapter<String> dakklak = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listdaklak);
        final ArrayAdapter<String> daknong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listdakNong);
        final ArrayAdapter<String> dienbien = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listdienBien);
        final ArrayAdapter<String> dongnai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listdongNai);
        final ArrayAdapter<String> dongthap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listdongThap);
        final ArrayAdapter<String> gialai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listgiaLai);
        final ArrayAdapter<String> hagiang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhaGiang);
        final ArrayAdapter<String> hanam = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhaNam);
        final ArrayAdapter<String> hatinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhaTinh);
        final ArrayAdapter<String> haiduong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhaiduong);
        final ArrayAdapter<String> haugiang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhauGiang);
        final ArrayAdapter<String> hoabinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhoabinh);
        final ArrayAdapter<String> hungyen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhungyen);
        final ArrayAdapter<String> khanhhoa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listkhanhhoa);
        final ArrayAdapter<String> kiengiang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listkiengiang);
        final ArrayAdapter<String> kontum = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listkontum);
        final ArrayAdapter<String> laichau = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listlaichau);
        final ArrayAdapter<String> lamdong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listlamdong);
        final ArrayAdapter<String> langson = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listlangson);
        final ArrayAdapter<String> laocai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listlaocai);
        final ArrayAdapter<String> longan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listlongan);
        final ArrayAdapter<String> namdinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listnamdinh);
        final ArrayAdapter<String> nghean = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listnghean);
        final ArrayAdapter<String> ninhbinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listninhbinh);
        final ArrayAdapter<String> ninhthuan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listninhthuan);
        final ArrayAdapter<String> phtho = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listphutho);
        final ArrayAdapter<String> quangbinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listquangbinh);
        final ArrayAdapter<String> quangnam = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listquangnam);
        final ArrayAdapter<String> quangngai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listquangngai);
        final ArrayAdapter<String> quangninh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listquangninh);
        final ArrayAdapter<String> quangtri = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listquangtri);
        final ArrayAdapter<String> soctrang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listsoctrang);
        final ArrayAdapter<String> sonla = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listsonla);
        final ArrayAdapter<String> tayninh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listtayninh);
        final ArrayAdapter<String> thaibinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listthaibinh);
        final ArrayAdapter<String> thainguyen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listthainguyen);
        final ArrayAdapter<String> thanhhoa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listthanhhoa);
        final ArrayAdapter<String> thuathienhue = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listhue);
        final ArrayAdapter<String> tiengiang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listtiengian);
        final ArrayAdapter<String> travinh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listtravinh);
        final ArrayAdapter<String> tuyenquang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listtuyenquang);
        final ArrayAdapter<String> vinhlong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listvinhlong);
        final ArrayAdapter<String> vinhphuc = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listvinhphuc);
        final ArrayAdapter<String> yenbai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listyenbai);
        final ArrayAdapter<String> phuyen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listphuyen);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinner2.setAdapter(adapterChon);
                } else if (position == 1) {
                    spinner2.setAdapter(hanoi);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (position == 2) {
                    spinner2.setAdapter(hcmm);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 3) {
                    spinner2.setAdapter(cantho);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 4) {
                    spinner2.setAdapter(danang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 5) {
                    spinner2.setAdapter(haiphong);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 6) {
                    spinner2.setAdapter(baria);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 7) {
                    spinner2.setAdapter(bacgiang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 8) {
                    spinner2.setAdapter(backan);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 9) {
                    spinner2.setAdapter(baclieu);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 10) {
                    spinner2.setAdapter(bacninh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 11) {
                    spinner2.setAdapter(bentre);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 12) {
                    spinner2.setAdapter(binhdinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 13) {
                    spinner2.setAdapter(binhphuoc);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 14) {
                    spinner2.setAdapter(binhthuan);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 15) {
                    spinner2.setAdapter(camau);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 16) {
                    spinner2.setAdapter(caobang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 17) {
                    spinner2.setAdapter(dakklak);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 18) {
                    spinner2.setAdapter(daknong);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 19) {
                    spinner2.setAdapter(dienbien);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 20) {
                    spinner2.setAdapter(dongnai);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 21) {
                    spinner2.setAdapter(dongthap);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 22) {
                    spinner2.setAdapter(gialai);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 23) {
                    spinner2.setAdapter(hagiang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 24) {
                    spinner2.setAdapter(hanam);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 25) {
                    spinner2.setAdapter(hatinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 26) {
                    spinner2.setAdapter(haiduong);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 27) {
                    spinner2.setAdapter(haugiang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 28) {
                    spinner2.setAdapter(hoabinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 29) {
                    spinner2.setAdapter(hungyen);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 30) {
                    spinner2.setAdapter(khanhhoa);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 31) {
                    spinner2.setAdapter(kiengiang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 32) {
                    spinner2.setAdapter(kontum);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 33) {
                    spinner2.setAdapter(laichau);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 34) {
                    spinner2.setAdapter(lamdong);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 35) {
                    spinner2.setAdapter(langson);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 36) {
                    spinner2.setAdapter(laocai);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 37) {
                    spinner2.setAdapter(longan);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 38) {
                    spinner2.setAdapter(namdinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 39) {
                    spinner2.setAdapter(nghean);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 40) {
                    spinner2.setAdapter(ninhbinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 41) {
                    spinner2.setAdapter(ninhthuan);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 42) {
                    spinner2.setAdapter(phtho);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 43) {
                    spinner2.setAdapter(quangbinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 44) {
                    spinner2.setAdapter(quangnam);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 45) {
                    spinner2.setAdapter(quangngai);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 46) {
                    spinner2.setAdapter(quangninh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 47) {
                    spinner2.setAdapter(quangtri);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 48) {
                    spinner2.setAdapter(soctrang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 49) {
                    spinner2.setAdapter(sonla);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 50) {
                    spinner2.setAdapter(tayninh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 51) {
                    spinner2.setAdapter(thaibinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 52) {
                    spinner2.setAdapter(thainguyen);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 53) {
                    spinner2.setAdapter(thanhhoa);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 54) {
                    spinner2.setAdapter(thuathienhue);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 55) {
                    spinner2.setAdapter(tiengiang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 56) {
                    spinner2.setAdapter(travinh);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 57) {
                    spinner2.setAdapter(tuyenquang);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 58) {
                    spinner2.setAdapter(vinhlong);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 59) {
                    spinner2.setAdapter(vinhphuc);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 60) {
                    spinner2.setAdapter(yenbai);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 61) {
                    spinner2.setAdapter(phuyen);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 62) {
                    spinner2.setAdapter(binhduong);
                    tenThanhPho = (String) parent.getItemAtPosition(position);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tenHuyen = (String) parent.getItemAtPosition(position);
                            Log.d("hai", "onItemSelected: " + tenThanhPho + " " + tenHuyen);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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