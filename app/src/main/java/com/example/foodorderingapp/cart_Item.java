package com.example.foodorderingapp;

public class cart_Item {
    private String name;
    private double price;
    private int delivery;

    public cart_Item(String name, double price, int delivery) {
        this.name = name;
        this.price = price;
        this.delivery = delivery;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDelivery() {
        return delivery;
    }
}
