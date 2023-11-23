package com.example.projectapp.ui.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.Adapter.EndowFoodAdapter;
import com.example.projectapp.Adapter.EvaluationAdapter;
import com.example.projectapp.Adapter.GridviewFoodAdapter;

import com.example.projectapp.ObjectClass.Evaluation;
import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.ObjectClass.Stores;
import com.example.projectapp.R;
import com.example.projectapp.RoomDatabase.DatabaseHelper;
import com.example.projectapp.connectAPI.RequestProduct;
import com.example.projectapp.ui.Address.UpdateEvent;
import com.example.projectapp.ui.Payment.CartActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StoreItem extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ImageView img_back, imv_image, imv_cart_store;
    private TextView tv_nameStore, tv_discount, tv_distance, tv_rate, tv_address, tv_phone;
    private RecyclerView rcv_endow_food, rcv_review_evaluation;
    private EndowFoodAdapter endowFoodAdapter;
    private RecyclerView gridViewFood;
    private GridviewFoodAdapter gridviewFoodAdapter;
    private EvaluationAdapter evaluationAdapter;
    private int id;
    public static final int REQUEST_CALL_PHONE_PERMISSION = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_item);

        tv_nameStore = findViewById(R.id.tv_name_store);
        tv_discount = findViewById(R.id.tv_discount);
        tv_distance = findViewById(R.id.tv_distance);
        img_back = findViewById(R.id.imv_back);
        imv_image = findViewById(R.id.imv_background);
        imv_cart_store = findViewById(R.id.imv_cart_store);
        tv_rate = findViewById(R.id.textView7);
        tv_address = findViewById(R.id.tv_address);
        tv_phone = findViewById(R.id.tv_phone);

        imv_cart_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreItem.this, CartActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Stores store = (Stores) bundle.get("obj_store");
        if (bundle.get("id_screen") != null) {
            imv_cart_store.setVisibility(View.GONE);
        }

        Log.d("idor", "id: " + store.getStoreId());

        tv_nameStore.setText(store.getStoreName());
        tv_discount.setText("Ưu đãi đến 20%");
        tv_rate.setText(store.getRate());
        Picasso.get().load(store.getAvatarUrl()).into(imv_image);
        tv_address.setText(store.getAddress());
        tv_phone.setText(store.getPhone());
        tv_phone.setPaintFlags(tv_phone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = tv_phone.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle.get("id_screen") != null) {
                    EventBus.getDefault().post(new UpdateEvent());
                }
                finish();
            }
        });

        Log.d("idee", "id: " + store.getStoreId());

        setFoodEndow(store.getStoreId());
        setGridViewFood(store.getStoreId());
        setReviewEvaluation();
    }

    private void setReviewEvaluation() {
        rcv_review_evaluation = findViewById(R.id.rcv_review_evaluation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_review_evaluation.setLayoutManager(layoutManager);
        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(new Evaluation("Phạm Văn An", "thấy mọi người khen ngon quá trời mua về thử ai dè ngon thật"));
        evaluations.add(new Evaluation("Iron Man", "ngon lắm cả nhà ơi , đã đặt đơn của quán nhiều lần r..."));
        evaluations.add(new Evaluation("Cảnh Trần", "Ko có 10 sao à, cho tạm 5 sao vậy. Đỉnh của đỉnh"));
        evaluationAdapter = new EvaluationAdapter(evaluations, this);
        rcv_review_evaluation.setAdapter(evaluationAdapter);
    }

    private void setFoodEndow(int storeId) {
        rcv_endow_food = findViewById(R.id.recycleView_endow_food);
        endowFoodAdapter = new EndowFoodAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_endow_food.setLayoutManager(linearLayoutManager);

        RequestProduct requestProduct = new RequestProduct();
        CompletableFuture<List<Product>> future = requestProduct.requestListProducts(String.valueOf(storeId));
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                runOnUiThread(() -> {
                    Toast.makeText(StoreItem.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                if (result != null) {
                    Log.d("product", "result: " + result);
                    List<Product> productList = new ArrayList<>();
                    Product product = new Product();
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getDiscount() == 0) {
                            productList.add(result.get(i));
                        }
                    }
                    endowFoodAdapter.setData(productList);
                    rcv_endow_food.setAdapter(endowFoodAdapter);
//                    Log.e("ok", "setRecycleView: "+ result);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(StoreItem.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

    private void setGridViewFood(int storeId) {
        gridViewFood = findViewById(R.id.gridviewFood);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        gridViewFood.setLayoutManager(layoutManager);

        RequestProduct requestProduct = new RequestProduct();
        CompletableFuture<List<Product>> future = requestProduct.requestListProducts(String.valueOf(storeId));
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(StoreItem.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                if (result != null) {
                    List<Product> productList = new ArrayList<>();
                    Product product = new Product();
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getDiscount() == 0) {
                            productList.add(result.get(i));
                        }
                    }
                    gridviewFoodAdapter = new GridviewFoodAdapter(this, productList);
                    gridViewFood.setAdapter(gridviewFoodAdapter);

//                    Log.e("ok", "setRecycleView: "+ result);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(StoreItem.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, bạn có thể tiếp tục thực hiện cuộc gọi điện thoại
                // Ví dụ: Gọi Intent.ACTION_CALL ở đây
            } else {
                // Quyền không được cấp, hiển thị thông báo hoặc xử lý người dùng không cấp quyền
                Toast.makeText(this, "Ứng dụng cần quyền để thực hiện cuộc gọi điện thoại.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}