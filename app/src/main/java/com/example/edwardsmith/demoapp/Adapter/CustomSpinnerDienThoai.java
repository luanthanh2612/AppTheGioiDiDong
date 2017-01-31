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
 * Created by EdwardSmith on 1/19/17.
 */

public class CustomSpinnerDienThoai extends BaseAdapter {

    Context context;
    List<ThuongHieuDTO> thuongHieuDTOList;

    public CustomSpinnerDienThoai(Context context, List<ThuongHieuDTO> thuongHieuDTOList) {
        this.context = context;
        this.thuongHieuDTOList = thuongHieuDTOList;
    }

    @Override
    public int getCount() {
        return thuongHieuDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thuongHieuDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView imageViewHinh;
        TextView txtTen;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_spinner_dien_thoai,parent,false);

            viewHolder.imageViewHinh = (ImageView) convertView.findViewById(R.id.imageViewSpinnerDT);
            viewHolder.txtTen = (TextView) convertView.findViewById(R.id.textViewSpinnerDT);


            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ThuongHieuDTO thuongHieuDTO = thuongHieuDTOList.get(position);

        viewHolder.txtTen.setText(thuongHieuDTO.getTenTH());

        Bitmap bmp = BitmapFactory.decodeByteArray(thuongHieuDTO.getHinhTH(),0,thuongHieuDTO.getHinhTH().length);
        viewHolder.imageViewHinh.setImageBitmap(bmp);


        return convertView;
    }


}
