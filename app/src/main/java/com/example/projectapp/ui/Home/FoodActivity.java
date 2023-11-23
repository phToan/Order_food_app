package com.example.projectapp.ui.Home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestCart;
import com.example.projectapp.ui.Address.UpdateEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.CompletableFuture;

public class FoodActivity extends AppCompatActivity {
    private int number = 1;
    private ImageView imv_avatar, bt_back;
    private TextView tv_name,tv_visible, tv_discount, tv_old_price, tv_new_price, bt_minus, bt_plus, tv_number_food, tv_price_payment, tv_notifi_discount, tv_payment;
    private ConstraintLayout bt_add_cart, constraintLayout4;

    private boolean status = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        imv_avatar = findViewById(R.id.imv_avatar);
        tv_name = findViewById(R.id.tv_food_name);
        tv_old_price = findViewById(R.id.tv_old_price);
        tv_new_price = findViewById(R.id.tv_food_price);
        tv_discount = findViewById(R.id.tv_food_discount);
        bt_back = findViewById(R.id.bt_back);
        bt_minus = findViewById(R.id.bt_minus);
        bt_plus = findViewById(R.id.bt_plus);
        tv_number_food = findViewById(R.id.tv_number_food);
        tv_price_payment = findViewById(R.id.tv_price_payment);
        bt_add_cart = findViewById(R.id.bt_add_cart);
        tv_notifi_discount = findViewById(R.id.tv_notifi_discount);
        constraintLayout4 = findViewById(R.id.constraintLayout4);
        tv_payment = findViewById(R.id.tv_payment);
        tv_visible = findViewById(R.id.tv_visible);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Product product = (Product) bundle.get("obj_food");
        Log.d("dfff", "onCreate: dd");
        if(bundle.get("id_screen") != null){
            tv_number_food.setText(product.getCount()+"");
            tv_payment.setVisibility(View.GONE);
            tv_price_payment.setVisibility(View.GONE);
            tv_visible.setText("Cập nhật");
        }

        Picasso.get().load(product.getAvatarUrl()).into(imv_avatar);
        tv_name.setText(product.getProductName());
        int price = (int) (product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
        tv_new_price.setText("" + price);

        if (product.getDiscount() > 0) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) constraintLayout4.getLayoutParams();
            int newHeightInDp = 110;
            int newHeightInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newHeightInDp, getResources().getDisplayMetrics());
            layoutParams.height = newHeightInPixels;
            constraintLayout4.setLayoutParams(layoutParams);

            tv_old_price.setText(Double.toString(product.getPrice()));
            tv_discount.setText("Giảm " + product.getDiscount() + "%");
            tv_notifi_discount.setText("Bạn đã tiết kiệm được " + ((int) product.getPrice() - price) + "đ sau khi giảm giá");
        }
        tv_price_payment.setText("" + price);


        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                tv_number_food.setText(Integer.toString(number));
                tv_payment.setText("Thêm vào giỏ hàng - ");
                int newPrice = (int) (price + product.getPrice() * (number - 1));
                tv_price_payment.setText("" + newPrice);
                status = true;
                if (number > 1 && number < 4 && product.getDiscount() > 0) {
                    Toast.makeText(FoodActivity.this, "Bạn đã đạt đến giới hạn đổi ưu đãi. Bạn có thể tăng thêm số lượng của cùng 1 mặt hàng.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number > 1) {
                    number--;
                    tv_number_food.setText(Integer.toString(number));
                    tv_price_payment.setText("" + price * number);
                } else if (number == 1) {
                    if(bundle.get("id_screen") != null){
                        tv_visible.setText("Xóa");
                    }
                    number--;
                    tv_number_food.setText(Integer.toString(number));
                    tv_payment.setText("Quay trở lại thực đơn");
                    tv_price_payment.setText("");

                    status = false;
                }
            }
        });

        bt_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle.get("id_screen") != null){
                    if(tv_visible.getText().equals("Xóa")){
                        showAlertDialog(String.valueOf(product.getProductId()));
                    }else {
                        requestChageQuantity(String.valueOf(product.getProductId()), tv_number_food.getText().toString());
                    }
                }
                if (!status) {
                    finish();
                } else {
                    SharePreferences sharePreferences = new SharePreferences();
                    String userid = sharePreferences.getSharedPreferences(FoodActivity.this, "userid", "1");
                    requestAPI(userid, String.valueOf(product.getProductId()), tv_number_food.getText().toString());
                }
            }
        });

    }

    private void requestChageQuantity(String id, String count) {
        RequestCart requestCart = new RequestCart();
        CompletableFuture future = requestCart.updateQuantity(FoodActivity.this, id, count);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                Toast.makeText(FoodActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý dữ liệu user
                if (result.equals("Update successfully")) {
                    Toast.makeText(FoodActivity.this, "thay đổi số lượng món ăn thành công!", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new UpdateEvent());
                    finish();
                } else {
                    Toast.makeText(FoodActivity.this, "thay đổi số lượng món ăn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

    private void requestDelCartItem(String id){
        RequestCart requestCart = new RequestCart();
        CompletableFuture future = requestCart.requestDelCartItem(FoodActivity.this, id);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                Toast.makeText(FoodActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý dữ liệu user

                if (result.equals("Delete successfully")) {
                    Log.d("had", "ở kia: " + result);
                    EventBus.getDefault().post(new UpdateEvent());
                    finish();
                } else {
                    Toast.makeText(FoodActivity.this, "Xóa đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }
    private void requestAPI(String userid, String productid, String quantity) {
        RequestCart requestCart = new RequestCart();
        CompletableFuture<Integer> future = requestCart.addToCart(userid, productid, quantity);
        future.whenComplete((result, exception) -> {
            // Xử lý dữ liệu user
            runOnUiThread(() -> {
                if (result == 1) {
                    Toast.makeText(this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (result == 2) {
                    Toast.makeText(this, "Cập nhật số lượng thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.d("test", "onResponse: d");
                    Notification notification = new Notification();
                    notification.showAlertDialog(this, "Thêm vào giỏ hang thất bại");
                }
            });

        });
        future.join();
    }
    private void showAlertDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);
        builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("dag", "id: " + id);
                dialog.dismiss(); // Đóng Dialog
                requestDelCartItem(id);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng Dialog
            }
        }).show(); // Hiển thị Dialog
    }

}