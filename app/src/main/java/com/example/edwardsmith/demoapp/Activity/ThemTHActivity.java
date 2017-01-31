package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.Adapter.CustomSpinnerLoaiSP;
import com.example.edwardsmith.demoapp.DAO.LoaiSanPhamDAO;
import com.example.edwardsmith.demoapp.DAO.PhuKienDAO;
import com.example.edwardsmith.demoapp.DAO.ThuongHieuDAO;
import com.example.edwardsmith.demoapp.DTO.LoaiSanPhamDTO;
import com.example.edwardsmith.demoapp.DTO.PhuKienDTO;
import com.example.edwardsmith.demoapp.DTO.ThuongHieuDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ThemTHActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageViewTH;
    Button btnThem;
    EditText editTextTenTH;
    public static final int PICK_PHOTO_CODE = 1043;
     View viewToast = null;
    CreateDatabase createDatabase;
    Spinner spinner;
    List<LoaiSanPhamDTO> listLoaiSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_th);

        createDatabase = CreateDatabase.getInstance(getApplicationContext());
        imageViewTH = (ImageView) findViewById(R.id.imageView_hinhTH);
        btnThem = (Button) findViewById(R.id.buttonThemTH);
        editTextTenTH = (EditText) findViewById(R.id.edit_tenTH);
        spinner = (Spinner) findViewById(R.id.spinnerLoai);

        btnThem.setOnClickListener(this);
        imageViewTH.setOnClickListener(this);


        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(createDatabase);
        listLoaiSP = loaiSanPhamDAO.layDanhSachLoaiSP();

        CustomSpinnerLoaiSP customSpinnerLoaiSP = new CustomSpinnerLoaiSP(this,listLoaiSP);

        spinner.setAdapter(customSpinnerLoaiSP);
        customSpinnerLoaiSP.notifyDataSetChanged();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PICK_PHOTO_CODE:


                if (resultCode == RESULT_OK){
                    if (data != null){
                        Uri uri = data.getData();
                        Log.d("Check","data");
                        try {
                            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            Bitmap scaleBitmap = scaleDown(selectedImage,200,true);

                            imageViewTH.setImageBitmap(scaleBitmap);

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
            case R.id.buttonThemTH:




                String ten = editTextTenTH.getText().toString();

                if (editTextTenTH.getText().toString().isEmpty()){
                   show_Toast("Khong Duoc Bo Trong");
                }else {
                    int positionSpinner = spinner.getSelectedItemPosition();
                    int maloaisp = listLoaiSP.get(positionSpinner).getMaLoaiSanPham();
                    byte[] hinh = imageView_to_byteArray(imageViewTH);

                    switch (maloaisp){
                        case 1:
                            ThuongHieuDTO thuongHieuDTO = new ThuongHieuDTO(ten,hinh);
                            thuongHieuDTO.setMaLoaiSanPham(maloaisp);
                            ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAO(createDatabase);
                            if (thuongHieuDAO.ThemTH(thuongHieuDTO)){

                                Intent iMain = new Intent(ThemTHActivity.this,MainActivity.class);
                                startActivity(iMain);

                                show_Toast("Thanh Cong");
                            }else {
                                show_Toast("That Bai");
                            }
                            break;
                        case 2:
                            PhuKienDTO phuKienDTO = new PhuKienDTO(ten,hinh);
                            phuKienDTO.setMaLoaiSanPham(maloaisp);

                            PhuKienDAO phuKienDAO = new PhuKienDAO(createDatabase);
                            if (phuKienDAO.themPhukien(phuKienDTO)){

                                Intent iMain = new Intent(ThemTHActivity.this,MainActivity.class);
                                startActivity(iMain);

                                show_Toast("Thanh Cong");
                            }

                            break;
                    }







                }

                break;
            case R.id.imageView_hinhTH:

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                Intent chooserIntent = Intent.createChooser(pickIntent,"Chon Thu Muc");

                if (chooserIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(chooserIntent,PICK_PHOTO_CODE);
                }



                break;
        }
    }
    public void show_Toast(String message){
        viewToast = LayoutInflater.from(this).inflate(R.layout.custom_toast,null);
        ((TextView)viewToast.findViewById(R.id.tv_text)).setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setView(viewToast);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
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
    public byte[] imageView_to_byteArray(ImageView imageView){

        Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

    }
}
