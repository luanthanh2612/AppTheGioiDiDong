package com.example.edwardsmith.demoapp.DTO;

/**
 * Created by EdwardSmith on 1/16/17.
 */

public class ThuongHieuDTO {

    private int maTH;
    private String tenTH;
    private byte[] hinhTH;
    private int maLoaiSanPham;

    public ThuongHieuDTO() {
    }

    public ThuongHieuDTO(int maTH, String tenTH, byte[] hinhTH, int maLoaiSanPham) {

        this.maTH = maTH;
        this.tenTH = tenTH;
        this.hinhTH = hinhTH;
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public int getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public ThuongHieuDTO(String tenTH, byte[] hinhTH) {

        this.tenTH = tenTH;
        this.hinhTH = hinhTH;
    }

    public int getMaTH() {
        return maTH;
    }

    public void setMaTH(int maTH) {
        this.maTH = maTH;
    }

    public String getTenTH() {
        return tenTH;
    }

    public void setTenTH(String tenTH) {
        this.tenTH = tenTH;
    }

    public byte[] getHinhTH() {
        return hinhTH;
    }

    public void setHinhTH(byte[] hinhTH) {
        this.hinhTH = hinhTH;
    }
}
