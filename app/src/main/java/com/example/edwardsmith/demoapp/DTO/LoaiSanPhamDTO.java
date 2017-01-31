package com.example.edwardsmith.demoapp.DTO;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class LoaiSanPhamDTO {

    private int maLoaiSanPham;
    private String tenLoaiSanPham;

    public LoaiSanPhamDTO(int maLoaiSanPham,String tenLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public int getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }
}
