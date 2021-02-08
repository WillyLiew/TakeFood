package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class AddToCart extends AppCompatActivity {

    String name;
    String price;
    int delivery;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String user_email,temp;
    SharedPreferences myPref;
    ImageView History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        delivery = getIntent().getIntExtra("delivery",0);
        String rating = getIntent().getStringExtra("rating");
        String icon = getIntent().getStringExtra("icon");
        myPref= PreferenceManager.getDefaultSharedPreferences(this);
        user_email =myPref.getString(getString(R.string.userEmail), "NULL");

        TextView cartName = findViewById(R.id.cart_name);
        Button cartPrice = findViewById(R.id.cart_price);
        TextView cartRating = findViewById(R.id.cart_rating);
        TextView cartDelivery = findViewById(R.id.cart_delivery);
        ImageView cartIcon = findViewById(R.id.cart_icon);
        TextView desc=findViewById(R.id.desc);
        desc.setText(name+" is very delicious with the rating of "+rating+". Get it now with only RM "+price);

        cartName.setText(name);
        cartPrice.setText("Order now - RM"+price);
        cartRating.setText(rating);
        //cartDelivery.setText("RM "+delivery+".00");
        if (delivery==0){
            cartDelivery.setText("FREE Delivery");
        }else {
            cartDelivery.setText("RM " + Integer.toString(delivery) + ".00");
        }
        String iconUrl = "https://raw.githubusercontent.com/WillyLiew/DeliveryApp/master/"+icon;
        try{
            Glide.with(AddToCart.this).load(iconUrl).into(cartIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cartPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance(); //calling to rootNode from real time firebase
                reference = rootNode.getReference("user");

                FoodModel foodModel = new FoodModel(delivery, name, Float.parseFloat(price), user_email);

                String key = reference.push().getKey();
                reference.child(key).setValue(foodModel);
                Toast.makeText(AddToCart.this,name+" has been added into your cart",Toast.LENGTH_SHORT).show();
                    Intent CartActivity = new Intent(AddToCart.this, Cart.class);
                    CartActivity.putExtra("name",name);
                    CartActivity.putExtra("price",price);
                    CartActivity.putExtra("delivery",delivery);
                    startActivity(CartActivity);
            }
        });

        History = findViewById(R.id.history2);
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddToCart.this, "Redirecting to Order history page..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddToCart.this, OrderHistory.class));
            }
        });


    }



    }
