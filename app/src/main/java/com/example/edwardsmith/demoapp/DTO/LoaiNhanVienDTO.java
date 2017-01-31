package com.example.edwardsmith.demoapp.DTO;

/**
 * Created by EdwardSmith on 1/21/17.
 */

public class LoaiNhanVienDTO {

    private int maLoaiNV;
    private String tenLoaiNV;

    public LoaiNhanVienDTO(int maLoaiNV, String tenLoaiNV) {
        this.maLoaiNV = maLoaiNV;
        this.tenLoaiNV = tenLoaiNV;
    }

    public int getMaLoaiNV() {
        return maLoaiNV;
    }

    public void setMaLoaiNV(int maLoaiNV) {
        this.maLoaiNV = maLoaiNV;
    }

    public String getTenLoaiNV() {
        return tenLoaiNV;
    }

    public void setTenLoaiNV(String tenLoaiNV) {
        this.tenLoaiNV = tenLoaiNV;
    }
}
