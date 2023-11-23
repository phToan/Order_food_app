package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.R;
import com.example.projectapp.ui.Home.FoodActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EndowFoodAdapter extends RecyclerView.Adapter<EndowFoodAdapter.FoodViewHolder>{

    private List<Product> products;

    private Context context;

    public EndowFoodAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> listFood){
        this.products = listFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EndowFoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_endow, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EndowFoodAdapter.FoodViewHolder holder, int position) {
        final Product product = products.get(position);
        if(product == null) {
            return;
        }
        Picasso.get().load(product.getAvatarUrl()).into(holder.img);
        holder.name.setText(product.getProductName());
        holder.endow.setText("ưu đãi đến "+ product.getDiscount()+"%");
        holder.price.setText(product.getPrice()+"đ");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFoodActivity(product);
            }
        });
    }

    private void goToFoodActivity(Product product) {
        Intent intent = new Intent(context, FoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_food", product);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView endow;
        private TextView name, price;
        private CardView cardView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_food_endow);
            endow = itemView.findViewById(R.id.tv_food_endow);
            name = itemView.findViewById(R.id.tv_name_food_endow);
            price = itemView.findViewById(R.id.tv_price_food_endow);
            cardView = itemView.findViewById(R.id.layout_item_food);
        }
    }

}
