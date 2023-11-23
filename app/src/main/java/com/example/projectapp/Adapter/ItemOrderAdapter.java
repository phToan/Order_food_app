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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.OrderResponse;
import com.example.projectapp.R;
import com.example.projectapp.ui.DrawerMenu.DetailOrderActivity;
import com.example.projectapp.ObjectClass.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemOrderAdapter extends RecyclerView.Adapter<ItemOrderAdapter.ItemOrderHolder> {
    private List<OrderResponse> orderResponses;
    private Context context;

    public ItemOrderAdapter(List<OrderResponse> orderResponses, Context context) {
        this.orderResponses = orderResponses;
        this.context = context;
    }
    @NonNull
    @Override
    public ItemOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_cart, parent, false);
        return new ItemOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOrderAdapter.ItemOrderHolder holder, int position) {
        final OrderResponse order = orderResponses.get(position);
        if(order == null){
            return;
        }
//        holder.image.setImageResource(order.getAvatar());
        holder.name.setText(order.getStoreName());
        holder.num_disher.setText(order.getTotalPrice()+"đ");
        if(order.getStatus() == 0){
            holder.tv_status.setText("Chờ xác nhận");
            holder.image.setImageResource(R.drawable.images_waiting);
        } else if (order.getStatus() == 1) {
            holder.tv_status.setText("Đang giao hàng");
            holder.image.setImageResource(R.drawable.icon_delivery);
        } else if (order.getStatus() == 2) {
            holder.tv_status.setText("Giao hàng thành công");
            holder.image.setImageResource(R.drawable.icon_success);
        }else if(order.getStatus() == 3){
            holder.tv_status.setText("Đơn hàng bị hủy");
            holder.image.setImageResource(R.drawable.cancelicon);
        }
        holder.item_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToItem(order);
            }
        });
    }

    private void onClickGoToItem(OrderResponse order) {
        Intent intent = new Intent(context, DetailOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_order", order);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(orderResponses != null){
            return orderResponses.size();
        }
        return 0;
    }

    public class ItemOrderHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name, num_disher, tv_status;
        private LinearLayout item_order;
        public ItemOrderHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_order);
            num_disher = itemView.findViewById(R.id.tv_num_dish);
            item_order = itemView.findViewById(R.id.item_order);
            tv_status = itemView.findViewById(R.id.textView32);
            image = itemView.findViewById(R.id.imageView30);
        }
    }
}
