package com.example.asmpk01120.adpter;

import android.content.Intent;

public class GuiHang {

    private Integer Id;
    private String Ten;
    private String phone;
    private String thuho;
    private String ngay;
    private String diaChi;
    private Integer trangThai;

    public GuiHang(Integer id, String ten, String phone, String thuho, String ngay, String diaChi, Integer trangThai) {
        Id = id;
        Ten = ten;
        this.phone = phone;
        this.thuho = thuho;
        this.ngay = ngay;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getThuho() {
        return thuho;
    }

    public void setThuho(String thuho) {
        this.thuho = thuho;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
