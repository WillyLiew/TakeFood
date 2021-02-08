package com.example.foodorderingapp;

public class food_Item {
    private String name, icon, price, rating;
    private int delivery;

    public food_Item(String name, String icon, String price, String rating, int delivery) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.rating = rating;
        this.delivery = delivery;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public int getDelivery() {
        return delivery;
    }
}
