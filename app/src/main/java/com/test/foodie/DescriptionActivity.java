package com.test.foodie;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DescriptionActivity extends AppCompatActivity {

     private static FoodViewAdapter foodViewAdapter;
     ImageView foodImg,addCart,removeCart,descImg;
     TextView txtPrice,txtName,txtQuantity;
     RatingBar txtRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        final FoodViewModel viewModel=ViewModelProviders.of(this).get(FoodViewModel.class);

        final DBModel dbModel= (DBModel) getIntent().getSerializableExtra("dbModel");

        foodImg=findViewById(R.id.food_img);
        txtPrice=findViewById(R.id.item_price);
        txtRating=findViewById(R.id.avg_rating);
        txtName=findViewById(R.id.item_name);
        addCart=findViewById(R.id.add_cart);
        removeCart=findViewById(R.id.remove_cart);
        txtQuantity=findViewById(R.id.quantity);

        Glide.with(this)
                .load(dbModel.getImageURL())
                .into(foodImg);

        txtName.setText(dbModel.getItemName());
        txtPrice.setText("₹"+dbModel.getItemPrice());
        txtRating.setRating(Float.parseFloat(dbModel.getAvgRating()));
        txtQuantity.setText(dbModel.getQuantity());

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtQuantity.setText(Integer.parseInt(txtQuantity.getText().toString()) + 1 + "");
                String str=txtQuantity.getText().toString();
                int i = Integer.parseInt(str);

                if (i > 0) {
                    viewModel.updateInCart(true, dbModel);
                    viewModel.updateQuantity(str, dbModel);

                }
            }
        });

        removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = Integer.parseInt(txtQuantity.getText().toString()) - 1;
                txtQuantity.setText(i <= 0 ? 0 + "" : i + "");
                String str=txtQuantity.getText().toString();
                if (i > 0) {
                    viewModel.updateQuantity(str, dbModel);

                } else {
                    viewModel.updateQuantity("0",dbModel);
                    viewModel.updateInCart(false, dbModel);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
