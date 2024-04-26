package com.example.resteau.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resteau.R;

import java.util.ArrayList;
public class PoularAdapter extends RecyclerView.Adapter<PoularAdapter.ViewHolder> {
    private final ArrayList<FoodDomainse> categoryFood;
    private Context context;

    public PoularAdapter(Context context, ArrayList<FoodDomainse> categoryFood) {
        this.context = context;
        this.categoryFood = categoryFood;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomainse foodItem = categoryFood.get(position);
        holder.title.setText(foodItem.getTitle());
        holder.fee.setText(String.valueOf(foodItem.getFee()));
        int drawableResourceId = context.getResources().getIdentifier(foodItem.getPic(), "drawable", context.getPackageName());
        if (drawableResourceId != 0) {
            holder.pic.setImageResource(drawableResourceId);
        } else {
            int defaultDrawableResourceId = context.getResources().getIdentifier("i1", "drawable", context.getPackageName());
            if (defaultDrawableResourceId != 0) {
                holder.pic.setImageResource(defaultDrawableResourceId);
            } else {
                holder.pic.setImageDrawable(null); // Setting blank for now
            }
        }


        holder.addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowdetailActivity.class);
            intent.putExtra("object", foodItem);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryFood.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;
        ImageView pic;
        Button addBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlea);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
