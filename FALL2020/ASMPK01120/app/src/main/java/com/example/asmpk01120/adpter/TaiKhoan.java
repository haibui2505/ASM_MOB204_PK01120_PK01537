package com.example.asmpk01120.adpter;

public class TaiKhoan {
    private int id;
    private String hovaten;
    private String tennguoidung;
    private String pass;

    public TaiKhoan(int id, String hovaten, String tennguoidung, String pass) {
        this.id = id;
        this.hovaten = hovaten;
        this.tennguoidung = tennguoidung;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHovaten() {
        return hovaten;
    }

    public void setHovaten(String hovaten) {
        this.hovaten = hovaten;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
