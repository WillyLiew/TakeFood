package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FoodCategory extends AppCompatActivity {
    CategoryAdapter categoryAdapter;
    OkHttpClient client;
    ArrayList<food_Item> food_items_cat = new ArrayList<food_Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        String type = getIntent().getStringExtra("type");
        TextView category_type=findViewById(R.id.Category_type);
        category_type.setText(type);

        RecyclerView categoryView=findViewById(R.id.categoryView);
        categoryView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter();
        categoryView.setAdapter(categoryAdapter);

        client = new OkHttpClient();
        String category = getIntent().getStringExtra("category");
        String url = "https://raw.githubusercontent.com/WillyLiew/DeliveryApp/master/"+category+".json";
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    food_items_cat.clear();
                    try{
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject item=jsonArray.getJSONObject(i);
                            String name = item.getString("name");
                            String icon = item.getString("icon");
                            String price = item.getString("price");
                            String rating = item.getString("rating");
                            int delivery = item.getInt("delivery");
                            food_Item foodItem = new food_Item(name,icon,price,rating,delivery);
                            food_items_cat.add(foodItem);
                            FoodCategory.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    categoryAdapter.addElements(food_items_cat);
                                }
                            });
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{
        ArrayList<food_Item> elements = new ArrayList<food_Item>();

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowView = getLayoutInflater().inflate(R.layout.category_food_item,parent,false);
            return new MyViewHolder(rowView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.fName.setText(elements.get(position).getName());
            holder.fRating.setText(elements.get(position).getRating());
            holder.fPrice.setText("RM " + elements.get(position).getPrice());
            int tempDelivery = elements.get(position).getDelivery();
            if (tempDelivery==0){
                holder.fDelivery.setText("FREE Delivery");
            }else {
                holder.fDelivery.setText("RM " + Integer.toString(tempDelivery) + ".00");
            }

            String iconUrl = "https://raw.githubusercontent.com/WillyLiew/DeliveryApp/master/"+elements.get(position).getIcon();
            try{
                Glide.with(FoodCategory.this).load(iconUrl).into(holder.fIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return elements.size();
        }

        public void addElements(ArrayList<food_Item> food_items){
            elements.clear();
            elements.addAll(food_items);
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView fName, fRating, fPrice, fDelivery;
            public ImageView fIcon;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                fIcon = itemView.findViewById(R.id.category_icon);
                fName = itemView.findViewById(R.id.category_name);
                fRating = itemView.findViewById(R.id.category_rating);
                fPrice =itemView.findViewById(R.id.category_price);
                fDelivery = itemView.findViewById(R.id.category_delivery);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                String name = elements.get(getAdapterPosition()).getName();
                String price = elements.get(getAdapterPosition()).getPrice();
                String rating = elements.get(getAdapterPosition()).getRating();
                String icon = elements.get(getAdapterPosition()).getIcon();
                int delivery = elements.get(getAdapterPosition()).getDelivery();
                Intent addtocart = new Intent(FoodCategory.this, AddToCart.class);
                addtocart.putExtra("name",name);
                addtocart.putExtra("price",price);
                addtocart.putExtra("rating",rating);
                addtocart.putExtra("delivery",delivery);
                addtocart.putExtra("icon",icon);
                startActivity(addtocart);


            }
        }
    }


}
