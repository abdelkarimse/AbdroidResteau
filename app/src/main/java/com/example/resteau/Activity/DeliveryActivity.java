package com.example.resteau.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.resteau.R;

import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    EditText editTextName, editTextAddress, editTextPhone;
    Button buttonBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonBuy = findViewById(R.id.buttonBuy);

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToPhp();
            }
        });
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
    private void sendToPhp() {
        final String name = editTextName.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final double price = getIntent().getDoubleExtra("totalPrice", 0); // Assuming the key is "totalPrice"

        String url = "https://YOUR_Host_Name/achat.php?delivery";
        List<Integer> data = FoodDomainse.getAllIDs();
        String idListString = listToString(data, ",");
        AndroidNetworking.post(url)
                .addBodyParameter("name", name)
                .addBodyParameter("address", address)
                .addBodyParameter("phone", phone)
                .addBodyParameter("price", String.valueOf(price))
                .addBodyParameter("idList", String.valueOf(idListString))

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        Toast.makeText(DeliveryActivity.this, "Data sent successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DeliveryActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        // Handle errors
                        Log.e("Error", "Error occurred: " + anError.getErrorDetail());
                        Toast.makeText(DeliveryActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
