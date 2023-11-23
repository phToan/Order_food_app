package com.example.projectapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Cart;
import com.example.projectapp.ObjectClass.DishCart;
import com.example.projectapp.R;
import com.example.projectapp.ui.Payment.PaymentActivity;

import java.text.DecimalFormat;
import java.util.List;

public class DishCartAdapter extends RecyclerView.Adapter<DishCartAdapter.DishCartViewHolder> {

    private List<Cart> carts;
    private Context context;

    public DishCartAdapter(List<Cart> dishCartList, Context context) {
        this.carts = dishCartList;
        this.context = context;
    }

    @NonNull
    @Override
    public DishCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_cart, parent, false);
        return new DishCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishCartAdapter.DishCartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Cart cart = carts.get(position);
        if(cart == null) {
            return;
        }
        double price = Double.parseDouble(cart.getPrice());
        int priceInt = (int) price;
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String formattedNumber = decimalFormat.format(priceInt);
        holder.name.setText(cart.getProductName());
        holder.price.setText(formattedNumber);
        holder.count.setText(cart.getQuantity()+"x");
    }

    @Override
    public int getItemCount() {
        if(carts != null){
            return carts.size();
        }
        return 0;
    }

    public class DishCartViewHolder extends RecyclerView.ViewHolder {
        private TextView name, price, count, change;
        public DishCartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView13);
            price = itemView.findViewById(R.id.textView41);
            count = itemView.findViewById(R.id.textView17);
        }
    }
}
