package com.example.projectapp.ui.ItemSelect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.ObjectClass.Stores;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestStore;
import com.example.projectapp.Adapter.ListviewAdapter;
import com.example.projectapp.ObjectClass.item_gridview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ItemGridviewActivity extends AppCompatActivity {
    private ListviewAdapter listviewAdapter;
    private RecyclerView recyclerView;
    private ImageView bt_back;
    private TextView tv_title;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_gridview);
        recyclerView =findViewById(R.id.rcv_store_selected);
        bt_back = findViewById(R.id.imageView12);
        tv_title = findViewById(R.id.textView50);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        item_gridview item = (item_gridview) bundle.get("item");
        tv_title.setText(item.getTitle());
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setRecycleView(item.getId());
    }

    private void setRecycleView(int id) {
        listviewAdapter = new ListviewAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RequestStore requestStore = new RequestStore();
        if(id < 5){
            CompletableFuture<List<Stores>> future = requestStore.requestListStore(""+id);
            future.whenComplete((result, exception) -> {
                if (exception != null) {
                    runOnUiThread(() -> {
                        Toast.makeText(ItemGridviewActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                    });
                    exception.printStackTrace();
                } else {
                    if (result != null) {
                        listviewAdapter.setData(result);
                        recyclerView.setAdapter(listviewAdapter);
//                    Log.e("ok", "setRecycleView: "+ result);
                    } else {
                        Log.d("test", "onResponse: d");
                        Toast.makeText(ItemGridviewActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            future.join();
        }else {
            CompletableFuture<List<Stores>> future = requestStore.requestAllStore();
            future.whenComplete((result, exception) -> {
                if (exception != null) {
                    runOnUiThread(() -> {
                        Toast.makeText(ItemGridviewActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                    });
                    exception.printStackTrace();
                } else {
                    if (result != null) {
                        runOnUiThread(() -> {
                            if(id == 8 || id ==7 || id ==6 || id == 5){
                                listviewAdapter.setData(result);
                                recyclerView.setAdapter(listviewAdapter);
                            } else if (id == 9) {
                                List<Stores> storesList = new ArrayList<>();
                                for(int i =0; i<result.size(); i++){
                                    if(result.get(i).getRate().equals("5.00") || result.get(i).getRate().equals("4.80") || result.get(i).getRate().equals("4.60")){
                                        storesList.add(result.get(i));
                                    }
                                }
                                listviewAdapter.setData(storesList);
                                recyclerView.setAdapter(listviewAdapter);
                            }
                        });
//                    Log.e("ok", "setRecycleView: "+ result);
                    } else {
                        Log.d("test", "onResponse: d");
                        Toast.makeText(ItemGridviewActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            future.join();
        }

    }

}