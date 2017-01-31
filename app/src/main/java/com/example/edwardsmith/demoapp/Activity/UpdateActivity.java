package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.example.edwardsmith.demoapp.DAO.DienThoaiDAO;
import com.example.edwardsmith.demoapp.DTO.DienThoaiDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.edwardsmith.demoapp.Activity.ThemTHActivity.PICK_PHOTO_CODE;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextTen,editTextGia,editTextThongTin;
    ImageView imageViewHinhSP;
    Button btnUpdate;
    DienThoaiDTO dienThoaiDTO;
    int maDT,maTH;
    CreateDatabase createDatabase;
    DienThoaiDAO dienThoaiDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        createDatabase = CreateDatabase.getInstance(getApplicationContext());
        editTextTen = (EditText) findViewById(R.id.ediitext_TenUpdate);
        editTextGia = (EditText) findViewById(R.id.edittext_GiaUpdate);
        editTextThongTin = (EditText) findViewById(R.id.edittext_ThongTinUpdate);
        imageViewHinhSP = (ImageView) findViewById(R.id.imageViewHinhUpdate);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);

        imageViewHinhSP.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        dienThoaiDAO = new DienThoaiDAO(createDatabase);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            dienThoaiDTO = (DienThoaiDTO) bd.getSerializable("DienThoai");
            maTH = dienThoaiDTO.getMaTH();
            maDT = dienThoaiDTO.getMaDT();
            editTextTen.setText(dienThoaiDTO.getTenDT());
            editTextGia.setText(String.valueOf(dienThoaiDTO.getGiaDT()));
            editTextThongTin.setText(dienThoaiDTO.getThongTinDT());

            Bitmap bmp = BitmapFactory.decodeByteArray(dienThoaiDTO.getHinhDT(),0,dienThoaiDTO.getHinhDT().length);
            imageViewHinhSP.setImageBitmap(bmp);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.buttonUpdate:
                String tenDT = editTextTen.getText().toString();
                String giaDT = editTextGia.getText().toString();
                String thongTinDT = editTextThongTin.getText().toString();
                byte[] hinhDT = imageView_to_byteArray(imageViewHinhSP);

                DienThoaiDTO dienThoaiDTO = new DienThoaiDTO(hinhDT,thongTinDT,tenDT,Integer.parseInt(giaDT),maTH);
                dienThoaiDTO.setMaDT(maDT);

                if (dienThoaiDAO.capNhatDienThoaiTheoMa(dienThoaiDTO,maDT)){

                    Toast.makeText(getApplicationContext(),"Cap Nhat Thanh Cong",Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Log.d("CapNhatDienThoai","That bai");
                }

                break;
            case R.id.imageViewHinhUpdate:
                Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickImage.setType("image/*");

                Intent chooserIntent = Intent.createChooser(pickImage,"Chon Thu muc");

                if (chooserIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(chooserIntent,PICK_PHOTO_CODE);
                }

                break;
        }

    }
    public byte[] imageView_to_byteArray(ImageView imageView){

        Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

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

                            imageViewHinhSP.setImageBitmap(image);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

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
}
