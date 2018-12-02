package com.test.foodie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodViewModel extends AndroidViewModel {

    private MutableLiveData<List<FoodModel>> foodItemList;
    private LiveData<List<DBModel>> foodDBItemList;
    private List<DBModel> listCartData;
    private MutableLiveData<String> grandTotal=null;
    private Repository repository;
    private FoodDAO foodDAO;

    public FoodViewModel(Application application) {
        super(application);
        repository=new Repository(application);
        foodDAO=repository.getFoodDAO();
        foodDBItemList=repository.getAlldata();

    }



    public FoodDAO getFoodDAO(){

        return foodDAO;
    }

    public MutableLiveData<List<FoodModel>> getItemsData(){


        if (foodItemList == null) {
            foodItemList = new MutableLiveData<>();


            loadData();
        }
        return foodItemList;
    }

    public LiveData<List<DBModel>> getDBItemsData(){

        return foodDBItemList;
    }

    public List<DBModel> getDBCartData(){

        return repository.getDBCartData();
    }

    public LiveData<String> getGrandTotal(List<DBModel> dbModels){

        if(grandTotal==null) {
            grandTotal=new MutableLiveData<String>();
            double total=30;
            for (DBModel db : dbModels) {

                total=total+ (Double.parseDouble(db.getItemPrice())*Integer.parseInt(db.getQuantity()));
            }
            grandTotal.setValue(String.valueOf(total));

        }

        return grandTotal;
    }
    public void setGrandTotal(String total){

        grandTotal.setValue(total);
    }


    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RetrofitAPI.BASE_URL)
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<FoodModel>> call = retrofitAPI.getData();

        call.enqueue(new Callback<List<FoodModel>>() {
            @Override
            public void onResponse(Call<List<FoodModel>> call, Response<List<FoodModel>> response) {

                foodItemList.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<FoodModel>> call, Throwable t) {
                Log.d("asddff","onFailure");

            }
        });

    }


    public void updateInCart(boolean bool, DBModel db) {
        repository.updateInCart(bool,db);

    }

    public void updateQuantity(String str, DBModel db) {

        repository.updateQuantity(str,db);

    }
}
