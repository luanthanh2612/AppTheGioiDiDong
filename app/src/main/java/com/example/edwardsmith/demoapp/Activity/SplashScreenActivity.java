package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.edwardsmith.demoapp.DAO.LoaiNhanVienDAO;
import com.example.edwardsmith.demoapp.DAO.LoaiSanPhamDAO;
import com.example.edwardsmith.demoapp.DTO.LoaiNhanVienDTO;
import com.example.edwardsmith.demoapp.DTO.LoaiSanPhamDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        CreateDatabase createDatabase = CreateDatabase.getInstance(getApplicationContext());

        LoaiSanPhamDTO ThuongHieu = new LoaiSanPhamDTO(1,"Thuong Hieu");
        LoaiSanPhamDTO PhuKien = new LoaiSanPhamDTO(2,"Phu Kien");

        LoaiNhanVienDTO nvQuanLy = new LoaiNhanVienDTO(1,"QuanLy");

        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(createDatabase);
        LoaiNhanVienDAO loaiNhanVienDAO = new LoaiNhanVienDAO(createDatabase);

        if (loaiSanPhamDAO.kiemtraData() && loaiNhanVienDAO.kiemtraData()){
            if (loaiSanPhamDAO.themLoaiSanPham(ThuongHieu) && loaiSanPhamDAO.themLoaiSanPham(PhuKien) && loaiNhanVienDAO.ThemLoaiNV(nvQuanLy)){
                Log.d("Them","Thanh Cong");
            }else {
                Log.d("Them","That bai");
            }
        }else {
            Log.d("KiemTraLoaiSP","Sai DK");
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent iMain = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(iMain);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        },3000);

    }
}
