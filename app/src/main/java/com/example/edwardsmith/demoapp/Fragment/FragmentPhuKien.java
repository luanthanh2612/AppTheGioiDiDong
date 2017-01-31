package com.example.edwardsmith.demoapp.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.Adapter.CustomAdapterPhuKien;
import com.example.edwardsmith.demoapp.DAO.PhuKienDAO;
import com.example.edwardsmith.demoapp.DTO.PhuKienDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.edwardsmith.demoapp.Activity.ThemTHActivity.PICK_PHOTO_CODE;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class FragmentPhuKien extends Fragment {
    ListView lvPhuKien;
    CreateDatabase createDatabase;
    List<PhuKienDTO> listPK;
    PhuKienDAO phuKienDAO;
    EditText editTextTen;
    ImageView imageViewHinh;
    Button btnCapNhat;
    PhuKienDTO phuKienDTO;
    CustomAdapterPhuKien customAdapterPhuKien;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDatabase = CreateDatabase.getInstance(getContext());
        phuKienDAO = new PhuKienDAO(createDatabase);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phu_kien,container,false);
        lvPhuKien = (ListView) view.findViewById(R.id.listViewPK);

        listPK = phuKienDAO.layDanhSachPhuKien();

        customAdapterPhuKien = new CustomAdapterPhuKien(getContext(),listPK);
        lvPhuKien.setAdapter(customAdapterPhuKien);
        customAdapterPhuKien.notifyDataSetChanged();

        ViewCompat.setNestedScrollingEnabled(lvPhuKien,true);

        if (layMaNV() > 0){
            registerForContextMenu(lvPhuKien);
        }

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int maPK = listPK.get(info.position).getMaPK();

        switch (id){
            case R.id.mXoa:
                if (phuKienDAO.xoaPKTheoMa(maPK)){
                    Toast.makeText(getContext(),"Xoa Thanh Cong",Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("XoaPK","That Bai");
                }
                break;

            case R.id.mCapNhat:

                showDialog(getContext(),maPK);


                break;
        }

        return true;
    }


    private int layMaNV(){
        SharedPreferences preferences = getActivity().getSharedPreferences("TenDN",MODE_PRIVATE);

        return preferences.getInt(CreateDatabase.MANV,0);
    }

    private void showDialog(Context context, final int itemPosition){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_hangdienthoai);

        editTextTen = (EditText) dialog.findViewById(R.id.edittext_tenDienThoai);
        imageViewHinh = (ImageView) dialog.findViewById(R.id.imageViewHinhUpdateDienThoai);
        btnCapNhat = (Button) dialog.findViewById(R.id.buttonUpdateDienThoai);

        phuKienDTO = phuKienDAO.LayPKTheoMa(itemPosition);
        editTextTen.setText(phuKienDTO.getTenPhukien());
        Bitmap bmp = BitmapFactory.decodeByteArray(phuKienDTO.getHinhPK(),0,phuKienDTO.getHinhPK().length);
        imageViewHinh.setImageBitmap(bmp);

        imageViewHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(pickIntent,"Chon Thu Muc");

                if (chooserIntent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(chooserIntent,PICK_PHOTO_CODE);
                }

            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenPK = editTextTen.getText().toString();
                byte[] hinhPK = imageView_to_byteArray(imageViewHinh);

                if (tenPK.isEmpty()){
                    Toast.makeText(getContext(),"Nhap Day Du Thong Tin",Toast.LENGTH_SHORT).show();
                }else {
                    phuKienDTO = new PhuKienDTO(tenPK,hinhPK);

                    if (phuKienDAO.capNhatPKtheoMa(phuKienDTO,itemPosition)){
                        Toast.makeText(getContext(),"Cap Nhat Thanh Cong",Toast.LENGTH_SHORT).show();
                        listPK = phuKienDAO.layDanhSachPhuKien();

                        customAdapterPhuKien = new CustomAdapterPhuKien(getContext(),listPK);
                        lvPhuKien.setAdapter(customAdapterPhuKien);
                        customAdapterPhuKien.notifyDataSetChanged();
                        dialog.cancel();
                    }else {
                        Log.d("CapNhatTH","That Bai");
                    }
                }

            }
        });

        dialog.show();
    }
    public byte[] imageView_to_byteArray(ImageView imageView){

        Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PICK_PHOTO_CODE:


                if (resultCode == RESULT_OK){
                    if (data != null){
                        Uri uri = data.getData();
                        Log.d("Check","data");
                        try {
                            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                            Bitmap scaleBitmap = scaleDown(selectedImage,200,true);

                            imageViewHinh.setImageBitmap(scaleBitmap);

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
