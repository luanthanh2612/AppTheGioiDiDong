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

import com.example.edwardsmith.demoapp.DTO.DienThoaiDTO;
import com.example.edwardsmith.demoapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by EdwardSmith on 1/18/17.
 */

public class CustomAdapterDienThoai extends BaseAdapter {

    private Context context;
    private List<DienThoaiDTO> listDT;
    private ArrayList<DienThoaiDTO> arrayListDT;

    public CustomAdapterDienThoai(Context context, List<DienThoaiDTO> listDT) {
        this.context = context;
        this.listDT = listDT;
        this.arrayListDT = new ArrayList<>();
        this.arrayListDT.addAll(listDT);

    }

    @Override
    public int getCount() {
        return listDT.size();
    }

    @Override
    public Object getItem(int position) {
        return listDT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView imageViewHinh;
        TextView txtTen,txtGia;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_adapter_dien_thoai,parent,false);

            viewHolder.imageViewHinh = (ImageView) convertView.findViewById(R.id.imageViewHinhDT);
            viewHolder.txtGia = (TextView) convertView.findViewById(R.id.textViewGiaDT);
            viewHolder.txtTen = (TextView) convertView.findViewById(R.id.textViewTenDT);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DienThoaiDTO dienThoaiDTO = listDT.get(position);

        DecimalFormat format = new DecimalFormat();


        viewHolder.txtTen.setText(dienThoaiDTO.getTenDT());
        viewHolder.txtGia.setText(format.format(dienThoaiDTO.getGiaDT()));

        Bitmap bmp = BitmapFactory.decodeByteArray(dienThoaiDTO.getHinhDT(),0,dienThoaiDTO.getHinhDT().length);
        viewHolder.imageViewHinh.setImageBitmap(bmp);


        return convertView;
    }
    public void filter(String query){
        query = query.toLowerCase(Locale.getDefault());
        listDT.clear();

        if (query.length() == 0){
            listDT.addAll(arrayListDT);
        }else {
            for (DienThoaiDTO dienThoaiDTO : arrayListDT){

                if (dienThoaiDTO.getTenDT().toLowerCase(Locale.getDefault()).contains(query)){
                    listDT.add(dienThoaiDTO);
                }

            }
        }
        notifyDataSetChanged();
    }
}
