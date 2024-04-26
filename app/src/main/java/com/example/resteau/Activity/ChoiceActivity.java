package com.example.resteau.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resteau.R;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Button radioGroup = findViewById(R.id.button_continue);
        RadioButton deliveryButton = findViewById(R.id.radio_delivery);
        RadioButton pickupButton = findViewById(R.id.radio_pickup);

        // Retrieve totalPrice from the intent
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        radioGroup.setOnClickListener((v) -> {
            if (deliveryButton.isChecked()) {
                // Pass the totalPrice to DeliveryActivity
                Intent intent = new Intent(ChoiceActivity.this, DeliveryActivity.class);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            } else if (pickupButton.isChecked()) {
                // Pass the totalPrice to QrcodeActivity
                Intent intent = new Intent(ChoiceActivity.this, QrcodeActivity.class);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });
    }
}
