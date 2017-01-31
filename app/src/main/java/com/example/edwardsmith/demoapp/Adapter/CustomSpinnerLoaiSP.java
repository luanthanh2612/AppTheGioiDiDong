package com.example.edwardsmith.demoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.edwardsmith.demoapp.DTO.LoaiSanPhamDTO;
import com.example.edwardsmith.demoapp.R;

import java.util.List;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class CustomSpinnerLoaiSP extends BaseAdapter {

    Context context;
    List<LoaiSanPhamDTO> loaiSanPhamDTOList;

    public CustomSpinnerLoaiSP(Context context, List<LoaiSanPhamDTO> loaiSanPhamDTOList) {
        this.context = context;
        this.loaiSanPhamDTOList = loaiSanPhamDTOList;
    }

    @Override
    public int getCount() {
        return loaiSanPhamDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiSanPhamDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.custom_spinner_loaisp,parent,false);
        TextView textViewTenLoaiSP = (TextView) convertView.findViewById(R.id.textViewLoaiSP);

        textViewTenLoaiSP.setText(loaiSanPhamDTOList.get(position).getTenLoaiSanPham());


        return convertView;
    }
}
