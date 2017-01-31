package com.example.edwardsmith.demoapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardsmith.demoapp.DTO.PhuKienDTO;
import com.example.edwardsmith.demoapp.R;

import java.util.List;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class CustomAdapterPhuKien extends BaseAdapter {

    Context context;
    List<PhuKienDTO> phuKienDTOList;

    public CustomAdapterPhuKien(Context context, List<PhuKienDTO> phuKienDTOList) {
        this.context = context;
        this.phuKienDTOList = phuKienDTOList;
    }

    @Override
    public int getCount() {
        return phuKienDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return phuKienDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView imageViewHinhPK;
        TextView txtTenPK;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.custom_adapter_phu_kien,parent,false);
            viewHolder.imageViewHinhPK = (ImageView) convertView.findViewById(R.id.imageView_hinhPK);
            viewHolder.txtTenPK = (TextView) convertView.findViewById(R.id.textViewTenPK);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhuKienDTO phuKienDTO = phuKienDTOList.get(position);

        viewHolder.txtTenPK.setText(phuKienDTO.getTenPhukien());
        Log.d("MALoaiSPPK",phuKienDTO.getMaLoaiSanPham() + "");
        Bitmap bmp = BitmapFactory.decodeByteArray(phuKienDTO.getHinhPK(),0,phuKienDTO.getHinhPK().length);
        viewHolder.imageViewHinhPK.setImageBitmap(bmp);


        return convertView;
    }
}
