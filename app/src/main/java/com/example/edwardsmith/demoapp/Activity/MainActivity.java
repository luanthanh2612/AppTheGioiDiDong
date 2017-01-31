package com.example.edwardsmith.demoapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.Adapter.ViewPagerAdapter;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.Fragment.FragmentPhuKien;
import com.example.edwardsmith.demoapp.Fragment.FragmentThuongHieu;
import com.example.edwardsmith.demoapp.R;

public class MainActivity extends BaseRequestPermissionActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    private static final int REQUEST_PERMISSIONS = 20;
    private static long back_pressed;
    SharedPreferences preferences;
    MenuItem itemTenDN,itemDangXuat,itemThem;
    Menu menuCurrent;
    int maNV = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.super.requestAppPermissions(new String[]{Manifest.permission.CAMERA},R.string.app_name,REQUEST_PERMISSIONS);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentThuongHieu(),"Dien Thoai");
        viewPagerAdapter.addFragment(new FragmentPhuKien(),"Phu Kien");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);




    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menuCurrent = menu;
        getMenuInflater().inflate(R.menu.main_menu,menu);
        itemTenDN = menu.findItem(R.id.mDangNhap);
        itemDangXuat = menu.findItem(R.id.mDangXuat);
        itemThem = menu.findItem(R.id.mThem);

        if (!layTenDN().equals("")){
            itemTenDN.setTitle(layTenDN());
            itemDangXuat.setVisible(true);
            itemThem.setVisible(true);
        }else {
            itemDangXuat.setVisible(false);
            itemThem.setVisible(false);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.mThem:

                Intent iThem = new Intent(this,ThemTHActivity.class);
                startActivity(iThem);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                break;

            case R.id.mCamera:

                Intent iScan = new Intent(getApplicationContext(),ScanActivity.class);
                startActivity(iScan);

                break;

            case R.id.mDangNhap:
                if (layTenDN().equals("")){
                    Intent iDN = new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivity(iDN);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }else {
                    Intent IUpdate = new Intent(getApplicationContext(),NhanVienActivity.class);
                    startActivity(IUpdate);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                break;
            case R.id.mDangXuat:

                if (!layTenDN().equals("")){

                    capNhapTrangThaiDN("",0);
                    this.menuCurrent.clear();
                    this.onCreateOptionsMenu(menuCurrent);
                }

                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Nhan 1 lan nua de thoat!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
    private String layTenDN(){
        preferences = getSharedPreferences("TenDN",MODE_PRIVATE);

        return preferences.getString(CreateDatabase.TENNV,"");
    }

    private void capNhapTrangThaiDN(String tenDN,int maNV){
        SharedPreferences preferences = getSharedPreferences("TenDN",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(CreateDatabase.TENNV,tenDN);
        editor.putInt(CreateDatabase.MANV,maNV);
        editor.apply();
    }

}
