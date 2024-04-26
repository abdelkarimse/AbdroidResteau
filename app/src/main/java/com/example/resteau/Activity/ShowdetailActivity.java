package com.example.resteau.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resteau.R;

public class ShowdetailActivity extends AppCompatActivity {
    private TextView addtocartbtn;
    private TextView titletxt, feetxt, description, NumberOrderTxt;
    private ImageView plusbtn, minusbtn, picFooda;
    private FoodDomainse object;
    private int quantity = 1;
    private double totalPrice;
    private Mangmentscard MangementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);
        MangementCart=new Mangmentscard(this);
        initView();
        getBundle();
        updateTotalPrice();

        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity();
            }
        });

        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity();
            }
        });
    }

    private void getBundle() {
        object = (FoodDomainse) getIntent().getSerializableExtra("object");
        displayItemDetails();

    }

    private void initView() {
        addtocartbtn = findViewById(R.id.addtocart);
        titletxt = findViewById(R.id.titleTxt);
        feetxt = findViewById(R.id.pricetxt);
        description = findViewById(R.id.descriptiontxt);
        NumberOrderTxt = findViewById(R.id.NumberOrdertxt);
        plusbtn = findViewById(R.id.pludbtn);
        minusbtn = findViewById(R.id.minusbtn);
        picFooda = findViewById(R.id.picFood);
    }

    private void displayItemDetails() {
        titletxt.setText(object.getTitle());
        feetxt.setText(String.valueOf(object.getFee())+"d");
        description.setText(object.getDescription());
        int drawableResourceId = this.getResources().getIdentifier(object.getPic(), "drawable", this.getPackageName());
        if (drawableResourceId != 0) {
            picFooda.setImageResource(drawableResourceId);
        } else {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToCart() {
         object.setNumberCart(quantity);
        Toast.makeText(this, "Add to cart ", Toast.LENGTH_SHORT).show();
        MangementCart.addCard(object);

    }

    private void increaseQuantity() {
        if (quantity < 10) {
            quantity++;
            NumberOrderTxt.setText(String.valueOf(quantity));
            updateTotalPrice();
        } else {
            Toast.makeText(this, "Maximum quantity reached", Toast.LENGTH_SHORT).show();
        }
    }

    private void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
            NumberOrderTxt.setText(String.valueOf(quantity));
            updateTotalPrice();
        } else {
            Toast.makeText(this, "Minimum quantity reached", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotalPrice() {
        totalPrice = quantity * object.getFee();
    }
}
