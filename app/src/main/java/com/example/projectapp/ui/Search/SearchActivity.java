package com.example.projectapp.ui.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectapp.Adapter.AllStoreAdapter;
import com.example.projectapp.Adapter.SearchFoodAdapter;
import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestProduct;
import com.example.projectapp.connectAPI.RequestSearch;
import com.example.projectapp.ui.Home.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SearchActivity extends AppCompatActivity {
    private LinearLayout layout1,layout2;
    private AllStoreAdapter allStoreAdapter;
    private RecyclerView recyclerView;
    private List<String> list;
    private ImageView bt_back;
    private EditText edt_search;
    private RecyclerView rcv_food_search, rcv_store_search;
    private SearchFoodAdapter searchFoodAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bt_back = findViewById(R.id.imageView8);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);

        recyclerView = findViewById(R.id.rcv_store);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        allStoreAdapter = new AllStoreAdapter(SearchActivity.this, addList());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allStoreAdapter);
        edt_search = findViewById(R.id.editTextText3);
        rcv_food_search = findViewById(R.id.rcv_propose);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edt_search.length() > 0){
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                    handleSearch(edt_search.getText().toString());
                } else {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                }
            }
        });

    }

    private void handleSearch(String toString) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_food_search.setLayoutManager(linearLayoutManager);
        RequestSearch requestSearch = new RequestSearch();
        CompletableFuture<List<Product>> future = requestSearch.searchFood(toString);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                runOnUiThread(() -> {
                    Toast.makeText(SearchActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                if (result != null) {
                    Log.d("product", "result: " + result);
                    searchFoodAdapter = new SearchFoodAdapter(SearchActivity.this, result);
                    rcv_food_search.setAdapter(searchFoodAdapter);
//                    Log.e("ok", "setRecycleView: "+ result);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(SearchActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        future.join();
    }

    private List<String> addList() {
        list = new ArrayList<>() ;
        list.add("Cơm");
        list.add("Bún chả");
        list.add("Phở");
        list.add("Gà");
        list.add("Mixue");
        list.add("Trà sữa");
        list.add("Cháo");
        list.add("Mì trộn");
        list.add("Bún bò Huế");
        list.add("Bún đậu");
        list.add("Mì cay");
        list.add("Pizza");
        list.add("Bánh tráng");
        list.add("Bánh tráng");
        list.add("Bánh tráng");
        return list;
    }


}