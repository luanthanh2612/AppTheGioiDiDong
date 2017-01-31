package com.example.edwardsmith.demoapp.DTO;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class PhuKienDTO {

    private int maPK;
    private String tenPhukien;
    private byte[] hinhPK;
    private int maLoaiSanPham;

    public int getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public PhuKienDTO(String tenPhukien, byte[] hinhPK) {
        this.tenPhukien = tenPhukien;
        this.hinhPK = hinhPK;
    }

    public int getMaPK() {
        return maPK;
    }

    public void setMaPK(int maPK) {
        this.maPK = maPK;
    }

    public String getTenPhukien() {
        return tenPhukien;
    }

    public void setTenPhukien(String tenPhukien) {
        this.tenPhukien = tenPhukien;
    }

    public byte[] getHinhPK() {
        return hinhPK;
    }

    public void setHinhPK(byte[] hinhPK) {
        this.hinhPK = hinhPK;
    }
}
