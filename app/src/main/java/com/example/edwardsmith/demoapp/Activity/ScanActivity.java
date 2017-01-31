package com.example.edwardsmith.demoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by EdwardSmith on 1/20/17.
 */

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(getApplicationContext());
        scannerView.setAutoFocus(true);
        scannerView.setFormats(ZXingScannerView.ALL_FORMATS);

        setContentView(scannerView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    public void handleResult(Result result) {

        try {
            JSONObject jsonResult = new JSONObject(result.getText());
            if (jsonResult.getString("Loai").equals("NV")){
                Intent iDangKy = new Intent(getApplicationContext(),DangKyActivity.class);
                iDangKy.putExtra("Result",result.getText());
                startActivity(iDangKy);
            }else if (jsonResult.getString("Loai").equals("SP")) {
                Intent iSanPham = new Intent(getApplicationContext(),ThemDTPKActivity.class);
                iSanPham.putExtra("ResultSP",result.getText());
                startActivity(iSanPham);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
