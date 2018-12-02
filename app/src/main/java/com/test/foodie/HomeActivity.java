package com.test.foodie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private FoodViewAdapter adapter;

    private OnItemClickListener onItemClickListener;
    private FoodViewModel viewModel;
    private FoodDAO foodDAO;
    private boolean loaded;
    private TextView texLoading;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try {


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            progressBar=findViewById(R.id.pgrsbar);
            viewModel = ViewModelProviders.of(this).get(FoodViewModel.class);


            viewModel.getDBItemsData().observe(this, new Observer<List<DBModel>>() {
                @Override
                public void onChanged(@Nullable List<DBModel> dbModels) {
                    setAdapter(dbModels);

                }

            });

            foodDAO=viewModel.getFoodDAO();

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<DBModel> dbModels=viewModel.getDBCartData();
                    if(dbModels.size()>0) {
                        startActivity(new Intent(HomeActivity.this, CartViewActivity.class));
                    }else {
                        Snackbar.make(view,"Cart is Empty",Snackbar.LENGTH_LONG).show();
                    }
                }
            });


            onItemClickListener = new OnItemClickListener() {
                @Override
                public void onAddCartClick(DBModel fm, View v) {

                    TextView textView = (TextView) v.findViewById(R.id.quantity);
                    textView.setText(Integer.parseInt(textView.getText().toString()) + 1 + "");
                    String str=textView.getText().toString();
                    int i = Integer.parseInt(str);

                    if (i > 0) {
                        viewModel.updateInCart(true, fm);
                        viewModel.updateQuantity(str, fm);
                    }
                }

                @Override
                public void onRemoveCartClick(DBModel fm, View v) {

                    TextView textView = (TextView) v.findViewById(R.id.quantity);
                    int i = Integer.parseInt(textView.getText().toString()) - 1;
                    textView.setText(i <= 0 ? 0 + "" : i + "");
                    String str=textView.getText().toString();
                    if (i > 0) {
                        viewModel.updateQuantity(str,fm);

                    } else {
                        viewModel.updateQuantity("0",fm);
                        viewModel.updateInCart(false, fm);
                    }
                }
                @Override
                public void onDescriptionClick(DBModel dbModel, View v) {
                    Intent intent=new Intent(HomeActivity.this,DescriptionActivity.class);
                    intent.putExtra("dbModel",dbModel);
                    startActivity(intent);

                }
            };



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(List<DBModel> dbList) {

        if (dbList.size() > 0) {

            adapter = new FoodViewAdapter(HomeActivity.this, dbList, onItemClickListener);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);

        } else {
            viewModel.getItemsData().observe(this, new Observer<List<FoodModel>>() {
                @Override
                public void onChanged(@Nullable List<FoodModel> foodModels) {
                    new Async().execute(foodModels);

                }
            });
        }

    }

    private List<DBModel> saveInDB (List < FoodModel > foodModels) {

        List<DBModel> list=new ArrayList<>();
        for (FoodModel fm : foodModels) {

            DBModel dbModel = new DBModel(fm.getRating(), fm.getName(), fm.getPrice(), fm.getImgURL(), false, "0");
            list.add(dbModel);
            foodDAO.insert(dbModel);
        }
        return list;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class Async extends AsyncTask<List<FoodModel> ,Void,List<DBModel>>{


        @Override
        protected List<DBModel> doInBackground(List<FoodModel>... lists) {

            List<DBModel> list=saveInDB(lists[0]);
            return list;
        }

        @Override
        protected void onPostExecute(List<DBModel> dbModels) {
            super.onPostExecute(dbModels);

            adapter = new FoodViewAdapter(HomeActivity.this, dbModels, onItemClickListener);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);


        }
    }

}
