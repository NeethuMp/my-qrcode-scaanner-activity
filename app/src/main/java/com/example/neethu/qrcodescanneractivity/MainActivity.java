package com.example.neethu.qrcodescanneractivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QRScanner_Beta";
    SurfaceView cameraView;
    TextView barcodeInfo;
    Button save,show;
    DBHelper dbHelper;
    String scannedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        barcodeInfo = (TextView) findViewById(R.id.textView);
        save = (Button) findViewById(R.id.button);
        show= (Button) findViewById(R.id.button2);
        dbHelper = new DBHelper(this);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        final CameraSource cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Log.e(TAG, "Camera permission denied! Aborting...");
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {
                        @Override
                        public void run() {
                            scannedData = barcodes.valueAt(0).displayValue;
                            barcodeInfo.setText(scannedData);
                            save.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(scannedData) ) {
//                if(scannedData == null || scannedData.length() == 0) {
                    Toast.makeText(MainActivity.this, "No data to store!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                long date=System.currentTimeMillis();
                HistoryData historyData=new HistoryData(scannedData, date);
                DBHelper dbHelper= new DBHelper(MainActivity.this);
                Log.e("Activity", "ID: " + dbHelper.insertData(historyData));
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
    @Override
            public void onClick(View v) {
            Intent showIntent=new Intent(MainActivity.this,ShowDetailsActivity.class);
            startActivity(showIntent);
            }
        });
    }
}
