package com.example.asmpk01120;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class DangNhap extends AppCompatActivity {

    EditText mTextUsername, mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    CheckBox chk;

    DatabaseHelper db;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        //Kiểm tra Validate form
        final String noWhiteSpace = "[a-zA-Z._\\d]+$";

        //Database
        db = new DatabaseHelper(this,"new.sqlite",null,1);
        db.QueryData("CREATE TABLE IF NOT EXISTS DanhSach (Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(200), Pass VARCHAR(200), TenNguoiDung VARCHAR(200))");

        //Ánh xạ
        mTextUsername     = findViewById(R.id.edittext_username);
        mTextPassword     = findViewById(R.id.edittext_password1);
        mButtonLogin      = findViewById(R.id.button_login);
        mTextViewRegister = findViewById(R.id.textview_register);
        chk               = findViewById(R.id.checkBox);


        //SharedPre
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);

        mTextUsername.setText(sharedPreferences.getString("name",""));
        mTextPassword.setText(sharedPreferences.getString("pass",""));
        chk.setChecked(sharedPreferences.getBoolean("checked",false));

        checkTK();

        //Tạo sự kiện onClick, addTextChange . . .
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this);
                builder.setMessage("Bạn muốn đăng kí!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent  = new Intent(DangNhap.this,DangKi.class);
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
        });


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();

//                Boolean res = db.checkUser(user);
//                Boolean res2 = db.checkPass(pwd);
                Boolean res3 = db.check(user,pwd);


                if(user.equals("")||pwd.equals("")){
                    Toast.makeText(DangNhap.this, "Không được để trống!", Toast.LENGTH_SHORT).show();

                }else {
                    if(res3 == true ){
                        if(chk.isChecked()){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",user);
                            editor.putString("pass",pwd);
                            editor.putBoolean("check",true);
                            editor.commit();
                        }else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("name");
                            editor.remove("pass");
                            editor.remove("check");
                            editor.commit();
                        }
                        Cursor cursor = db.getTenNguoiDung(user);
                        String ten = "";
                        int id = 0;
                        if (cursor.getCount()==0){
                            Toast.makeText(DangNhap.this, "Không có người dung này!", Toast.LENGTH_SHORT).show();
                        }else {
                            while (cursor.moveToNext()){
                                id = cursor.getInt(0);
                                ten = cursor.getString(3);
                            }
                        }
                        Intent intent = new Intent(DangNhap.this, TrangChu.class);
                        intent.putExtra("hvt","" + ten);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        Animatoo.animateInAndOut(DangNhap.this);
                    }else {
                        Toast.makeText(DangNhap.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        mTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mTextUsername.getText().toString().trim().equals("")){
                    mTextUsername.setError("Vui lòng không để trống!");
                }else if(charSequence.length() <= 5) {
                    mTextUsername.setError("Tối thiểu 6 kí tự!");
                }else if(!charSequence.toString().trim().matches(noWhiteSpace)){
                    mTextUsername.setError("Vui lòng không chưa kí tự đặc biệt!");
                }else {
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
                if(mTextPassword.getText().toString().trim().equals("")){
                    mTextPassword.setError("Vui lòng không để trống!");
                }else if(charSequence.length() <= 5) {
                    mTextPassword.setError("Tối thiểu 6 kí tự!");
                }else if(!charSequence.toString().trim().matches(noWhiteSpace)){
                    mTextPassword.setError("Vui lòng không chứa kí tự đặc biệt!");
                }else {
                    mTextPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void checkTK(){
        if(sharedPreferences.getString("name","").equals("") && sharedPreferences.getString("pass","").equals("")){
        }else {
            String user = mTextUsername.getText().toString().trim();
            Cursor cursor = db.getTenNguoiDung(user);
            String ten = "";
            int id = 0;
            if (cursor.getCount() == 0){
                Toast.makeText(DangNhap.this, "Không có người dung này!", Toast.LENGTH_SHORT).show();
            }else {
                while (cursor.moveToNext()){
                    id = cursor.getInt(0);
                    ten = cursor.getString(3);
                }
            }
            Intent intent = new Intent(DangNhap.this, TrangChu.class);
            intent.putExtra("hvt","" + ten);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }
}