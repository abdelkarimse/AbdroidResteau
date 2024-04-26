package com.example.resteau.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resteau.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loading_food);

        // Simulate some operation that takes time (e.g., loading data from a server)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity or perform any other action
                // For example, you can start your main activity after loading is complete
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it when pressing back button
            }
        }, 3000); // 3000 milliseconds (3 seconds) delay for demonstration
    }
}
