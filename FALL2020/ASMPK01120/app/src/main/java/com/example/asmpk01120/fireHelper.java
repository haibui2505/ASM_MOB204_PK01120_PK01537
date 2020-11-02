package com.example.asmpk01120;

public class fireHelper {
    private String trangThai, maDonHang, tenNguoiDung;

    public fireHelper(String trangThai, String maDonHang, String tenNguoiDung) {
        this.trangThai = trangThai;
        this.maDonHang = maDonHang;
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }
}
