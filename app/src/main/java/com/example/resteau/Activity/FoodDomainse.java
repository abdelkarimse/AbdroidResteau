package com.example.resteau.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodDomainse  implements Serializable {
    private int id; // Add id field
    private static List<Integer> allIDs = new ArrayList<>();
    private String title;
    private String pic;
    private String description;
    private Double fee;
    private int numberCart;

    public FoodDomainse (int id, String title, double price, String pic, String category, String description) {
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = price;
        this.numberCart = 1;
        allIDs.add(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getNumberCart() {
        return numberCart;
    }

    public void setNumberCart(int numberCart) {
        this.numberCart = numberCart;
    }

    public double getTotalPrice() {
        return numberCart * fee;
    }
    public static List<Integer> getAllIDs() {
        return allIDs;
    }
}
