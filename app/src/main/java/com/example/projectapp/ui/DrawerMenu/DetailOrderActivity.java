package com.example.projectapp.ui.DrawerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.Adapter.FoodOrderAdapter;
import com.example.projectapp.Adapter.GridviewFoodAdapter;
import com.example.projectapp.ObjectClass.Cart;
import com.example.projectapp.ObjectClass.OrderDetail;
import com.example.projectapp.ObjectClass.OrderResponse;
import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.R;
import com.example.projectapp.ObjectClass.DishCart;
import com.example.projectapp.Adapter.DishCartAdapter;
import com.example.projectapp.ObjectClass.Order;
import com.example.projectapp.connectAPI.RequestOrder;
import com.example.projectapp.ui.Home.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DetailOrderActivity extends AppCompatActivity {

    private TextView tv_name_store, tv_total_price, tv_address;
    private RecyclerView recyclerView ;
    private FoodOrderAdapter foodOrderAdapter;
    private ImageView bt_back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        tv_name_store = findViewById(R.id.tv_name_store);
        recyclerView = findViewById(R.id.recyclerView);
        bt_back = findViewById(R.id.imageView4);
        tv_total_price = findViewById(R.id.textView39);
        tv_address = findViewById(R.id.textView21);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String addresses = sharedPreferences.getString("address", "Số 8, ngõ 445/25, Nguyễn Khang, P.Yên Hòa, Cầu Giấy, Hà Nội");
        tv_address.setText(addresses);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        OrderResponse order = (OrderResponse) bundle.get("obj_order");
        tv_name_store.setText(order.getStoreName());
        tv_total_price.setText(order.getTotalPrice()+ "đ");
        requestAPI(String.valueOf(order.getOrderId()));


    }

    private void requestAPI(String orderid) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RequestOrder requestOrder =  new RequestOrder();
        CompletableFuture<List<OrderDetail>> future = requestOrder.requestDetailOrder(orderid);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(DetailOrderActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                if (result != null) {
                    foodOrderAdapter = new FoodOrderAdapter(result, DetailOrderActivity.this);
                    recyclerView.setAdapter(foodOrderAdapter);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(DetailOrderActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

}