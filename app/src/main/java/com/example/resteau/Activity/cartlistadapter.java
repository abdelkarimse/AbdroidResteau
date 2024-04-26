package com.example.resteau.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resteau.R;

import java.util.ArrayList;

public class cartlistadapter extends RecyclerView.Adapter<cartlistadapter.ViewHolder> {
    private ArrayList<FoodDomainse> foodItems;
    private Mangmentscard mangmentscard;
    private ChangeNumberItemsListtenar listener;

    public cartlistadapter(ArrayList<FoodDomainse> cartItems, Mangmentscard manga, ChangeNumberItemsListtenar listener) {
        this.foodItems = cartItems;
        this.mangmentscard = manga;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholdercart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDomainse item = foodItems.get(position);
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.feeEachItem.setText(String.valueOf(item.getFee()));
            holder.quantity.setText(String.valueOf(item.getNumberCart()));
            holder.totalEachItem.setText(String.valueOf(item.getTotalPrice()*100/100));
            int drawableResourceId = holder.itemView.getContext().getResources()
                    .getIdentifier(item.getPic(), "drawable", holder.itemView.getContext().getPackageName());
            holder.pic.setImageResource(drawableResourceId);

            holder.plusBtn.setOnClickListener(v -> {
               mangmentscard.plusNumberFood(foodItems,position,new ChangeNumberItemsListtenar(){

                   @Override
                   public void onChange() {
                       notifyDataSetChanged();
                       listener.onChange();
                   }
               });
            });

            holder.minusBtn.setOnClickListener(v -> {
                mangmentscard.minusNumberFood(foodItems,position,new ChangeNumberItemsListtenar(){

                    @Override
                    public void onChange() {
                        notifyDataSetChanged();
                        listener.onChange();
                    }
                });
            });
        }
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, quantity, totalEachItem;
        ImageView pic, plusBtn, minusBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlecarditem);
            feeEachItem = itemView.findViewById(R.id.feeitemcart);
            quantity = itemView.findViewById(R.id.quantityCart);
            plusBtn = itemView.findViewById(R.id.plusCart);
            minusBtn = itemView.findViewById(R.id.minusCart);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.TotalEchItem);
        }
    }
}
