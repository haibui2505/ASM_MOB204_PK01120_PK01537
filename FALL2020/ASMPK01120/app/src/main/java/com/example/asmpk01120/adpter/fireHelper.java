package com.example.asmpk01120.adpter;

public class fireHelper {
    private String trangThai, maDonHang, tenNguoiDung, maKhachHang;

    public fireHelper(String trangThai, String maDonHang, String tenNguoiDung, String maKhachHang) {
        this.trangThai = trangThai;
        this.maDonHang = maDonHang;
        this.tenNguoiDung = tenNguoiDung;
        this.maKhachHang = maKhachHang;
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

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
}
