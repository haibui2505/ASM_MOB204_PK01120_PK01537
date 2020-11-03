package com.example.asmpk01120;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.asmpk01120.service.Broadcast;

public class DangNhap extends AppCompatActivity implements View.OnClickListener {
    EditText mTextUsername, mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    CheckBox chk;
    BroadcastReceiver broadcastReceiver = new Broadcast();

    DatabaseHelper db;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(broadcastReceiver, intentFilter);

        //Kiểm tra Validate form
        final String noWhiteSpace = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$";
        final String pass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        //Database
        db = new DatabaseHelper(this, "new.sqlite", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS DanhSach (Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(10), Pass VARCHAR(10), TenNguoiDung VARCHAR(25))");

        //Ánh xạ
        mTextUsername = findViewById(R.id.edittext_username);
        mTextPassword = findViewById(R.id.edittext_password1);
        mButtonLogin = findViewById(R.id.button_login);
        mTextViewRegister = findViewById(R.id.textview_register);
        chk = findViewById(R.id.checkBox);

        //SharedPre
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        mTextUsername.setText(sharedPreferences.getString("name", ""));
        mTextPassword.setText(sharedPreferences.getString("pass", ""));
        chk.setChecked(sharedPreferences.getBoolean("checked", false));

        //Kiểm tra TK xem có chưa
        checkTK();
        //Tạo sự kiện onClick, addTextChange . . .
        mTextViewRegister.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);

        mTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mTextUsername.getText().toString().trim().equals("")) {
                    mTextUsername.setError("Không được để trống!");
                } else if (charSequence.length() <= 5) {
                    mTextUsername.setError("Tối thiểu 6 kí tự!");
                } else if (!charSequence.toString().trim().matches(noWhiteSpace)) {
                    mTextUsername.setError("Không chứa kí tự đặc biệt!");
                } else {
                    mTextUsername.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mTextPassword.getText().toString().trim().equals("")) {
                    mTextPassword.setError("Không được để trống!");
                } else if (charSequence.length() <= 5) {
                    mTextPassword.setError("Tối thiểu 6 kí tự!");
                } else if (!charSequence.toString().trim().matches(pass)) {
                    mTextPassword.setError("Gồm 1 chữ hoa, số và kí tự đặc biệt!");
                } else {
                    mTextPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void restartActivity(Activity act) {
        Intent intent = new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                login();
                break;
            case R.id.textview_register:
                register();
                break;
        }
    }

    //funtion
    private void checkTK() {
        if (MainActivity.logincheck == false) {
            process();
        } else {
            if (sharedPreferences.getString("name", "").equals("") && sharedPreferences.getString("pass", "").equals("")) {
            } else {
                String user = mTextUsername.getText().toString().trim();
                Cursor cursor = db.getTenNguoiDung(user);
                String ten = "";
                int id = 0;
                if (cursor.getCount() == 0) {
                    Toast.makeText(DangNhap.this, "Không có người dùng này!", Toast.LENGTH_SHORT).show();
                } else {
                    while (cursor.moveToNext()) {
                        id = cursor.getInt(0);
                        ten = cursor.getString(3);
                    }
                }
                Intent intent = new Intent(DangNhap.this, TrangChu.class);
                intent.putExtra("hvt", "" + ten);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        }
    }


    private void login() {
        String user = mTextUsername.getText().toString().trim();
        String pwd = mTextPassword.getText().toString().trim();

        Boolean res3 = db.check(user, pwd);

        if (MainActivity.logincheck == true) {
            if (user.equals("") || pwd.equals("")) {
                Toast.makeText(DangNhap.this, "Không được để trống!", Toast.LENGTH_SHORT).show();

            } else {
                if (res3 == true) {
                    if (chk.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", user);
                        editor.putString("pass", pwd);
                        editor.putBoolean("check", true);
                        editor.commit();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("name");
                        editor.remove("pass");
                        editor.remove("check");
                        editor.commit();
                    }
                    Cursor cursor = db.getTenNguoiDung(user);
                    String ten = "";
                    int id = 0;
                    if (cursor.getCount() == 0) {
                        Toast.makeText(DangNhap.this, "Không có người dùng này!", Toast.LENGTH_SHORT).show();
                    } else {
                        while (cursor.moveToNext()) {
                            id = cursor.getInt(0);
                            ten = cursor.getString(3);
                        }
                    }
                    Intent intent = new Intent(DangNhap.this, TrangChu.class);
                    intent.putExtra("hvt", "" + ten);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    Animatoo.animateInAndOut(DangNhap.this);
                } else {
                    Toast.makeText(DangNhap.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            process();
        }
    }

    private void process() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.processbar, null));
        builder.setCancelable(false);
        builder.show();
    }

    private void register() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this);
        builder.setMessage("Bạn muốn đăng kí!");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DangNhap.this, DangKi.class);
                startActivity(intent);
                Animatoo.animateInAndOut(DangNhap.this);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

}