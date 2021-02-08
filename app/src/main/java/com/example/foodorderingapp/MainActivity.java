package com.example.foodorderingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView textView;
    ArrayList<food_Item> food_items = new ArrayList<food_Item>();
    RequestQueue queue;
    ImageView History,logout;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textViewLocation2);
        checkLocationPermission();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logging Out..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, FirebaseLogin.class));
            }
        });


        History = findViewById(R.id.history);
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Redirecting to Order history page..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, OrderHistory.class));
            }
        });


        RecyclerView popularView = findViewById(R.id.popularView);
        popularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) );

        final PopularAdapter popularAdapter = new PopularAdapter();
        popularView.setAdapter(popularAdapter);
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        "https://raw.githubusercontent.com/WillyLiew/DeliveryApp/master/popular.json",
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                food_items.clear();
                                for(int i =0;i < response.length();i++) {
                                    try {
                                        JSONObject item = response.getJSONObject(i);
                                        String name = item.getString("name");
                                        String icon = item.getString("icon");
                                        String price = item.getString("price");
                                        String rating = item.getString("rating");
                                        int delivery = item.getInt("delivery");
                                        food_Item foodItem = new food_Item(name,icon,price,rating,delivery);
                                        food_items.add(foodItem);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(MainActivity.this,"JSON file format not compatible",Toast.LENGTH_LONG).show();
                                    }
                                }
                                popularAdapter.addElements(food_items);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,"Cannot retrieve from github",Toast.LENGTH_LONG).show();
                            }
                        }
                );

        queue.add(request);
    }

    private void getLocation() {
        locationManager= (LocationManager) this.getSystemService(LOCATION_SERVICE);
        checkLocationPermission();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,100,this);
        Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address=addresses.get(0).getAddressLine(0);
            textView.setText(""+address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }

    public void onClickCategory(View view) {
        Intent foodCategory = new Intent(MainActivity.this,FoodCategory.class);
        switch(view.getId()) {
            case R.id.Msia_category:
                foodCategory.putExtra("category","msia");
                foodCategory.putExtra("type","Malaysian Cuisine");
                break;
            case R.id.Western_category:
                foodCategory.putExtra("category","western");
                foodCategory.putExtra("type","Western Delights");
                break;
            case R.id.Asian_category:
                foodCategory.putExtra("category","asian");
                foodCategory.putExtra("type","Asian Flavours");
                break;
            case R.id.Dessert_category:
                foodCategory.putExtra("category","beverage");
                foodCategory.putExtra("type","Desserts & Beverages");
                break;

        }
        startActivity(foodCategory);
    }

    public void onClickCart(View view) {
        Intent cartActivity = new Intent(MainActivity.this,Cart.class);
        startActivity(cartActivity);
    }

    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address=addresses.get(0).getAddressLine(0);
            textView.setText(""+address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public void onProviderEnabled(String provider) {
    Log.i("Location","Provider enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
    Log.i("Location","Provider Disabled");
    }

    class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder>{
        ArrayList<food_Item> elements = new ArrayList<food_Item>();

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View popularView = getLayoutInflater().inflate(R.layout.food_item,parent,false);
            return new MyViewHolder(popularView);
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
            final LruCache<String, Bitmap> cache =new LruCache<String, Bitmap>(20);
            
            holder.fIcon.setImageUrl(iconUrl, new ImageLoader(queue, new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            }));
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
            public NetworkImageView fIcon;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                fIcon = itemView.findViewById(R.id.popular_icon);
                fName = itemView.findViewById(R.id.popular_name);
                fRating = itemView.findViewById(R.id.popular_rating);
                fPrice =itemView.findViewById(R.id.popular_price);
                fDelivery = itemView.findViewById(R.id.popular_delivery);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                String name = elements.get(getAdapterPosition()).getName();
                String price = elements.get(getAdapterPosition()).getPrice();
                String icon = elements.get(getAdapterPosition()).getIcon();
                String rating = elements.get(getAdapterPosition()).getRating();
                int delivery = elements.get(getAdapterPosition()).getDelivery();
                Intent addtocart = new Intent(MainActivity.this, AddToCart.class);
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
