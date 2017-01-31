package com.example.edwardsmith.demoapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardsmith.demoapp.DTO.DienThoaiDTO;
import com.example.edwardsmith.demoapp.R;

import java.text.DecimalFormat;

public class ThongTinDTActivity extends AppCompatActivity implements View.OnClickListener {
    DienThoaiDTO dienThoaiDTO;
    ImageView imgHinh;
    TextView txtTen,txtGia,txtMoTa;
    Button btnDatHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_dt);

        imgHinh = (ImageView) findViewById(R.id.imageViewHinhThongTinDT);
        txtGia = (TextView) findViewById(R.id.textViewThongTinGiaDT);
        txtTen = (TextView) findViewById(R.id.textViewThongTinTenDT);
        txtMoTa = (TextView) findViewById(R.id.textViewThongChiTietDT);
        btnDatHang = (Button) findViewById(R.id.buttonMua);

        btnDatHang.setOnClickListener(this);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            dienThoaiDTO = (DienThoaiDTO) bd.getSerializable("DienThoai");
        }

        showDetail();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.buttonMua:

                break;
        }
    }
    private void showDetail(){
        Bitmap bmp = BitmapFactory.decodeByteArray(dienThoaiDTO.getHinhDT(),0,dienThoaiDTO.getHinhDT().length);
        imgHinh.setImageBitmap(bmp);

        DecimalFormat format = new DecimalFormat("#,###");

        txtTen.setText(dienThoaiDTO.getTenDT());
        txtGia.setText(format.format(dienThoaiDTO.getGiaDT()));
        txtMoTa.setText(dienThoaiDTO.getThongTinDT());

    }
}
