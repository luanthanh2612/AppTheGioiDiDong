package com.example.edwardsmith.demoapp.DTO;

import java.io.Serializable;

/**
 * Created by EdwardSmith on 1/18/17.
 */

public class DienThoaiDTO implements Serializable {

    private int maDT,maTH,giaDT;
    private String tenDT,thongTinDT;
    private byte[] hinhDT;

    public DienThoaiDTO(byte[] hinhDT, String thongTinDT, String tenDT, int giaDT, int maTH) {
        this.hinhDT = hinhDT;
        this.thongTinDT = thongTinDT;
        this.tenDT = tenDT;
        this.giaDT = giaDT;
        this.maTH = maTH;
    }

    public int getMaDT() {
        return maDT;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public int getMaTH() {
        return maTH;
    }

    public void setMaTH(int maTH) {
        this.maTH = maTH;
    }

    public int getGiaDT() {
        return giaDT;
    }

    public void setGiaDT(int giaDT) {
        this.giaDT = giaDT;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public String getThongTinDT() {
        return thongTinDT;
    }

    public void setThongTinDT(String thongTinDT) {
        this.thongTinDT = thongTinDT;
    }

    public byte[] getHinhDT() {
        return hinhDT;
    }

    public void setHinhDT(byte[] hinhDT) {
        this.hinhDT = hinhDT;
    }
}
