package com.example.resteau.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.resteau.R;

public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout startbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introactivity);
        startbtn = findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the LoadingActivity
                startActivity(new Intent(IntroActivity.this, LoadingActivity.class));
            }
        });
    }
}
