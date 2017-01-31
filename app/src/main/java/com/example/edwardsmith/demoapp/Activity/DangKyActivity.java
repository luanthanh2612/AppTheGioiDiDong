package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.DAO.LoaiNhanVienDAO;
import com.example.edwardsmith.demoapp.DAO.NhanVienDAO;
import com.example.edwardsmith.demoapp.DTO.LoaiNhanVienDTO;
import com.example.edwardsmith.demoapp.DTO.NhanVienDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextTen,editTextDiaChi,editTextSDT,editTextPassWord;
    Button btnDangKy,btnDangNhap;
    LoaiNhanVienDTO loaiNhanVienDTO;
    LoaiNhanVienDAO loaiNhanVienDAO;
    NhanVienDTO nhanVienDTO;
    NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        CreateDatabase createDatabase = CreateDatabase.getInstance(getApplicationContext());
        editTextTen = (EditText) findViewById(R.id.ediitext_tenDK);
        editTextDiaChi = (EditText) findViewById(R.id.edittext_diachiDK);
        editTextSDT = (EditText) findViewById(R.id.edittext_sdtDK);
        editTextPassWord = (EditText) findViewById(R.id.edittext_matkhauDK);
        btnDangKy = (Button) findViewById(R.id.buttonDangKy);
        btnDangNhap = (Button) findViewById(R.id.buttonDangKyDN);

        btnDangNhap.setOnClickListener(this);
        btnDangKy.setOnClickListener(this);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            String result = bd.getString("Result");
            Log.d("Result",result);
            show_ThongTin(layNhanVienTuScan(result));
        }

        loaiNhanVienDAO = new LoaiNhanVienDAO(createDatabase);
        nhanVienDAO = new NhanVienDAO(createDatabase);
        loaiNhanVienDTO = loaiNhanVienDAO.LayMaLoaiNV();

    }
    private void show_ThongTin(NhanVienDTO nhanVienDTO){
        editTextTen.setText(nhanVienDTO.getTenNV());
        editTextSDT.setText(nhanVienDTO.getSDT());
        editTextDiaChi.setText(nhanVienDTO.getDiaChi());
    }
    private NhanVienDTO layNhanVienTuScan(String json){
        String tenNV = "";
        String DiaChi = "";
        String SDT = "";
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("NhanVien");

            int size = array.length();
            for (int i = 0;i<size;i++){

                JSONObject item = array.getJSONObject(i);

                tenNV = item.getString("TenNV");
                DiaChi = item.getString("Diachi");
                SDT = item.getString("SDT");


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new NhanVienDTO(tenNV,DiaChi,SDT);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.buttonDangKy:

                String tenNV = editTextTen.getText().toString();
                String diaChi = editTextDiaChi.getText().toString();
                String SDT = editTextSDT.getText().toString();
                String matKhau = editTextPassWord.getText().toString();
                int maLoaiNV = loaiNhanVienDTO.getMaLoaiNV();


                nhanVienDTO = new NhanVienDTO(tenNV,diaChi,SDT);
                nhanVienDTO.setMatKhau(matKhau);
                nhanVienDTO.setMaLoaiNV(maLoaiNV);


                if (matKhau.isEmpty() || tenNV.isEmpty() || diaChi.isEmpty() || SDT.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Nhap day du thong tin",Toast.LENGTH_SHORT).show();
                }else {

                    if (nhanVienDAO.themNhanVien(nhanVienDTO)){
                        Toast.makeText(getApplicationContext(),"Dang Ky Thanh Cong",Toast.LENGTH_SHORT).show();
                        Intent iDangNhap = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(iDangNhap);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }else {
                        Toast.makeText(getApplicationContext(),"Dang Ky That Bai",Toast.LENGTH_SHORT).show();
                    }


                }

                break;

            case R.id.buttonDangKyDN:

                break;

        }
    }
}
