package com.example.asmpk01120;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "new.sqlite";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

//    Thoong bao

    public long addThongBao(String ten, String sdt) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenThongBao", ten);
        contentValues.put("NgayThongBao", sdt);

        long res = db.insert("ThongBao", null, contentValues);
        db.close();
        return res;

    }

    //    Gửi hàng
    public long addHangGui(String maso, String TenNguoiNhan, String SDTNguoiNhan, String DiaChiNguoiNhan, String MoTaHangHoa, String GiaTriHangHoa, String TrongLuongHangHoa, String SoLuongHangHoa, String NoiNhan, String TienCuoc, String TienThuHo, String ngay, Integer trangThai) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaNGuoiDung", maso);
        contentValues.put("HoTenNguoiNhan", TenNguoiNhan);
        contentValues.put("SDTNguoiNhan", SDTNguoiNhan);
        contentValues.put("DiaChiNguoiNhan", DiaChiNguoiNhan);
        contentValues.put("TienThuHo", TienThuHo);
        contentValues.put("MoTaHangHoa", MoTaHangHoa);
        contentValues.put("GiaTriHangHoa", GiaTriHangHoa);
        contentValues.put("TrongLuongHangHoa", TrongLuongHangHoa);
        contentValues.put("SoLuongHangHoa", SoLuongHangHoa);
        contentValues.put("NoiNhan", NoiNhan);
        contentValues.put("TienCuoc", TienCuoc);
        contentValues.put("NgayGui", ngay);
        contentValues.put("TrangThai", trangThai);

        long res = db.insert("GuiHang", null, contentValues);
        db.close();
        return res;

    }

    public Cursor checksdt(String number) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from AUTOPHONE where PhoneNumber = ?", new String[]{number});
        return cursor;
    }

    public boolean checkID(String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("Select * from GuiHang where MaNguoiGui =? ", new String[]{username});

        int count = cursor1.getCount();
        cursor1.close();
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    //    Địa chỉ, số điện thoại
    public long addDiaChi(String ms, String name, String sdt, String diachi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaNguoiDung", ms);
        contentValues.put("Ten", name);
        contentValues.put("Phone", sdt);
        contentValues.put("DiaChi", diachi);

        long res = db.insert("DiaChi", null, contentValues);
        db.close();
        return res;

    }

    public Cursor getMaNguoiDung(String ms) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DiaChi where MaNguoiDung = ?", new String[]{ms});
        return cursor;
    }

    //    Đăng nhập, Đăng kí
    public long addUser(String user, String password, String hvt) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Ten", user);
        contentValues.put("Pass", password);
        contentValues.put("TenNguoiDung", hvt);

        long res = db.insert("DanhSach", null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String username) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("Select * from DanhSach where Ten = ? ", new String[]{username});

        int count = cursor1.getCount();
        cursor1.close();
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getTenNguoiDung(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DanhSach where Ten = ?", new String[]{username});
        return cursor;
    }

    public boolean checkPass(String pass) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("Select * from DanhSach where Pass = ? ", new String[]{pass});

        int count = cursor1.getCount();
        cursor1.close();
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean check(String name, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from DanhSach where Ten=? and Pass=?", new String[]{name, pass});

        if (cursor.getCount() > 0) return true;
        else return false;
    }
}
