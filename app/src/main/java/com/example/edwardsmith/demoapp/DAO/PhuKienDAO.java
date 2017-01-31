package com.example.edwardsmith.demoapp.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.edwardsmith.demoapp.DTO.PhuKienDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class PhuKienDAO  {
     SQLiteDatabase database;
    public PhuKienDAO(CreateDatabase createDatabase) {

        database = createDatabase.openConnection();

    }

    public boolean themPhukien(PhuKienDTO phuKienDTO){

        ContentValues values = new ContentValues();

        values.put(CreateDatabase.TENPK,phuKienDTO.getTenPhukien());
        values.put(CreateDatabase.HINHPK,phuKienDTO.getHinhPK());
        values.put(CreateDatabase.MALOAISP,phuKienDTO.getMaLoaiSanPham());

        long check = database.insert(CreateDatabase.TABLE_PHUKIEN,null,values);

        if (check > 0){
            return true;
        }

        return false;
    }

    public List<PhuKienDTO> layDanhSachPhuKien(){
        List<PhuKienDTO> list = new ArrayList<>();

        String truyVan = "SELECT * FROM " + CreateDatabase.TABLE_PHUKIEN;

        Cursor cursor = database.rawQuery(truyVan,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            int maPK = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MAPK));
            String tenPK = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENPK));
            int maloaisp = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAISP));
            byte[] hinhsp = cursor.getBlob(cursor.getColumnIndex(CreateDatabase.HINHPK));

            PhuKienDTO phuKienDTO = new PhuKienDTO(tenPK,hinhsp);
            phuKienDTO.setMaPK(maPK);
            phuKienDTO.setMaLoaiSanPham(maloaisp);

            list.add(phuKienDTO);

            cursor.moveToNext();
        }

        cursor.close();

        return list;

    }
    public boolean xoaPKTheoMa(int maPk){

        if (database.delete(CreateDatabase.TABLE_PHUKIEN,CreateDatabase.MAPK + " = ?",new String[]{String.valueOf(maPk)}) > 0){
            return true;
        }

        return false;


    }

    public boolean capNhatPKtheoMa(PhuKienDTO phuKienDTO, int maPk){

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENPK,phuKienDTO.getTenPhukien());
        values.put(CreateDatabase.HINHPK,phuKienDTO.getHinhPK());

        int check = database.update(CreateDatabase.TABLE_PHUKIEN,values,CreateDatabase.MAPK + " = ?",new String[]{String.valueOf(maPk)});
        if (check > 0){
            return true;
        }
        return false;
    }
    public PhuKienDTO LayPKTheoMa(int maPK){
        PhuKienDTO phuKienDTO = null;
        String truyVan = "SELECT * FROM " + CreateDatabase.TABLE_PHUKIEN + " WHERE " + CreateDatabase.MAPK + " = " + maPK;

        Cursor cursor = database.rawQuery(truyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int mapk = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MAPK));
            String tenPK = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENPK));
            byte[] hinhPK = cursor.getBlob(cursor.getColumnIndex(CreateDatabase.HINHPK));
            int maloaisSP = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAISP));

            phuKienDTO = new PhuKienDTO(tenPK,hinhPK);
            phuKienDTO.setMaPK(mapk);
            phuKienDTO.setMaLoaiSanPham(maloaisSP);


            cursor.moveToNext();
        }
        cursor.close();

        return phuKienDTO;
    }
}
