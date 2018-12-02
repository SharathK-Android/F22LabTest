package com.test.foodie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder>  {
    private Context context;
    private List<DBModel>dataList;
    private double total_price;
    private TextView grandTotal;

    public CartViewAdapter(Context context, List<DBModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.grandTotal=grandTotal;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.cartlist_layout, viewGroup, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {

        Log.d("arraysize",dataList.size()+"");

        DBModel data = dataList.get(i);



        cartViewHolder.txtName.setText(data.getItemName());
        cartViewHolder.txtPrice.setText("₹"+data.getItemPrice());
        total_price=Double.parseDouble(data.getItemPrice())*Integer.parseInt(data.getQuantity());
        cartViewHolder.txtTtlPrice.setText("₹"+String.valueOf(total_price));
        cartViewHolder.txtQuantity.setText(" x"+data.getQuantity());


    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }




    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice,txtQuantity,txtName,txtTtlPrice;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice=itemView.findViewById(R.id.price);
            txtQuantity=itemView.findViewById(R.id.txt_quantity);
            txtName=itemView.findViewById(R.id.name);
            txtTtlPrice=itemView.findViewById(R.id.ttl_price);


        }

    }
}
