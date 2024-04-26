package com.example.resteau.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.resteau.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class QrcodeActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                initQRCodeScanner();
            }
        } else {
            initQRCodeScanner();
        }
    }

    private void initQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Scan a QR code");
        integrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQRCodeScanner();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                String scannedData = result.getContents();
                Toast.makeText(this, "Scanned: " + scannedData, Toast.LENGTH_LONG).show();
                sendToPhp(scannedData);
            }
        }
    }
    private String listToString(List<Integer> list, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(list.get(i));
        }
        return sb.toString();}
    private void sendToPhp(String d) {
        final double price = getIntent().getDoubleExtra("totalPrice", 0);
        List<Integer> data = FoodDomainse.getAllIDs();
        String idListString = listToString(data, ",");
        String url = "https://YOUR_Host_Name/achat.php?place";

        AndroidNetworking.post(url)
                .addBodyParameter("numerot", d)
                .addBodyParameter("idList", idListString)
                .addBodyParameter("price", String.valueOf(price))
                .addBodyParameter("tables", String.valueOf(d))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        Toast.makeText(QrcodeActivity.this, " 15min ", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(QrcodeActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1 * 60 * 1000);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Error", "Error occurred: " + anError.getErrorDetail());
                        Toast.makeText(QrcodeActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
