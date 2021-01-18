package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    //ArrayList<cart_Item> cartItems = new ArrayList<cart_Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        TextView tv = findViewById(R.id.cart_tv);
        //String name = getIntent().getStringExtra("name");
        //String price = getIntent().getStringExtra("price");
        //int delivery = getIntent().getIntExtra("delivery",0);
        //cart_Item cartItem = new cart_Item(name,Double.parseDouble(price),delivery);
        //cartItems.add(cartItem);
        tv.setText("Hello world");



    }

}
