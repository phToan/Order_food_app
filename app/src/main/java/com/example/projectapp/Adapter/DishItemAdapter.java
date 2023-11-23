package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Dish;
import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestCart;
import com.example.projectapp.ui.Address.UpdateEvent;
import com.example.projectapp.ui.Payment.ChangeQuantity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DishItemAdapter extends RecyclerView.Adapter<DishItemAdapter.DishItemHolder> {
    private Context context;
    private List<Dish> list;

    public DishItemAdapter(Context context, List<Dish> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DishItemAdapter.DishItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishItemAdapter.DishItemHolder holder, int position) {
        Dish dish = list.get(position);
        holder.tv_name.setText(dish.getName());
        holder.tv_sl.setText(dish.getQuantity() + "x");
        holder.itemdish.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showAlertDialog(String.valueOf(dish.getId()));
                return false;
            }
        });
        holder.itemdish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product();
                product.setDiscount(Double.parseDouble(dish.getDiscount()));
                product.setPrice(Double.parseDouble(dish.getPrice()));
                product.setProductName(dish.getName());
                product.setAvatarUrl(dish.getAvatar());
                product.setCount(dish.getQuantity());
                product.setProductId(dish.getId());
                showAlertDialog2(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class DishItemHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_sl;
        LinearLayout itemdish;

        public DishItemHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.textView22);
            tv_sl = itemView.findViewById(R.id.textView97);
            itemdish = itemView.findViewById(R.id.itemdish);
        }
    }

    private void showAlertDialog(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("dag", "id: " + id);
                requestAPI(id);
                dialog.dismiss(); // Đóng Dialog
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng Dialog
            }
        }).show(); // Hiển thị Dialog
    }

    private void showAlertDialog2(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn thay đổi số lượng: "+ product.getProductName()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, ChangeQuantity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj_food", product);
                intent.putExtras(bundle);
                context.startActivity(intent);
                dialog.dismiss(); // Đóng Dialog
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng Dialog
            }
        }).show(); // Hiển thị Dialog
    }

    private void requestAPI(String id) {
        RequestCart requestCart = new RequestCart();
        CompletableFuture future = requestCart.requestDelCartItem(context, id);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                Toast.makeText(context, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý dữ liệu user

                if (result.equals("Delete successfully")) {
                    Log.d("had", "ở kia: " + result);
                    EventBus.getDefault().post(new UpdateEvent());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo"); // Tiêu đề của AlertDialog
                    builder.setMessage("Xóa đơn hàng thành công!"); // Nội dung của AlertDialog
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EventBus.getDefault().post(new UpdateEvent());
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(context, "Xóa đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

}
