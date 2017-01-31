package com.example.edwardsmith.demoapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EdwardSmith on 1/16/17.
 */

public class CreateDatabase extends SQLiteOpenHelper {
    private static CreateDatabase mInstance ;
    private static final String DATABASE_NAME = "QLDT";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public static final String TABLE_LOAISANPHAM = "LOAISANPHAM";
    public static final String MALOAISP = "MALOAISP";
    public static final String TENLOAISP = "TENLOAISP";

    public static final String TABLE_THUONGHIEU = "THUONGHIEU";
    public static final String MATH = "MATH";
    public static final String TENTH = "TENTH";
    public static final String HINHTH = "HINHTH";

    public static final String TABLE_PHUKIEN = "PHUKIEN";
    public static final String MAPK = "MAPK";
    public static final String TENPK = "TENPK";
    public static final String HINHPK = "HINHPK";

    public static final String TABLE_DIENTHOAI = "DIENTHOAI";
    public static final String MADT = "MADT";
    public static final String TENDT = "TENDT";
    public static final String GIADT = "GIADT";
    public static final String HINHDT = "HINHDT";
    public static final String THONGTINDT = "THONGTINDT";

    public static final String TABLE_LOAINV = "LOAINV";
    public static final String MALOAINV = "MALOAINV";
    public static final String TENLOAINV = "TENLOAINV";

    public static final String  TABLE_NHANVIEN = "NHANVIEN";
    public static final String MANV = "MANV";
    public static final String TENNV = "TENNV";
    public static final String DIACHI = "DIACHI";
    public static final String SDT = "SDT";
    public static final String MATKHAU = "MATKHAU";


    public static CreateDatabase getInstance(Context context){
        if (mInstance == null){
            mInstance = new CreateDatabase(context.getApplicationContext());
        }
        return mInstance;
    }

    private CreateDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlLoaiSanpham = "CREATE TABLE IF NOT EXISTS " + TABLE_LOAISANPHAM + " ( " + MALOAISP + " INTEGER PRIMARY KEY, " +
                TENLOAISP + " VARCHAR )" ;

        String sqlThuongHieu = "CREATE TABLE IF NOT EXISTS " + TABLE_THUONGHIEU + " ( " + MATH + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TENTH + " VARCHAR, " + MALOAISP + " INTEGER, " + HINHTH + " BLOB )";

        String sqlPhuKien = "CREATE TABLE IF NOT EXISTS " + TABLE_PHUKIEN + " ( " + MAPK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TENPK + " VARCHAR, " + MALOAISP + " INTEGER, " + HINHPK + " BLOB )";

        String sqlDienThoai = "CREATE TABLE IF NOT EXISTS " + TABLE_DIENTHOAI + " ( " + MADT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TENDT + " VARCHAR, " + GIADT + " INTEGER, " + HINHDT + " BLOB, " + THONGTINDT + " TEXT, " + MATH + " INTEGER )";

        String sqlLoaiNhanVien = "CREATE TABLE IF NOT EXISTS " + TABLE_LOAINV + " ( " + MALOAINV + " INTEGER PRIMARY KEY, " + TENLOAINV + " VARCHAR )";

        String sqlNhanVien = "CREATE TABLE IF NOT EXISTS " + TABLE_NHANVIEN + " ( " + MANV + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TENNV + " VARCHAR ," +
                DIACHI + " TEXT, " + SDT + " TEXT, " + MATKHAU + " VARCHAR, " + MALOAINV + " INTEGER )";

        db.execSQL(sqlLoaiSanpham);
        db.execSQL(sqlThuongHieu);
        db.execSQL(sqlPhuKien);
        db.execSQL(sqlDienThoai);
        db.execSQL(sqlLoaiNhanVien);
        db.execSQL(sqlNhanVien);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAISANPHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUONGHIEU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHUKIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIENTHOAI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAINV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHANVIEN);

        onCreate(db);
    }

    public SQLiteDatabase openConnection(){

        return getWritableDatabase();
    }

}
