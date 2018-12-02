package com.test.foodie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FoodViewAdapter extends RecyclerView.Adapter<FoodViewAdapter.FoodViewHolder>  {
    Context context;
    List<DBModel>dataList;
    OnItemClickListener onItemClickListener;

    public FoodViewAdapter(Context context, List<DBModel> dataList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, viewGroup, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {

        Log.d("arraysize",dataList.size()+"");

        DBModel data = dataList.get(i);

        Glide.with(context)
                .load(data.getImageURL())
                .into(foodViewHolder.foodImg);

        foodViewHolder.txtName.setText(data.getItemName());
        foodViewHolder.txtPrice.setText("₹"+data.getItemPrice());
        foodViewHolder.txtRating.setRating(Float.parseFloat(data.getAvgRating()));
        foodViewHolder.txtQuantity.setText(data.getQuantity());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView foodImg,addCart,removeCart,descImg;
        TextView txtPrice,txtName,txtQuantity;
        RatingBar txtRating;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImg=itemView.findViewById(R.id.food_img);
            txtPrice=itemView.findViewById(R.id.item_price);
            txtRating=itemView.findViewById(R.id.avg_rating);
            txtName=itemView.findViewById(R.id.item_name);
            addCart=itemView.findViewById(R.id.add_cart);
            removeCart=itemView.findViewById(R.id.remove_cart);
            descImg=itemView.findViewById(R.id.img_desc);
            txtQuantity=itemView.findViewById(R.id.quantity);

            addCart.setOnClickListener(this);
            removeCart.setOnClickListener(this);
            descImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            View view= (View) v.getParent();
            if(v.getId()==R.id.add_cart){
                onItemClickListener.onAddCartClick(dataList.get(getAdapterPosition()),view);
            }else if(v.getId()==R.id.remove_cart){
                onItemClickListener.onRemoveCartClick(dataList.get(getAdapterPosition()),view);
            }else if(v.getId()==R.id.img_desc){

                onItemClickListener.onDescriptionClick(dataList.get(getAdapterPosition()),view);

            }
        }
    }
}
