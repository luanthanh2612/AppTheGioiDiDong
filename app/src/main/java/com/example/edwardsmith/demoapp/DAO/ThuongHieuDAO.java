package com.example.edwardsmith.demoapp.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.edwardsmith.demoapp.DTO.ThuongHieuDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdwardSmith on 1/16/17.
 */

public class ThuongHieuDAO {


    private SQLiteDatabase database;
    public ThuongHieuDAO(CreateDatabase createDatabase) {
        database = createDatabase.openConnection();
    }

    public boolean ThemTH(ThuongHieuDTO thuongHieuDTO){

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENTH,thuongHieuDTO.getTenTH());
        values.put(CreateDatabase.HINHTH,thuongHieuDTO.getHinhTH());
        values.put(CreateDatabase.MALOAISP,thuongHieuDTO.getMaLoaiSanPham());

        long check = database.insert(CreateDatabase.TABLE_THUONGHIEU,null,values);

        if (check > 0){
            return true;
        }

        return false;
    }
    public List<ThuongHieuDTO> LayDanhSachThuongHieu(){
        List<ThuongHieuDTO> listTH = new ArrayList<>();

        String truyVan = "SELECT * FROM " + CreateDatabase.TABLE_THUONGHIEU;

        Cursor cursor = database.rawQuery(truyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int maTH = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MATH));
            String tenTH = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENTH));
            byte[] hinhTH = cursor.getBlob(cursor.getColumnIndex(CreateDatabase.HINHTH));
            int maloaisSP = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAISP));

            ThuongHieuDTO thuongHieuDTO = new ThuongHieuDTO(tenTH,hinhTH);
            thuongHieuDTO.setMaTH(maTH);
            thuongHieuDTO.setMaLoaiSanPham(maloaisSP);

            listTH.add(thuongHieuDTO);

            cursor.moveToNext();
        }
        cursor.close();

        return listTH;
    }
    public ThuongHieuDTO LayThuongHieuTheoMa(int maTh){
        ThuongHieuDTO thuongHieuDTO = null;
        String truyVan = "SELECT * FROM " + CreateDatabase.TABLE_THUONGHIEU + " WHERE " + CreateDatabase.MATH + " = " + maTh;

        Cursor cursor = database.rawQuery(truyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int maTH = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MATH));
            String tenTH = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENTH));
            byte[] hinhTH = cursor.getBlob(cursor.getColumnIndex(CreateDatabase.HINHTH));
            int maloaisSP = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAISP));

            thuongHieuDTO = new ThuongHieuDTO(tenTH,hinhTH);
            thuongHieuDTO.setMaTH(maTH);
            thuongHieuDTO.setMaLoaiSanPham(maloaisSP);


            cursor.moveToNext();
        }
        cursor.close();

        return thuongHieuDTO;
    }
    public boolean xoaTHTheoMa(int maTH){

        if (database.delete(CreateDatabase.TABLE_THUONGHIEU,CreateDatabase.MATH + " = ?",new String[]{String.valueOf(maTH)}) > 0){
            return true;
        }

        return false;
    }

    public boolean capNhatThuongHieuTheoMa(ThuongHieuDTO thuongHieuDTO,int maTh){

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENTH,thuongHieuDTO.getTenTH());
        values.put(CreateDatabase.HINHTH,thuongHieuDTO.getHinhTH());

        int check = database.update(CreateDatabase.TABLE_THUONGHIEU,values,CreateDatabase.MATH + " = ?",new String[]{String.valueOf(maTh)});
        if (check > 0){
            return true;
        }

        return false;
    }

}
