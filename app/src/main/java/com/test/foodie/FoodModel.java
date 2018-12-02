package com.test.foodie;

public class FoodModel {

    private String average_rating;
    private String image_url;
    private String item_name;
    private String item_price;

    public FoodModel(String average_rating, String image_url, String item_name, String item_price) {
        this.average_rating = average_rating;
        this.image_url = image_url;
        this.item_name = item_name;
        this.item_price = item_price;
    }

    public String getRating() {
        return average_rating;
    }


    public String getImgURL() {
        return image_url;
    }



    public String getName() {
        return item_name;
    }



    public String getPrice() {
        return item_price;
    }


}
