package com.example.edwardsmith.demoapp.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.edwardsmith.demoapp.DTO.LoaiNhanVienDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;

/**
 * Created by EdwardSmith on 1/21/17.
 */

public class LoaiNhanVienDAO {

    SQLiteDatabase database;

    public LoaiNhanVienDAO(CreateDatabase createDatabase) {
        database = createDatabase.openConnection();

    }

    public boolean kiemtraData(){

        String sql = "SELECT * FROM " + CreateDatabase.TABLE_LOAINV;
        Cursor cursor = database.rawQuery(sql,null);

        if (cursor.getCount() == 0){
            return true;
        }

        cursor.close();
        return false;
    }

    public boolean ThemLoaiNV(LoaiNhanVienDTO loaiNhanVienDTO){

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.MALOAINV,loaiNhanVienDTO.getMaLoaiNV());
        values.put(CreateDatabase.TENLOAINV,loaiNhanVienDTO.getTenLoaiNV());

        if (database.insert(CreateDatabase.TABLE_LOAINV,null,values) > 0){
            return true;
        }
        return false;
    }
    public LoaiNhanVienDTO LayMaLoaiNV(){
        String tenLoaiNV = "";
        int maloaiNV = 0;

        String sql = "SELECT * FROM " + CreateDatabase.TABLE_LOAINV;

        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            tenLoaiNV = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENLOAINV));
            maloaiNV = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAINV));
            cursor.moveToNext();
        }

        cursor.close();

        return  new LoaiNhanVienDTO(maloaiNV,tenLoaiNV);
    }

}
