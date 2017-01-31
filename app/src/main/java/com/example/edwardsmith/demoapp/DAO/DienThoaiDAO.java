package com.example.edwardsmith.demoapp.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.edwardsmith.demoapp.DTO.DienThoaiDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdwardSmith on 1/18/17.
 */

public class DienThoaiDAO {
    SQLiteDatabase database;
    public DienThoaiDAO(CreateDatabase createDatabase) {
        database = createDatabase.openConnection();
    }

    public boolean ThemDienThoai(DienThoaiDTO dienThoaiDTO){

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENDT,dienThoaiDTO.getTenDT());
        values.put(CreateDatabase.GIADT,dienThoaiDTO.getGiaDT());
        values.put(CreateDatabase.HINHDT,dienThoaiDTO.getHinhDT());
        values.put(CreateDatabase.MATH,dienThoaiDTO.getMaTH());
        values.put(CreateDatabase.THONGTINDT,dienThoaiDTO.getThongTinDT());

        long check = database.insert(CreateDatabase.TABLE_DIENTHOAI,null,values);

        if (check > 0){
            return true;
        }

        return false;
    }
    public List<DienThoaiDTO> layDanhSachDTTheoMa(int maTh){
        List<DienThoaiDTO> listDT = new ArrayList<>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TABLE_DIENTHOAI + " WHERE " + CreateDatabase.MATH + " = '" + maTh +"'";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            int maDT = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MADT));
            int maTH = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MATH));
            int giaDT = cursor.getInt(cursor.getColumnIndex(CreateDatabase.GIADT));
            String tenDT = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENDT));
            byte[] hinhDT = cursor.getBlob(cursor.getColumnIndex(CreateDatabase.HINHDT));
            String thongTin = cursor.getString(cursor.getColumnIndex(CreateDatabase.THONGTINDT));

            DienThoaiDTO dienThoaiDTO = new DienThoaiDTO(hinhDT,thongTin,tenDT,giaDT,maTH);
            dienThoaiDTO.setMaDT(maDT);

            listDT.add(dienThoaiDTO);

            cursor.moveToNext();
        }

        cursor.close();

        return listDT;
    }
    public boolean capNhatDienThoaiTheoMa(DienThoaiDTO dienThoaiDTO,int maDT){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENDT,dienThoaiDTO.getTenDT());
        values.put(CreateDatabase.GIADT,dienThoaiDTO.getGiaDT());
        values.put(CreateDatabase.THONGTINDT,dienThoaiDTO.getThongTinDT());
        values.put(CreateDatabase.HINHDT,dienThoaiDTO.getHinhDT());

        int check = database.update(CreateDatabase.TABLE_DIENTHOAI,values,CreateDatabase.MADT + " = ?",new String[]{String.valueOf(maDT)});

        if (check > 0){
            return true;
        }

        return false;
    }
    public boolean XoaDienThoaiTheoMa(int maDT){

        int check = database.delete(CreateDatabase.TABLE_DIENTHOAI,CreateDatabase.MADT + " = ?",new String[]{String.valueOf(maDT)});
        if (check > 0){
            return true;
        }

        return false;
    }
}
