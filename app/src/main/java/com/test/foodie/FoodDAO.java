package com.test.foodie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FoodDAO {

    @Query("Select * From DBModel")
    LiveData<List<DBModel>> getAllData();

    @Query("Select * From DBModel WHERE inCart= :inCart")
    List<DBModel> getCartData(boolean inCart);

    @Insert
    void insert(DBModel... dbModels);

    @Delete
    void delete(DBModel... dbModels);

    @Query("UPDATE DBModel SET quantity=:quantity WHERE avgRating = :avgRating AND itemName = :itemName AND itemPrice = :itemPrice")
    void updateQuantity(String quantity,String avgRating,String itemName,String itemPrice);

    @Query("UPDATE DBModel SET inCart=:inCart WHERE avgRating = :avgRating AND itemName = :itemName AND itemPrice = :itemPrice")
    void updateInCart(boolean inCart,String avgRating,String itemName,String itemPrice);



}
