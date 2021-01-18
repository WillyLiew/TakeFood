package com.example.foodorderingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String FOOD_TABLE = "FOOD_TABLE";
    public static final String COLUMN_FOOD_NAME = "FOOD_NAME";
    public static final String COLUMN_FOOD_QTY = "FOOD_QTY";
    public static final String COLUMN_FOOD_PRICE = "FOOD_PRICE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Order History.db", null, 1);
    }

    //called the first time database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + FOOD_TABLE + " (" + COLUMN_FOOD_NAME + " TEXT, " + COLUMN_FOOD_QTY + " INT, " + COLUMN_FOOD_PRICE + ")";

        db.execSQL(createTableStatement);

    }

    //for upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addOne(FoodModel foodModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FOOD_NAME, foodModel.getFoodName());
        cv.put(COLUMN_FOOD_QTY, foodModel.getQty());
        cv.put(COLUMN_FOOD_PRICE, foodModel.getPrice());

        long insert = db.insert(FOOD_TABLE, null, cv);
        if(insert == -1 ){
            return false;

        }else{
            return true;
        }


    }
/*
    public List <FoodModel> getEveryone(){
        List<FoodModel> returnList = new ArrayList<>();
        //retrieve data from database

        String queryString = "SELECT * FROM "+FOOD_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            //loop through cursor (result set) and create new food result for each row, then add to return list

            do{
                String foodName = cursor.getString(0);
                int foodQty = cursor.getInt(1);
                float foodPrice = cursor.getFloat(2);
                FoodModel newFoodModel = new FoodModel(foodQty,foodName, foodPrice, email);
                returnList.add(newFoodModel);

            }while(cursor.moveToNext());

        }else{
        //fail do nothing
        }

        cursor.close();
        db.close();
        return returnList;
    }
    */
}
