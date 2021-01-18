package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        listView= findViewById(R.id.ListView);

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    FoodModel foodModel = dataSnapshot.getValue(FoodModel.class);
                    String txt = "Customer Email :"+ foodModel.getEmail() + "\nFood Order :" + foodModel.getFoodName() +"\nQuantity :" + foodModel.getQty() + "\nPrice :" + foodModel.getPrice();

                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*
        DatabaseHelper databaseHelper = new DatabaseHelper(OrderHistory.this);
        List<FoodModel> everyone = databaseHelper.getEveryone();

        ArrayAdapter foodArrayAdapter = new ArrayAdapter<FoodModel>(OrderHistory.this,  android.R.layout.simple_list_item_1, everyone);
        lv_history.setAdapter(foodArrayAdapter);
        //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();

   */
    }



}