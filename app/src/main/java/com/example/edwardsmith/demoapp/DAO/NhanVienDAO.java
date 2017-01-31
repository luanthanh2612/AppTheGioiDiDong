package com.example.edwardsmith.demoapp.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.edwardsmith.demoapp.DTO.NhanVienDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;

/**
 * Created by EdwardSmith on 1/24/17.
 */

public class NhanVienDAO {

    private SQLiteDatabase database;

    public NhanVienDAO(CreateDatabase createDatabase) {

        database = createDatabase.openConnection();

    }

    public boolean themNhanVien(NhanVienDTO nhanVienDTO){

        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENNV,nhanVienDTO.getTenNV());
        values.put(CreateDatabase.DIACHI,nhanVienDTO.getDiaChi());
        values.put(CreateDatabase.SDT,nhanVienDTO.getSDT());
        values.put(CreateDatabase.MALOAINV,nhanVienDTO.getMaLoaiNV());
        values.put(CreateDatabase.MATKHAU,nhanVienDTO.getMatKhau());

        if (database.insert(CreateDatabase.TABLE_NHANVIEN,null,values) > 0){
            return true;
        }
        return false;
    }

    public boolean dangNhap(String tenDN,String matKhau){
        String sql = "SELECT * FROM " + CreateDatabase.TABLE_NHANVIEN + " WHERE " + CreateDatabase.TENNV + " = '" + tenDN + "'" +
                " AND " + CreateDatabase.MATKHAU + " = '" + matKhau + "'";

        Cursor cursor = database.rawQuery(sql,null);
        if (cursor.getCount() > 0){
            return true;
        }

        cursor.close();

        return false;
    }
    public NhanVienDTO layThongTinNV(){
        NhanVienDTO nhanvienDTO = null;
        String sql = "SELECT * FROM " + CreateDatabase.TABLE_NHANVIEN;

        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int maNV = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MANV));
            int maLoaiNV = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAINV));
            String tenNV = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENNV));
            String diaChi = cursor.getString(cursor.getColumnIndex(CreateDatabase.DIACHI));
            String matKhau = cursor.getString(cursor.getColumnIndex(CreateDatabase.MATKHAU));
            String SDT = cursor.getString(cursor.getColumnIndex(CreateDatabase.SDT));

            nhanvienDTO = new NhanVienDTO(tenNV,diaChi,SDT);
            nhanvienDTO.setMaLoaiNV(maLoaiNV);
            nhanvienDTO.setMaNV(maNV);
            nhanvienDTO.setMatKhau(matKhau);

            cursor.moveToNext();
        }
        cursor.close();

        return nhanvienDTO;
    }
    public NhanVienDTO layThongTinNVTheoMa(int maNV){
        NhanVienDTO nhanvienDTO = null;
        String sql = "SELECT * FROM " + CreateDatabase.TABLE_NHANVIEN + " WHERE " + CreateDatabase.MANV + " = " + maNV;

        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int manv = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MANV));
            int maLoaiNV = cursor.getInt(cursor.getColumnIndex(CreateDatabase.MALOAINV));
            String tenNV = cursor.getString(cursor.getColumnIndex(CreateDatabase.TENNV));
            String diaChi = cursor.getString(cursor.getColumnIndex(CreateDatabase.DIACHI));
            String matKhau = cursor.getString(cursor.getColumnIndex(CreateDatabase.MATKHAU));
            String SDT = cursor.getString(cursor.getColumnIndex(CreateDatabase.SDT));

            nhanvienDTO = new NhanVienDTO(tenNV,diaChi,SDT);
            nhanvienDTO.setMaLoaiNV(maLoaiNV);
            nhanvienDTO.setMaNV(manv);
            nhanvienDTO.setMatKhau(matKhau);

            cursor.moveToNext();
        }
        cursor.close();

        return nhanvienDTO;
    }
    public boolean capNhatNhanVienTheoMa(NhanVienDTO nhanVienDTO,int maNV){
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TENNV,nhanVienDTO.getTenNV());
        values.put(CreateDatabase.DIACHI,nhanVienDTO.getDiaChi());
        values.put(CreateDatabase.SDT,nhanVienDTO.getSDT());
        values.put(CreateDatabase.MATKHAU,nhanVienDTO.getMatKhau());

        int check = database.update(CreateDatabase.TABLE_NHANVIEN,values,CreateDatabase.MANV + " = ?",new String[]{String.valueOf(maNV)});

        if (check > 0 ){
            return true;
        }

        return false;
    }
}
