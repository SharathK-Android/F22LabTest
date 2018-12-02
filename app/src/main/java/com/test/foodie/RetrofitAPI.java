package com.test.foodie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

    String BASE_URL= "https://android-full-time-task.firebaseio.com";

    @GET("/data.json")
    Call<List<FoodModel>> getData();
}
