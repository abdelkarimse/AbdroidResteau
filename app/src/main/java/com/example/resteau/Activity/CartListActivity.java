package com.example.resteau.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resteau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity  {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private Mangmentscard mangmentscard;
    private TextView totalFeeTextView, taxTextView, totalAfterTaxTextView, emptyTextView,btnpassfacture;
    private ScrollView scrollView;
    private FloatingActionButton cart;
    private LinearLayout home,out;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mangmentscard = new Mangmentscard(this);
        initView();
        initList();
        Calculcard();
        cart=findViewById(R.id.toCartCart);
        home=findViewById(R.id.Homecart);
        out=findViewById(R.id.outcat);
        btnpassfacture=findViewById(R.id.passercart);

        btnpassfacture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice = mangmentscard.getTotalFee();
                Intent intent = new Intent(CartListActivity.this, ChoiceActivity.class);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CartListActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerViewLis);
        totalFeeTextView = findViewById(R.id.Taxtotaltxt);
        taxTextView = findViewById(R.id.Taxtxt);
        totalAfterTaxTextView = findViewById(R.id.Taxtotaltxt);
        emptyTextView = findViewById(R.id.empty);
        scrollView = findViewById(R.id.scrollView3);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        ArrayList<FoodDomainse> cartItems = mangmentscard.getCard();
        adapter = new cartlistadapter(cartItems, mangmentscard, new ChangeNumberItemsListtenar() {
            @Override
            public void onChange() {
                Calculcard();
            }
        });
        recyclerViewList.setAdapter(adapter);

        if (cartItems != null && !cartItems.isEmpty()) {
            emptyTextView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

        } else {
            emptyTextView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }
    private void Calculcard(){
        double totalPrice =mangmentscard.getTotalFee();
        double tax =totalPrice+totalPrice*0.02 ;
        totalFeeTextView.setText(String.format("$%.2f", totalPrice));
        taxTextView.setText(String.format("$%.2f", tax));
        totalAfterTaxTextView.setText(String.format("$%.2f", tax));
    }

}
