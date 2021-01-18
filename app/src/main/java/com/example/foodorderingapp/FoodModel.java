package com.example.foodorderingapp;

public class FoodModel {
    private int delivery;
    private String foodName;
    private float price;
    private String email;

    //parameterised constructor - called when new food comes into view


    public FoodModel(int delivery, String foodName, float price, String email) {
        this.delivery = delivery;
        this.foodName = foodName;
        this.price = price;
        this.email = email;
    }

    //default constructor
    public FoodModel() {
    }

    //toString method for displaying

    @Override
    public String toString() {
        return "FoodModel{" +
                "delivery=" + delivery +
                ", foodName='" + foodName + '\'' +
                ", price=" + price +
                ", email='" + email + '\'' +
                '}';
    }




    //getters and setters for quantity, name, price

    public int getQty() {
        return delivery;
    }

    public void setQty(int delivery) {
        this.delivery = delivery;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
