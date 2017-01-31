package com.example.edwardsmith.demoapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardsmith.demoapp.DTO.ThuongHieuDTO;
import com.example.edwardsmith.demoapp.R;

import java.util.List;

/**
 * Created by EdwardSmith on 1/16/17.
 */

public class CustomAdapterTH extends BaseAdapter {

     Context context;
     List<ThuongHieuDTO> listTH;

    public CustomAdapterTH(Context context, List<ThuongHieuDTO> listTH) {
        this.context = context;
        this.listTH = listTH;
    }

    @Override
    public int getCount() {
        return listTH.size();
    }

    @Override
    public Object getItem(int position) {
        return listTH.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView imageViewHinhTH;
        TextView txtTen;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.custom_adapter_thuong_hieu,parent,false);

            viewHolder.imageViewHinhTH = (ImageView) convertView.findViewById(R.id.imageView_HinhThuongHieu);
            viewHolder.txtTen = (TextView) convertView.findViewById(R.id.textViewTenTH);


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ThuongHieuDTO thuongHieuDTO = listTH.get(position);

        viewHolder.txtTen.setText(thuongHieuDTO.getTenTH());
        Bitmap bmp = BitmapFactory.decodeByteArray(thuongHieuDTO.getHinhTH(),0,thuongHieuDTO.getHinhTH().length);

        viewHolder.imageViewHinhTH.setImageBitmap(bmp);




        return convertView;
    }
}
