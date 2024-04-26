package com.example.resteau.Activity;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class Mangmentscard {
    private Context context;
    private TinyDB tinydb;

    public Mangmentscard(Context context) {
           this.context = context;
            this.tinydb = new TinyDB(context);
    }


    public void addCard(FoodDomainse item) {
        ArrayList<FoodDomainse> listFood = getCard();
        boolean existsAlready = false;
        int index = 0;

        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existsAlready = true;
                index = i;
                break;
            }
        }
        if (existsAlready) {
            listFood.get(index).setNumberCart(item.getNumberCart());
        } else {
            listFood.add(item);
        }
        tinydb.putListObject("cardList", listFood);
        Toast.makeText(context, "Added to your cart", Toast.LENGTH_SHORT).show();
    }

    // Method to retrieve the card list from the database
    public ArrayList<FoodDomainse> getCard() {
        return tinydb.getListObject("cardList");
    }
    public void plusNumberFood(ArrayList<FoodDomainse>ListFood,int posstion,ChangeNumberItemsListtenar changeNumberItemsListtenar){
    ListFood.get(posstion).setNumberCart(ListFood.get(posstion).getNumberCart()+1);
        tinydb.putListObject("cardList",ListFood);
        changeNumberItemsListtenar.onChange();
    }
    public void minusNumberFood(ArrayList<FoodDomainse> ListFood, int position, ChangeNumberItemsListtenar changeNumberItemsListtenar) {
        if (ListFood.get(position).getNumberCart() > 1) {
            ListFood.get(position).setNumberCart(ListFood.get(position).getNumberCart() - 1);
        } else {
            ListFood.remove(position);
        }
        tinydb.putListObject("cardList", ListFood);
        changeNumberItemsListtenar.onChange();
    }
    public Double getTotalFee() {
        ArrayList<FoodDomainse> foodlist = getCard();
        double fee = 0;
        for (FoodDomainse item : foodlist) {
            fee += item.getFee() * item.getNumberCart();
        }
        return fee;
    }

}
