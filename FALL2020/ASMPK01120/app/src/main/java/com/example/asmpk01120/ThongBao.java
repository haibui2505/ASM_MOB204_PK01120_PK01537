package com.example.asmpk01120;

public class ThongBao {
    private Integer Id;
    private String TenThongBao;
    private String NgayThongBao;

    public ThongBao(Integer id, String tenThongBao, String ngayThongBao) {
        Id = id;
        this.TenThongBao = tenThongBao;
        this.NgayThongBao = ngayThongBao;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTenThongBao() {
        return TenThongBao;
    }

    public void setTenThongBao(String tenThongBao) {
        TenThongBao = tenThongBao;
    }

    public String getNgayThongBao() {
        return NgayThongBao;
    }

    public void setNgayThongBao(String ngayThongBao) {
        NgayThongBao = ngayThongBao;
    }
}
