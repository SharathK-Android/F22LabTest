package com.test.foodie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;

import java.io.Serializable;

@Entity
public class DBModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "avgRating")
    private String avgRating;

    @ColumnInfo(name = "itemName")
    private String itemName;

    @ColumnInfo(name = "itemPrice")
    private String itemPrice;

    @ColumnInfo(name = "imageURL")
    private String imageURL;

    @ColumnInfo(name = "inCart")
    private boolean inCart;

    @ColumnInfo(name = "quantity")
    private String quantity;

    public DBModel(String avgRating, String itemName, String itemPrice, String imageURL, boolean inCart,String quantity) {
        this.avgRating = avgRating;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageURL = imageURL;
        this.inCart = inCart;
        this.quantity=quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }
}
