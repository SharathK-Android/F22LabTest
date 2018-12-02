package com.test.foodie;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private LiveData<List<DBModel>> dbData;
    private static FoodDAO foodDAO;
    private List<DBModel> listCartData;


    public Repository(Application application) {
      AppDatabase appDatabase=AppDatabase.getDatabase(application);
      foodDAO=appDatabase.foodDAO();
      dbData=foodDAO.getAllData();
    }

    public FoodDAO getFoodDAO(){

        return foodDAO;
    }

    public LiveData<List<DBModel>> getAlldata(){

        return dbData;
    }
    public List<DBModel> getDBCartData(){

            listCartData = new ArrayList<>();
            listCartData= foodDAO.getCartData(true);
        return listCartData;
    }

    public void updateInCart(boolean bool, DBModel db) {
        new Task1(db).execute(bool);
    }


    public void updateQuantity(String str, DBModel db) {
        new Task(db).execute(str);
    }

    private static class Task extends AsyncTask<String ,Void,Void>{
        private DBModel dbModel;
        public Task(DBModel dbModel) {
            this.dbModel=dbModel;
        }

        @Override
        protected Void doInBackground(String... strings) {

            foodDAO.updateQuantity(strings[0],dbModel.getAvgRating(),dbModel.getItemName(),dbModel.getItemPrice());

            return null;
        }
    }

    private static class Task1 extends AsyncTask<Boolean ,Void,Void>{
        private DBModel dbModel;
        public Task1(DBModel dbModel) {
            this.dbModel=dbModel;
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            foodDAO.updateInCart(booleans[0],dbModel.getAvgRating(),dbModel.getItemName(),dbModel.getItemPrice());
            return null;
        }
    }
}
