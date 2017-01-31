package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.DAO.NhanVienDAO;
import com.example.edwardsmith.demoapp.DTO.NhanVienDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

public class NhanVienActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextTen,editTextDiachi,editTextMK,editTextSDT;
    Button btnCapNhat;
    CreateDatabase createDatabase;
    NhanVienDAO nhanVienDAO;
    NhanVienDTO nhanVienDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        createDatabase = CreateDatabase.getInstance(getApplicationContext());
        editTextSDT = (EditText) findViewById(R.id.edittext_SDTNV);
        editTextDiachi = (EditText) findViewById(R.id.edittext_diachiNV);
        editTextTen = (EditText) findViewById(R.id.edittext_tenNV);
        editTextMK = (EditText) findViewById(R.id.edittext_matkhauNV);
        btnCapNhat = (Button) findViewById(R.id.buttonUpDateNV);
        btnCapNhat.setOnClickListener(this);

        nhanVienDAO = new NhanVienDAO(createDatabase);
        nhanVienDTO = nhanVienDAO.layThongTinNVTheoMa(layMaNV());

        editTextTen.setText(nhanVienDTO.getTenNV());
        editTextSDT.setText(nhanVienDTO.getSDT());
        editTextDiachi.setText(nhanVienDTO.getDiaChi());
        editTextMK.setText(nhanVienDTO.getMatKhau());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.buttonUpDateNV:
                String tenNV = editTextTen.getText().toString();
                String diaChi = editTextDiachi.getText().toString();
                String matKhau = editTextMK.getText().toString();
                String SDT = editTextSDT.getText().toString();

                if (tenNV.isEmpty() || diaChi.isEmpty() || matKhau.isEmpty() || SDT.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Nhap Day Du Thong Tin",Toast.LENGTH_SHORT).show();
                }else {
                    nhanVienDTO = new NhanVienDTO(tenNV,diaChi,SDT);
                    nhanVienDTO.setMaNV(layMaNV());
                    nhanVienDTO.setMatKhau(matKhau);

                    if (nhanVienDAO.capNhatNhanVienTheoMa(nhanVienDTO,layMaNV())){

                        SharedPreferences preferences = getSharedPreferences("TenDN",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CreateDatabase.TENNV,nhanVienDTO.getTenNV());
                        editor.putInt(CreateDatabase.MANV,nhanVienDTO.getMaNV());
                        editor.apply();

                        Intent iMain = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(iMain);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }else {
                        Log.d("CapNhatNV","That bai");
                    }
                }

                break;
        }
    }
    private int layMaNV(){
        SharedPreferences preferences = getSharedPreferences("TenDN",MODE_PRIVATE);

        return preferences.getInt(CreateDatabase.MANV,0);
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
