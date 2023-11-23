package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Cart;
import com.example.projectapp.ObjectClass.CartItem;
import com.example.projectapp.ObjectClass.Dish;
import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.Stores;
import com.example.projectapp.R;
import com.example.projectapp.ObjectClass.Order;
import com.example.projectapp.connectAPI.RequestCart;
import com.example.projectapp.connectAPI.RequestStore;
import com.example.projectapp.ui.Payment.PaymentActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.OrderHolder> {

    private List<Order> orders;
    private Context context;
    private DishItemAdapter dishItemAdapter;

    public ListOrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_cart, parent, false);
        return new OrderHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListOrderAdapter.OrderHolder holder, int position) {
        final Order order = orders.get(position);
        if(order == null){
            Notification notification = new Notification();
            notification.showAlertDialog(context, "Chưa có đơn hàng nào trong giở hàng.!!!");
            return;
        }
        holder.num_disher.setText(order.getCarts().size()+" món");
        holder.name.setText(order.getCarts().get(0).getStoreName()+"");
        Picasso.get().load(order.getCarts().get(0).getAvatar()).into(holder.image);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.rcv_dish.setLayoutManager(linearLayoutManager);
        List<Dish> list = new ArrayList<>();
        for(int i =0 ; i< order.getCarts().size(); i++){
            Dish dish = new Dish();
            dish.setName(order.getCarts().get(i).getProductName());
            dish.setQuantity(order.getCarts().get(i).getQuantity());
            dish.setId(order.getCarts().get(i).getId());
            dish.setAvatar(order.getCarts().get(i).getAvatar());
            dish.setPrice(order.getCarts().get(i).getPrice());
            dish.setDiscount(order.getCarts().get(i).getDiscount());
            list.add(dish);
        }
        dishItemAdapter = new DishItemAdapter(context, list);
        holder.rcv_dish.setAdapter(dishItemAdapter);
        holder.item_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToItem(order);
            }
        });
    }

    private void onClickGoToItem(Order order ) {
        Intent intent = new Intent(context, PaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_order", order);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(orders != null){
            return orders.size();
        }
        return 0;
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name, num_disher;
        private LinearLayout item_cart;
        private RecyclerView rcv_dish;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_order);
            num_disher = itemView.findViewById(R.id.tv_num_dish);
            item_cart = itemView.findViewById(R.id.item_order);
            image = itemView.findViewById(R.id.imageView30);
            rcv_dish  = itemView.findViewById(R.id.rcv_dish);
        }
    }
}
