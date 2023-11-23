package com.example.projectapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Cart;
import com.example.projectapp.ObjectClass.DishCart;
import com.example.projectapp.ObjectClass.OrderDetail;
import com.example.projectapp.R;
import com.example.projectapp.ui.Payment.PaymentActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderHolder> {

    private List<OrderDetail> orderDetails;
    private Context context;

    public FoodOrderAdapter(List<OrderDetail> orderDetails, Context context) {
        this.orderDetails = orderDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_cart_order, parent, false);
        return new FoodOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderAdapter.FoodOrderHolder holder, @SuppressLint("RecyclerView") int position) {
        final OrderDetail order = orderDetails.get(position);
        if(order == null) {
            return;
        }
        double price = Double.parseDouble(order.getPrice());
        int priceInt = (int) price;
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String formattedNumber = decimalFormat.format(priceInt);
        holder.name.setText(order.getProductName());
        holder.price.setText(formattedNumber);
        holder.count.setText(order.getQuantity()+"x");
        Picasso.get().load(order.getAvatar()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        if(orderDetails != null){
            return orderDetails.size();
        }
        return 0;
    }

    public class FoodOrderHolder extends RecyclerView.ViewHolder {
        private TextView name, price, count;
        private ImageView image;
        public FoodOrderHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView131);
            price = itemView.findViewById(R.id.textView411);
            count = itemView.findViewById(R.id.textView171);
            image = itemView.findViewById(R.id.imageView31);
        }
    }
}
