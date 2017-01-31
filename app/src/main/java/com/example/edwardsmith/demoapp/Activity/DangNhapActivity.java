package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.DAO.NhanVienDAO;
import com.example.edwardsmith.demoapp.DTO.NhanVienDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtTenDN,edtMatKhau;
    Button btnDangNhap;
    NhanVienDAO nhanVienDAO;
    NhanVienDTO nhanVienDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        CreateDatabase createDatabase = CreateDatabase.getInstance(getApplicationContext());
        edtTenDN = (EditText) findViewById(R.id.edittext_tenDangNhap);
        edtMatKhau = (EditText) findViewById(R.id.edittext_matkhauDN);
        btnDangNhap = (Button) findViewById(R.id.buttonDangNhap);

        btnDangNhap.setOnClickListener(this);

        nhanVienDAO = new NhanVienDAO(createDatabase);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.buttonDangNhap:
                String tenDN = edtTenDN.getText().toString();
                String matKhau = edtMatKhau.getText().toString();

                if (tenDN.isEmpty() || matKhau.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Vui long nhap day du",Toast.LENGTH_SHORT).show();

                }else {
                    if (nhanVienDAO.dangNhap(tenDN,matKhau)){
                        nhanVienDTO = nhanVienDAO.layThongTinNV();

                        SharedPreferences preferences = getSharedPreferences("TenDN",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CreateDatabase.TENNV,nhanVienDTO.getTenNV());
                        editor.putInt(CreateDatabase.MANV,nhanVienDTO.getMaNV());
                        editor.apply();

                        Intent iHome = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(iHome);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }else {
                        Toast.makeText(getApplicationContext(),"That Bai",Toast.LENGTH_SHORT).show();
                    }

                }

                break;

        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
