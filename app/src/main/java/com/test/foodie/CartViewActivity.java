package com.test.foodie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class CartViewActivity extends AppCompatActivity {

    private List<DBModel> cartList;
    private RecyclerView recyclerView;
    private CartViewAdapter cartViewAdapter;
    private TextView delCharge,grandTotal;
    private Button btnApply;
    private EditText edtCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        try {


            final FoodViewModel viewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
            cartList = viewModel.getDBCartData();

            recyclerView = findViewById(R.id.cartlist);
            edtCoupon = findViewById(R.id.edtCoupon);
            delCharge = findViewById(R.id.del_charge);
            grandTotal = findViewById(R.id.grand_total);
            btnApply = findViewById(R.id.btnApply);

            cartViewAdapter = new CartViewAdapter(this, cartList);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(cartViewAdapter);

            viewModel.getGrandTotal(cartList).observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    grandTotal.setText(s);
                }
            });

            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(edtCoupon.getText())) {
                        String coupon = edtCoupon.getText().toString();
                        double total = Double.parseDouble(grandTotal.getText().toString());

                        if (coupon.equalsIgnoreCase("FREEDEL")) {
                            delCharge.setVisibility(View.GONE);
                            edtCoupon.setEnabled(false);
                            btnApply.setEnabled(false);
                            viewModel.setGrandTotal(String.valueOf(total-30));

                        } else if (coupon.equalsIgnoreCase("F22LABS")) {
                            if (total > 400) {
                                edtCoupon.setEnabled(false);
                                btnApply.setEnabled(false);
                                float ttl= (float) (total*0.80);
                                viewModel.setGrandTotal(String.valueOf(ttl));

                            } else {

                                Snackbar.make(v, "Invalid Coupon", Snackbar.LENGTH_LONG).show();
                            }

                        } else {
                            Snackbar.make(v, "Invalid Coupon", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
