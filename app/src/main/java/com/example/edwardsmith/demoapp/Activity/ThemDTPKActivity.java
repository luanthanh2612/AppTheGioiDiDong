package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.Adapter.CustomSpinnerDienThoai;
import com.example.edwardsmith.demoapp.DAO.DienThoaiDAO;
import com.example.edwardsmith.demoapp.DAO.ThuongHieuDAO;
import com.example.edwardsmith.demoapp.DTO.DienThoaiDTO;
import com.example.edwardsmith.demoapp.DTO.ThuongHieuDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ThemDTPKActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener {
    EditText edtTen,editTextThongTin,editTextGia;
    ImageView imageViewHinhDTPK;
    Button btnScan,btnThem;
    private final int PICK_PHOTO_CODE = 888;
    boolean kiemtra = false;
    CreateDatabase createDatabase;
    DienThoaiDAO dienThoaiDAO;
    Spinner spinnerDT;
    List<ThuongHieuDTO> listTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_dtpk);

        createDatabase = CreateDatabase.getInstance(getApplicationContext());
        ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAO(createDatabase);
        edtTen = (EditText) findViewById(R.id.edittext_tendtpk);
        spinnerDT = (Spinner) findViewById(R.id.spinnerDienThoai);
        editTextGia = (EditText) findViewById(R.id.edittext_Gia);
        editTextThongTin = (EditText) findViewById(R.id.edittext_ThongTin);
        imageViewHinhDTPK = (ImageView) findViewById(R.id.imageViewHinhDTPK);
        btnScan = (Button) findViewById(R.id.buttonScanDT);
        btnThem = (Button) findViewById(R.id.buttonThemDTPK);

        btnThem.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        imageViewHinhDTPK.setOnClickListener(this);
        editTextThongTin.setOnFocusChangeListener(this);
        edtTen.setOnFocusChangeListener(this);
        editTextGia.setOnFocusChangeListener(this);


        listTH = thuongHieuDAO.LayDanhSachThuongHieu();
        CustomSpinnerDienThoai customSpinnerDienThoai = new CustomSpinnerDienThoai(this,listTH);
        spinnerDT.setAdapter(customSpinnerDienThoai);
        customSpinnerDienThoai.notifyDataSetChanged();

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            String resultJson = bd.getString("ResultSP");
            parserJsonSP(resultJson);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_PHOTO_CODE:

                if (resultCode == RESULT_OK){
                    if (data != null){
                        Uri uri = data.getData();

                        try {
                            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            Bitmap image = scaleDown(selectedImage,300,true);
                            //Bitmap image = getResizedBitmap(selectedImage,200,200);

                            imageViewHinhDTPK.setImageBitmap(image);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }


                break;

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.buttonThemDTPK:

                dienThoaiDAO = new DienThoaiDAO(createDatabase);
                String tenDT = edtTen.getText().toString();
                String thongTin = editTextThongTin.getText().toString();
                String giaDT = editTextGia.getText().toString();
                byte[] hinhDT = imageView_to_byte(imageViewHinhDTPK);
                int maTH = listTH.get(spinnerDT.getSelectedItemPosition()).getMaTH();
                Log.d("MaTH",maTH + "");

                if (tenDT.isEmpty() || !kiemtra || thongTin.isEmpty() || giaDT.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Kiem tra lai thong tin nhap",Toast.LENGTH_SHORT).show();
                }else {
                   DienThoaiDTO dienThoaiDTO = new DienThoaiDTO(hinhDT,thongTin,tenDT,Integer.parseInt(giaDT),maTH);
                    Log.d("MaTH",dienThoaiDTO.getMaTH() + "");
                   if (dienThoaiDAO.ThemDienThoai(dienThoaiDTO)){
                       edtTen.setText("");
                       editTextGia.setText("");
                       editTextThongTin.setText("");
                       imageViewHinhDTPK.setImageDrawable(getResources().getDrawable(R.drawable.logochamhoi));
                       Toast.makeText(getApplicationContext(),"Them Thanh Cong",Toast.LENGTH_SHORT).show();

                   }else {
                       Toast.makeText(getApplicationContext(),"Them That Bai",Toast.LENGTH_SHORT).show();
                   }

                }


                break;

            case R.id.buttonScanDT:
                Intent iScan = new Intent(getApplicationContext(),ScanActivity.class);
                startActivity(iScan);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;

            case R.id.imageViewHinhDTPK:

                Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickImage.setType("image/*");

                Intent chooserIntent = Intent.createChooser(pickImage,"Chon Thu muc");

                if (chooserIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(chooserIntent,PICK_PHOTO_CODE);
                }


                break;
        }

    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
    public byte[] imageView_to_byte(ImageView imageViewHinh){
        Bitmap hinh = ((BitmapDrawable)imageViewHinh.getDrawable()).getBitmap();

        ByteArrayOutputStream luongByte = new ByteArrayOutputStream();

        hinh.compress(Bitmap.CompressFormat.PNG,100,luongByte);

        return luongByte.toByteArray();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.edittext_tendtpk:

                if (!hasFocus){
                    if (edtTen.getText().toString().length() <= 2){
                        edtTen.setError("Nhap tu 2 ky tu tro len");
                        kiemtra = false;
                    }else {
                        kiemtra = true;
                    }
                }

                break;
            case R.id.edittext_ThongTin:
                if (!hasFocus){
                    if (editTextThongTin.getText().toString().length() <= 2){
                        editTextThongTin.setError("Nhap tu 2 ky tu tro len");
                        kiemtra = false;
                    }else {
                        kiemtra = true;
                    }
                }
                break;
            case R.id.edittext_Gia:
                if (!hasFocus){
                    if (editTextGia.getText().toString().isEmpty()){
                        editTextGia.setError("Khong duoc bo trong");
                        kiemtra = false;
                    }else {
                        kiemtra = true;
                    }
                }
                break;
        }
    }
    private void parserJsonSP(String json){

        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("SanPham");

            int size = array.length();
            for (int i = 0; i< size; i++){
                JSONObject item = array.getJSONObject(i);

                String tenSP = item.getString("TenSP");
                int giaSP = item.getInt("Gia");
                String Hinh = item.getString("Hinh");
                String thongTin = item.getString("ThongTin");

                edtTen.setText(tenSP);
                editTextGia.setText(String.valueOf(giaSP));
                editTextThongTin.setText(thongTin);

                Picasso.with(this).load(Hinh).error(R.drawable.imgerror).into(imageViewHinhDTPK);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
