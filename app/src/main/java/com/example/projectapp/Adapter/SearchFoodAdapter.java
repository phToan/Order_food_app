package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.R;
import com.example.projectapp.ui.Home.FoodActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchFoodAdapter extends RecyclerView.Adapter<SearchFoodAdapter.SearchFoodHolder> {

    private List<Product> products;

    private Context context;

    public SearchFoodAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public SearchFoodAdapter.SearchFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_search, parent, false);
        return new SearchFoodAdapter.SearchFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFoodAdapter.SearchFoodHolder holder, int position) {
        Product product = products.get(position);
        Picasso.get().load(product.getAvatarUrl()).into(holder.image);
        holder.tv_price.setText((int) product.getPrice() + "đ -- " +product.getRate());
        holder.tv_name.setText(product.getProductName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, FoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj_food", product);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
        }
        return 0;
    }

    public class SearchFoodHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price;
        private ImageView image;
        private LinearLayout layout;
        public SearchFoodHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.textView33);
            tv_price = itemView.findViewById(R.id.textView34);
            image = itemView.findViewById(R.id.imageView32);
            layout = itemView.findViewById(R.id.layout_search);
        }
    }
}
