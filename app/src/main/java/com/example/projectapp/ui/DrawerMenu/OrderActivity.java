package com.example.projectapp.ui.DrawerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectapp.Adapter.ItemOrderAdapter;
import com.example.projectapp.ObjectClass.OrderResponse;
import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.R;
import com.example.projectapp.ObjectClass.Order;
import com.example.projectapp.connectAPI.RequestOrder;
import com.example.projectapp.connectAPI.RequestProduct;
import com.example.projectapp.ui.Home.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OrderActivity extends AppCompatActivity {

    private ItemOrderAdapter itemOrderAdapter;
    private RecyclerView recyclerView;
    private ImageView bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = findViewById(R.id.rcv_order);
        bt_back = findViewById(R.id.imageView29);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharePreferences sharePreferences = new SharePreferences();
        String userid = sharePreferences.getSharedPreferences(this, "userid", "2");
        requestAPI(userid);
    }

    private void requestAPI(String userid) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RequestOrder requestOrder =  new RequestOrder();
        CompletableFuture<List<OrderResponse>> future = requestOrder.requestOrder(userid);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                runOnUiThread(() -> {
                    Toast.makeText(OrderActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                if (result != null) {
                    itemOrderAdapter = new ItemOrderAdapter(result, this);
                    recyclerView.setAdapter(itemOrderAdapter);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(OrderActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

}