package com.example.edwardsmith.demoapp.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.edwardsmith.demoapp.DTO.LoaiSanPhamDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class LoaiSanPhamDAO {
    SQLiteDatabase database;
    public LoaiSanPhamDAO(CreateDatabase createDatabase) {

        database = createDatabase.openConnection();

    }
    public boolean kiemtraData(){

        String sql = "SELECT * FROM " + CreateDatabase.TABLE_LOAISANPHAM;
        Cursor cursor = database.rawQuery(sql,null);
        if (cursor.getCount() == 0){
            return true;
        }
        cursor.close();

        return false;
    }
    public boolean themLoaiSanPham(LoaiSanPhamDTO loaiSanPhamDTO){

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENLOAISP,loaiSanPhamDTO.getTenLoaiSanPham());

        if (database.insert(CreateDatabase.TABLE_LOAISANPHAM,null,values) > 0){
            return true;
        }

        return false;
    }
    public List<LoaiSanPhamDTO> layDanhSachLoaiSP(){
        List<LoaiSanPhamDTO> listLSP = new ArrayList<>();

        String sql = "SELECT * FROM " + CreateDatabase.TABLE_LOAISANPHAM;

        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            int maLoaiSP = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAISP));
            String tenLoaiSP = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENLOAISP));

            LoaiSanPhamDTO loaiSanPhamDTO = new LoaiSanPhamDTO(maLoaiSP,tenLoaiSP);

            listLSP.add(loaiSanPhamDTO);

            cursor.moveToNext();
        }

        cursor.close();

        return listLSP;
    }

}
