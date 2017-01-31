package com.example.edwardsmith.demoapp.DTO;

/**
 * Created by EdwardSmith on 1/20/17.
 */

public class NhanVienDTO {

    private int maNV;
    private int maLoaiNV;
    private String tenNV,diaChi,SDT,matKhau;

    public NhanVienDTO(String tenNV, String diaChi, String SDT) {
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        this.SDT = SDT;

    }

    public NhanVienDTO() {
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getMaLoaiNV() {
        return maLoaiNV;
    }

    public void setMaLoaiNV(int maLoaiNV) {
        this.maLoaiNV = maLoaiNV;
    }

}
