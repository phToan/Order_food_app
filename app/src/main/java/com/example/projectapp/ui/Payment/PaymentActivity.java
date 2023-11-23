package com.example.projectapp.ui.Payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.Adapter.DishCartAdapter;
import com.example.projectapp.ObjectClass.FoodOrderRes;
import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.Order;
import com.example.projectapp.ObjectClass.OrderRequest;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.ObjectClass.Stores;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestOrder;
import com.example.projectapp.connectAPI.RequestStore;
import com.example.projectapp.ui.Address.AddressActivity;
import com.example.projectapp.ui.Address.UpdateEvent;
import com.example.projectapp.ui.Home.StoreItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PaymentActivity extends AppCompatActivity {

    private TextView tv_name_store, tv_distance_payment, tv_change, tv_main_address, tv_price_food, tv_delivery, tv_cast, bt_add_food;
    private RecyclerView recyclerView;
    private DishCartAdapter dishCartAdapter;
    private ImageView bt_back;
    private RadioGroup rg_fee;
    private RadioButton rb_fast, rb_saved, rb_priority;
    private ConstraintLayout bt_add_order;
    private int total_price;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateEvent event) {
        recreate();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        EventBus.getDefault().register(this);

        tv_name_store = findViewById(R.id.tv_name_store);
        tv_distance_payment = findViewById(R.id.tv_distance_payment);
        recyclerView = findViewById(R.id.recyclerView);
        bt_back = findViewById(R.id.imageView4);
        tv_change = findViewById(R.id.textView86);
        tv_main_address = findViewById(R.id.textView21);
        tv_price_food = findViewById(R.id.textView28);
        tv_delivery = findViewById(R.id.textView30);
        tv_cast = findViewById(R.id.textView39);
        rg_fee = findViewById(R.id.rg_fee);
        rb_fast = findViewById(R.id.radioButton7);
        rb_saved = findViewById(R.id.radioButton6);
        rb_priority = findViewById(R.id.radioButton5);
        bt_add_order = findViewById(R.id.bt_add_order);
        bt_add_food = findViewById(R.id.textView26);


        SharePreferences sharePreferences = new SharePreferences();
        String main_address = sharePreferences.getSharedPreferences(this, "address", "25 Ngõ 445 Nguyễn Khang");
        tv_main_address.setText(main_address);
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Order order = (Order) bundle.get("obj_order");

        int id = order.getOrderId();
        Log.d("idee", "idcart: " + id);
        Log.d("idee", "order: ");

        tv_distance_payment.setText("6 km");
        tv_name_store.setText(order.getCarts().get(0).getStoreName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dishCartAdapter = new DishCartAdapter(order.getCarts(), this);
        recyclerView.setAdapter(dishCartAdapter);

        double cast = 0;
        for (int i = 0; i < order.getCarts().size(); i++) {
            double discount = Double.parseDouble(order.getCarts().get(i).getDiscount());
            double price = Double.parseDouble(order.getCarts().get(i).getPrice());
            int count = order.getCarts().get(i).getQuantity();
            cast += ((price * (1 - discount / 100)) + (price * (count - 1)));
        }


        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        Double price = Double.parseDouble(tv_delivery.getText().toString());
        total_price = (int) (price + cast);
        String formattedCast = decimalFormat.format(total_price);
        tv_cast.setText(formattedCast + "đ");

        String formattedNumber = decimalFormat.format(cast);
        tv_price_food.setText((int) cast + "");
        double finalCast = cast;
        double finalCast1 = cast;
        double finalCast2 = cast;

        rg_fee.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton7) {
                    tv_delivery.setText("16000");
                    Double price = Double.parseDouble(tv_delivery.getText().toString());
                    total_price = (int) (price + finalCast);
                    String formattedCast = decimalFormat.format(total_price);
                    tv_cast.setText(formattedCast + "đ");
                } else if (i == R.id.radioButton6) {
                    tv_delivery.setText("12000");
                    Double price = Double.parseDouble(tv_delivery.getText().toString());
                    total_price = (int) (price + finalCast1);
                    String formattedCast = decimalFormat.format(total_price);
                    tv_cast.setText(formattedCast + "đ");
                } else {
                    tv_delivery.setText("20000");
                    Double price = Double.parseDouble(tv_delivery.getText().toString());
                    total_price = (int) (price + finalCast2);
                    String formattedCast = decimalFormat.format(total_price);
                    tv_cast.setText(formattedCast + "đ");
                }
            }
        });

        bt_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStore(id);
            }
        });


        bt_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = Integer.parseInt(sharePreferences.getSharedPreferences(PaymentActivity.this, "userid", "2"));
                List<FoodOrderRes> products = new ArrayList<>();
                for (int i = 0; i < order.getCarts().size(); i++) {
                    int product_id = order.getCarts().get(i).getProductId();
                    int quantity = order.getCarts().get(i).getQuantity();
                    int price = (int) Double.parseDouble(order.getCarts().get(i).getPrice());
                    products.add(new FoodOrderRes(product_id, quantity, price));

                }
                String totalPrice = String.valueOf(total_price);
                String store_name = order.getCarts().get(0).getStoreName();
                OrderRequest request = new OrderRequest(userId, products, totalPrice, store_name);
                requestAPI(request);
            }
        });

    }

    private void requestStore(int id) {
        RequestStore requestStore = new RequestStore();
        CompletableFuture<Stores> future = requestStore.requestAStore(String.valueOf(id));
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(PaymentActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Xử lý dữ liệu user
                if (result != null) {
                    Intent i = new Intent(PaymentActivity.this, StoreItem.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("obj_store", result);
                    bundle.putSerializable("id_screen", id);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(PaymentActivity.this, "Không tìm thấy cửa hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();

    }

    private void requestAPI(OrderRequest request) {
        RequestOrder requestOrder = new RequestOrder();
        CompletableFuture future = requestOrder.requestAddOrder(PaymentActivity.this, request);
        Notification notification = new Notification();
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(PaymentActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Xử lý dữ liệu user
                runOnUiThread(() -> {
                    if (result != null) {
                        if (result.equals("Order successfully")) ;
                        Toast.makeText(PaymentActivity.this, "Đặt đơn hàng thành công!!", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new UpdateEvent());
                        finish();
                    } else {
                        notification.showAlertDialog(PaymentActivity.this, " Đặt đơn thất bại! ");
                    }
                });
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