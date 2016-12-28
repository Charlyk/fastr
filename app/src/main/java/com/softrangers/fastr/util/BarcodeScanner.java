package com.softrangers.fastr.util;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class BarcodeScanner extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    public static final String ACTION_SCAN = "ACTION_SCAN";
    private ZBarScannerView mScannerView;
    private Intent mResultIntent;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);// Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera(Camera.CameraInfo.CAMERA_FACING_BACK);         // Start camera on resume
        mResultIntent = new Intent(ACTION_SCAN);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        String barcode = rawResult.getContents();
        mResultIntent.putExtra("code", barcode);
        setResult(RESULT_OK, mResultIntent);
        finish();
    }
}