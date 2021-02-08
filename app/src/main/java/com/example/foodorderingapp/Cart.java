package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart extends AppCompatActivity {

    String name;
    String price;
    ImageView History;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        String icon = getIntent().getStringExtra("icon");

        TextView caname = findViewById(R.id.ca_name);
        Button PlaceOrder = findViewById(R.id.placeorder);
        TextView cacost3 = findViewById(R.id.ca_cost3);
        TextView cacost2 = findViewById(R.id.ca_cost2);


        cacost2.setText("RM \t" + price);
        cacost3.setText("RM \t" + price);
        caname.setText(name);

        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //calling to rootNode from real time firebase
                DatabaseReference reference = rootNode.getReference("user");
                Toast.makeText(Cart.this,name+"Ready to pay",Toast.LENGTH_SHORT).show();
                Intent CartActivity = new Intent(Cart.this, OrderHistory.class);
                startActivity(new Intent(Cart.this, OrderHistory.class));
            }
        });

        History = findViewById(R.id.history3);
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cart.this, "Redirecting to Order history page..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Cart.this, OrderHistory.class));
            }
        });
    }

    public void cartFood(View view) {
        Intent OrderHisotry = new Intent(Cart.this, OrderHistory.class);
        OrderHisotry.putExtra("name",name);
        OrderHisotry.putExtra("price",price);
        startActivity(OrderHisotry);

        //ArrayList<cart_Item> cartItems = new ArrayList<cart_Item>();

        //String name = getIntent().getStringExtra("name");
        //String price = getIntent().getStringExtra("price");
        //int delivery = getIntent().getIntExtra("delivery",0);
        //cart_Item cartItem = new cart_Item(name,Double.parseDouble(price),delivery);
        //cartItems.add(cartItem);



    }

}
