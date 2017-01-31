package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edwardsmith.demoapp.Adapter.CustomAdapterDienThoai;
import com.example.edwardsmith.demoapp.DAO.DienThoaiDAO;
import com.example.edwardsmith.demoapp.DTO.DienThoaiDTO;
import com.example.edwardsmith.demoapp.Database.CreateDatabase;
import com.example.edwardsmith.demoapp.R;

import java.util.List;
import java.util.Locale;

public class DienThoaiActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Toolbar toolbar;
    ListView lvDienThoai;
    int maTH;
    CreateDatabase createDatabase;
    List<DienThoaiDTO> listDT;
    DienThoaiDAO dienThoaiDAO;
    DienThoaiDTO dienThoaiDTO;
    CustomAdapterDienThoai customAdapterDienThoai;
    MenuItem mThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        createDatabase = CreateDatabase.getInstance(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolBarDienThoai);
        lvDienThoai = (ListView) findViewById(R.id.listViewDienThoai);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Bundle bd = getIntent().getExtras();
        if (bd != null){
            maTH = bd.getInt("MATH");
        }

        dienThoaiDAO = new DienThoaiDAO(createDatabase);
        listDT = dienThoaiDAO.layDanhSachDTTheoMa(maTH);

        customAdapterDienThoai = new CustomAdapterDienThoai(this,listDT);
        lvDienThoai.setAdapter(customAdapterDienThoai);
        customAdapterDienThoai.notifyDataSetChanged();

        ViewCompat.setNestedScrollingEnabled(lvDienThoai,true);

        lvDienThoai.setOnItemClickListener(this);

        if (layMaNV() > 0){
            registerForContextMenu(lvDienThoai);
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        int id = item.getItemId();
        switch (id){
            case R.id.mXoa:
                dienThoaiDTO = listDT.get(position);
                if (dienThoaiDAO.XoaDienThoaiTheoMa(dienThoaiDTO.getMaDT())){
                    listDT = dienThoaiDAO.layDanhSachDTTheoMa(maTH);

                    customAdapterDienThoai = new CustomAdapterDienThoai(getApplicationContext(),listDT);
                    lvDienThoai.setAdapter(customAdapterDienThoai);
                    customAdapterDienThoai.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(),"Xoa Thanh Cong",Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("XoaDT","That bai");
                }
                break;

            case R.id.mCapNhat:
                DienThoaiDTO dienThoaiDTO = listDT.get(position);
                Intent iUpdate = new Intent(getApplicationContext(),UpdateActivity.class);
                iUpdate.putExtra("DienThoai",dienThoaiDTO);
                startActivity(iUpdate);
                break;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dien_thoai,menu);
        mThem = menu.findItem(R.id.mThemDienThoai);

        if (layMaNV() > 0){
            mThem.setVisible(true);
        }

        SearchView searchView = (SearchView) menu.findItem(R.id.mTimKiem).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                customAdapterDienThoai.filter(newText.toLowerCase(Locale.getDefault()).trim());

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                Intent iTH = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(iTH);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;

            case R.id.mThemDienThoai:

                Intent iThemDT = new Intent(getApplicationContext(),ThemDTPKActivity.class);
                startActivity(iThemDT);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }



    @Override
    protected void onResume() {
        super.onResume();
        listDT = dienThoaiDAO.layDanhSachDTTheoMa(maTH);

        customAdapterDienThoai = new CustomAdapterDienThoai(getApplicationContext(),listDT);
        lvDienThoai.setAdapter(customAdapterDienThoai);
        customAdapterDienThoai.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DienThoaiDTO dienThoaiDTO = listDT.get(position);
        Intent iThongTin = new Intent(getApplicationContext(),ThongTinDTActivity.class);
        iThongTin.putExtra("DienThoai",dienThoaiDTO);
        startActivity(iThongTin);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    private int layMaNV(){
        SharedPreferences preferences = getSharedPreferences("TenDN",MODE_PRIVATE);

        return preferences.getInt(CreateDatabase.MANV,0);
    }
}
