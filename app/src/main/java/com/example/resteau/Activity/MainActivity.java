package com.example.resteau.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.resteau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPlat, recyclerViewFastFood, recyclerViewCafe;
    private FloatingActionButton cart;
    private LinearLayout home, out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPlat = findViewById(R.id.recyclerViewPlat);
        recyclerViewFastFood = findViewById(R.id.recyclerViewFastFood);
        recyclerViewCafe = findViewById(R.id.recyclerViewCafe);

        LinearLayoutManager layoutManagerPlat = new LinearLayoutManager(this);
        layoutManagerPlat.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewPlat.setLayoutManager(layoutManagerPlat);

        LinearLayoutManager layoutManagerFastFood = new LinearLayoutManager(this);
        layoutManagerFastFood.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFastFood.setLayoutManager(layoutManagerFastFood);

        LinearLayoutManager layoutManagerCafe = new LinearLayoutManager(this);
        layoutManagerCafe.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCafe.setLayoutManager(layoutManagerCafe);

        cart = findViewById(R.id.toCart);
        home = findViewById(R.id.Home);
        out = findViewById(R.id.out);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming MainActivity is your home activity
                // You may want to change this to your home activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        // Fetch data for Plat RecyclerView
        fetchData("https://YOUR_Host_Name/apiphp.php?get_plat_data", recyclerViewPlat);

        // Fetch data for Fast Food RecyclerView
        fetchData("https://YOUR_Host_Name/apiphp.php?get_fast_food_data", recyclerViewFastFood);

        // Fetch data for Cafe RecyclerView
        fetchData("https://YOUR_Host_Name/apiphp.php?get_cafe_data", recyclerViewCafe);
    }

    private void fetchData(String url, final RecyclerView recyclerView) {
        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                ArrayList<FoodDomainse> foodList = parseData(response);
                                if (foodList != null && !foodList.isEmpty()) {
                                    PoularAdapter adapter = new PoularAdapter(MainActivity.this, foodList);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    Log.e("AdapterError", "Adapter is null or empty");
                                }
                            } else {
                                Log.e("ResponseError", "Response is null or empty");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSONError", "Error parsing JSON: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        Log.e("NetworkError", "Error: " + error.getErrorDetail());
                    }
                });
    }

    private ArrayList<FoodDomainse> parseData(JSONArray response) throws JSONException {
        ArrayList<FoodDomainse> foodList = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            JSONObject foodObject = response.getJSONObject(i);
            int foodId = foodObject.getInt("id");
            String foodName = foodObject.getString("Name");
            double foodPrice = foodObject.getDouble("Price");
            String foodPic = foodObject.getString("Image");
            String foodCategory = foodObject.getString("category");
            String foodDescription = foodObject.getString("description");
            FoodDomainse food = new FoodDomainse(foodId,foodName, foodPrice, foodPic, foodCategory, foodDescription);
            foodList.add(food);
        }
        return foodList;
    }
}
