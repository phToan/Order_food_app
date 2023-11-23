package com.example.projectapp.ui.Payment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestCart;
import com.example.projectapp.ui.Address.UpdateEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.CompletableFuture;

public class ChangeQuantity extends AppCompatActivity {
    private int number = 1;
    private ImageView imv_avatar, bt_back;
    private TextView tv_name, tv_visible, tv_discount, tv_old_price, tv_new_price, bt_minus, bt_plus, tv_number_food, tv_price_payment, tv_notifi_discount, tv_payment;
    private ConstraintLayout bt_add_cart, constraintLayout4;

    private boolean status = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_quantity);

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
        constraintLayout4 = findViewById(R.id.constraintLayout4);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Product product = (Product) bundle.get("obj_food");


        Picasso.get().load(product.getAvatarUrl()).into(imv_avatar);
        tv_name.setText(product.getProductName());
        int price = (int) product.getPrice();
        tv_new_price.setText("" + price);
        tv_number_food.setText(product.getCount()+"");
        number = Integer.parseInt(tv_number_food.getText().toString());

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
                tv_price_payment.setText("Cập nhật");
            }
        });

        bt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number > 1) {
                    number--;
                    tv_number_food.setText(Integer.toString(number));
                    tv_price_payment.setText("Cập nhật");
                } else if (number == 1) {
                    number--;
                    tv_number_food.setText(Integer.toString(number));
                    tv_price_payment.setText("Xóa");
                }
            }
        });

        bt_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_price_payment.getText().equals("Xóa")) {
                    showAlertDialog(String.valueOf(product.getProductId()));
                } else if((tv_price_payment.getText().equals("Cập nhật"))) {
                    requestChageQuantity(String.valueOf(product.getProductId()), tv_number_food.getText().toString());
                }
            }
        });

    }

    private void requestChageQuantity(String id, String count) {
        RequestCart requestCart = new RequestCart();
        CompletableFuture future = requestCart.updateQuantity(ChangeQuantity.this, id, count);
        future.whenComplete((result, exception) -> {
            Log.d("vl", "requestChageQuantity: "+result);

            if (exception != null) {
                Toast.makeText(ChangeQuantity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý dữ liệu user
                if (result.equals("Update successfully")) {
                    EventBus.getDefault().post(new UpdateEvent());
                    finish();

                } else {
                    Toast.makeText(ChangeQuantity.this, "Thay đổi số lượng món ăn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

    private void requestDelCartItem(String id) {
        RequestCart requestCart = new RequestCart();
        CompletableFuture future = requestCart.requestDelCartItem(ChangeQuantity.this, id);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                Toast.makeText(ChangeQuantity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý dữ liệu user

                if (result.equals("Delete successfully")) {
                    Log.d("had", "ở kia: " + result);
                    EventBus.getDefault().post(new UpdateEvent());
                    Log.d("had", "requestDelCartItem: ");
                    finish();
                } else {
                    Toast.makeText(ChangeQuantity.this, "Xóa đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

    private void showAlertDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeQuantity.this);
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