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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.Activity.DienThoaiActivity;
import com.example.edwardsmith.demoapp.Adapter.CustomAdapterTH;
import com.example.edwardsmith.demoapp.DAO.ThuongHieuDAO;
import com.example.edwardsmith.demoapp.DTO.ThuongHieuDTO;
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

public class FragmentThuongHieu extends Fragment implements AdapterView.OnItemLongClickListener,PopupMenu.OnMenuItemClickListener {
    CreateDatabase createDatabase;
    GridView gridView;
    List<ThuongHieuDTO> listThuongHieuDTO;
    CustomAdapterTH customAdapterTH;
    ThuongHieuDAO thuongHieuDAO;
    int itemPosition;
    ThuongHieuDTO thuongHieuDTO;
    EditText editTextTen;
    ImageView imageViewHinh;
    Button btnCapNhat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDatabase = CreateDatabase.getInstance(getContext());
        thuongHieuDAO = new ThuongHieuDAO(createDatabase);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuong_hieu,container,false);
        gridView = (GridView) view.findViewById(R.id.gridViewTH);

        listThuongHieuDTO = thuongHieuDAO.LayDanhSachThuongHieu();

        customAdapterTH = new CustomAdapterTH(getContext(),listThuongHieuDTO);

        gridView.setAdapter(customAdapterTH);
        customAdapterTH.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iDienThoai = new Intent(getContext(), DienThoaiActivity.class);
                iDienThoai.putExtra("MATH",listThuongHieuDTO.get(position).getMaTH());
                startActivity(iDienThoai);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
        });

        gridView.setOnItemLongClickListener(this);

        ViewCompat.setNestedScrollingEnabled(gridView,true);

        return view;
    }
    private int layMaNV(){
        SharedPreferences preferences = getActivity().getSharedPreferences("TenDN",MODE_PRIVATE);

        return preferences.getInt(CreateDatabase.MANV,0);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (layMaNV() > 0){
            showPopupMenu(view,position);
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        thuongHieuDTO = listThuongHieuDTO.get(itemPosition);

        int id = item.getItemId();
        switch (id){
            case R.id.mXoa:


                if (thuongHieuDAO.xoaTHTheoMa(thuongHieuDTO.getMaTH())){
                    Toast.makeText(getContext(),"Xoa Thanh Cong",Toast.LENGTH_SHORT).show();

                    listThuongHieuDTO = thuongHieuDAO.LayDanhSachThuongHieu();

                    customAdapterTH = new CustomAdapterTH(getContext(),listThuongHieuDTO);
                    gridView.setAdapter(customAdapterTH);
                    customAdapterTH.notifyDataSetChanged();
                }else {
                    Log.d("XoaTH","That Bai");
                }

                break;
            case R.id.mCapNhat:

                showDialog(getContext(),thuongHieuDTO.getMaTH());


                break;
        }
        return false;
    }
    private void showPopupMenu(View v,int position){
        itemPosition = position;
        android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getContext(),v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_context);
        popupMenu.show();
    }
    private void showDialog(Context context, final int itemPosition){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_hangdienthoai);

        editTextTen = (EditText) dialog.findViewById(R.id.edittext_tenDienThoai);
        imageViewHinh = (ImageView) dialog.findViewById(R.id.imageViewHinhUpdateDienThoai);
        btnCapNhat = (Button) dialog.findViewById(R.id.buttonUpdateDienThoai);

        thuongHieuDTO = thuongHieuDAO.LayThuongHieuTheoMa(itemPosition);
        editTextTen.setText(thuongHieuDTO.getTenTH());
        Bitmap bmp = BitmapFactory.decodeByteArray(thuongHieuDTO.getHinhTH(),0,thuongHieuDTO.getHinhTH().length);
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

                String tenTH = editTextTen.getText().toString();
                byte[] hinhTH = imageView_to_byteArray(imageViewHinh);

                if (tenTH.isEmpty()){
                    Toast.makeText(getContext(),"Nhap Day Du Thong Tin",Toast.LENGTH_SHORT).show();
                }else {
                    thuongHieuDTO = new ThuongHieuDTO(tenTH,hinhTH);

                    if (thuongHieuDAO.capNhatThuongHieuTheoMa(thuongHieuDTO,itemPosition)){
                        Toast.makeText(getContext(),"Cap Nhat Thanh Cong",Toast.LENGTH_SHORT).show();
                        listThuongHieuDTO = thuongHieuDAO.LayDanhSachThuongHieu();

                        customAdapterTH = new CustomAdapterTH(getContext(),listThuongHieuDTO);
                        gridView.setAdapter(customAdapterTH);
                        customAdapterTH.notifyDataSetChanged();
                        dialog.cancel();
                    }else {
                        Log.d("CapNhatTH","That Bai");
                    }
                }

            }
        });

        dialog.show();
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

}
