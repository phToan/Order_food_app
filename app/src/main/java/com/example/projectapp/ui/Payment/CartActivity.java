package com.example.projectapp.ui.Payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectapp.Adapter.ListOrderAdapter;
import com.example.projectapp.ObjectClass.Cart;
import com.example.projectapp.ObjectClass.CartItem;
import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.Order;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestCart;
import com.example.projectapp.ui.Address.UpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CartActivity extends AppCompatActivity{

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateEvent event) {
        recreate();
    }

    private RecyclerView listOrder;
    private ListOrderAdapter listOrderAdapter;
    private ImageView imv_back_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        EventBus.getDefault().register(this);

        imv_back_home = findViewById(R.id.imv_back_home);
        imv_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        requestAPI();
    }

    private void requestAPI() {
        listOrder = findViewById(R.id.listview_order);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listOrder.setLayoutManager(linearLayoutManager);

        List<CartItem> cartItems = new ArrayList<>();
        SharePreferences sharePreferences = new SharePreferences();
        String userid = sharePreferences.getSharedPreferences(this, "userid", "2");
        Notification notification = new Notification();

        RequestCart requestCart = new RequestCart();
        CompletableFuture<List<Cart>> future = requestCart.getCart(userid);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                notification.showAlertDialog(this, "Đã xảy ra lỗi khi tải dữ liệu!");
                exception.printStackTrace();
            } else {
                if (result != null) {
                    Map<Integer, List<Cart>> cartMap = new HashMap<>();
                    for (Cart cart : result) {
                        int storeId = cart.getStoreId();
                        if (cartMap.containsKey(storeId)) {
                            // Nếu đã có, thêm đơn hàng vào danh sách tương ứng
                            cartMap.get(storeId).add(cart);
                        } else {
                            // Nếu chưa có, tạo một danh sách mới và thêm đơn hàng vào danh sách đó
                            List<Cart> carts = new ArrayList<>();
                            carts.add(cart);
                            cartMap.put(storeId, carts);
                        }
                    }
                    List<Order> orders = new ArrayList<>();
                    int i = 0;
                    for (List<Cart> ordersOfStore : cartMap.values()) {
                        List<Cart> carts = new ArrayList<>();
                        for (Cart cart : ordersOfStore) {
                            // Hiển thị thông tin của đơn hàng
                            Log.d("list", "ordersOfStore: "+cart.getProductName());
                            carts.add(cart);
                        }
                        Order order = new Order();
                        order.setCarts(carts);
                        orders.add(order);
                    }

                    Set<Integer> storeIds = cartMap.keySet();
                    for (int storeId : storeIds) {
                        orders.get(i).setOrderId(storeId);
                        i++;
                    }
                    listOrderAdapter = new ListOrderAdapter(orders, this);
                    listOrder.setAdapter(listOrderAdapter);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
