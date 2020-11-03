package com.example.asmpk01120;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.asmpk01120.adpter.DatabaseHelper;

public class DangKi extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db;
    EditText mTextUsername, mTextPassword, getTextCnfPassword, mTextTenNguoiDung, edittext_name;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        //Validate form
        final String noWhiteSpace = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$";
        final String pass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        final String vali_hoTen = "^[A-ZẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴa-zàáãạảăắằẳẵặâấầẩẫậèéẹẻẽêềếểễệđìíĩỉịòóõọỏôốồổỗộơớờởỡợùúũụủưứừửữựỳỵỷỹý ]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

        //Database
        db = new DatabaseHelper(this, "new.sqlite", null, 1);

        //Ánh xạ
        mTextUsername = findViewById(R.id.edittext_username);
        mTextPassword = findViewById(R.id.edittext_password);
        mTextTenNguoiDung = findViewById(R.id.edittext_name);
        getTextCnfPassword = findViewById(R.id.edittext_cnf_repassword);
        mButtonRegister = findViewById(R.id.button_register);
        mTextViewLogin = findViewById(R.id.textview_register);
        edittext_name = findViewById(R.id.edittext_name);

        //các sự kiện
        mTextViewLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);

        edittext_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittext_name.getText().toString().trim().equals("")) {
                    edittext_name.setError("Không để trống!");
                } else if (!edittext_name.getText().toString().trim().matches(vali_hoTen)) {
                    edittext_name.setError("Nhập đúng định dạng hộ cái!");
                } else if (s.length() > 25){
                    edittext_name.setError("Không lớn hơn 25 kí tự!");
                } else edittext_name.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mTextUsername.equals("")) {
                    mTextUsername.setError("Vui lòng không để trống!");
                } else if (charSequence.length() <= 5) {
                    mTextUsername.setError("Tối thiểu 6 kí tự!");
                } else if (!charSequence.toString().trim().matches(noWhiteSpace)) {
                    mTextUsername.setError("Vui lòng không chưa kí tự đặc biệt!");
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
                    mTextPassword.setError("Vui lòng không để trống!");
                } else if (charSequence.length() <= 5) {
                    mTextPassword.setError("Tối thiểu 6 kí tự!");
                } else if (!charSequence.toString().trim().matches(pass)) {
                    mTextPassword.setError("Vui lòng không chứa kí tự đặc biệt!");
                } else {
                    mTextPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getTextCnfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals(mTextPassword.getText().toString().trim())) {
                    getTextCnfPassword.setError("Nhập lại không đúng!");
                } else
                    getTextCnfPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_register:
                Logindangnhap();
                break;
            case R.id.button_register:
                dangKimoi();
                break;
        }
    }

    private void dangKimoi() {
        String user = mTextUsername.getText().toString().trim();
        String pwd = mTextPassword.getText().toString().trim();
        String hvt = mTextTenNguoiDung.getText().toString().trim();
        String cnf_pwd = getTextCnfPassword.getText().toString().trim();

        if (user.equals("") || pwd.equals("")) {
            Toast.makeText(DangKi.this, "Không để trống!", Toast.LENGTH_SHORT).show();
        } else {
            if (pwd.equals(cnf_pwd)) {

                Boolean res = db.checkUser(user);
                if (res == true) {
                    Toast.makeText(DangKi.this, "Tài khoản này đã có!", Toast.LENGTH_SHORT).show();
                } else {
                    if (user.length() > 5 || pwd.length() > 5 || cnf_pwd.length() > 5) {
                        long val = db.addUser(user, pwd, hvt);

                        if (val > 0) {

                            Toast.makeText(DangKi.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangKi.this, DangNhap.class);
                            startActivity(intent);

                            Animatoo.animateInAndOut(DangKi.this);

                        } else {
                            Toast.makeText(DangKi.this, "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DangKi.this, "Vui lòng nhập đúng định dạng!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(DangKi.this, "Nhập lại mật khẩu phải giống nhau!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Logindangnhap() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DangKi.this);
        builder.setMessage("Bạn muốn đăng nhập!");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent LoginIntent = new Intent(DangKi.this, DangNhap.class);
                startActivity(LoginIntent);
                Animatoo.animateInAndOut(DangKi.this);
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