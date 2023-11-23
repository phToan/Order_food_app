package com.example.projectapp.ui.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.Adapter.GridviewItemAdapter;
import com.example.projectapp.Adapter.ListviewAdapter;
import com.example.projectapp.Adapter.SlideAdapter;
import com.example.projectapp.Adapter.StoreAdapter;
import com.example.projectapp.ObjectClass.item_gridview;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.ObjectClass.Stores;
import com.example.projectapp.ObjectClass.sliderItem;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestStore;
import com.example.projectapp.ui.Address.AddressActivity;
import com.example.projectapp.ObjectClass.ItemDrawer;
import com.example.projectapp.Adapter.ListItemMenuAdapter;
import com.example.projectapp.ui.Address.UpdateEvent;
import com.example.projectapp.ui.ItemSelect.ItemGridviewActivity;
import com.example.projectapp.ui.Payment.CartActivity;
import com.example.projectapp.ui.Search.SearchActivity;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeActivity extends AppCompatActivity implements ListItemMenuAdapter.OnCloseActivity {

    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();
    private GridView gridView, gridViewFood;
    private ArrayList<item_gridview> arrayList;
    private GridviewItemAdapter gridviewItemAdapter;
    private RecyclerView recyclerView_store, rcv_menu, recycleView_drink, recyclerView_store_meal, listview, getRecyclerView_store_fastfood, listview_fastfood;
    private StoreAdapter storeAdapter;
    private ListItemMenuAdapter listItemMenuAdapter;
    private ListviewAdapter listviewAdapter;
    private ImageView imv_cart, bt_menu, bt_createUser;
    private TextView search, address, tv_username, tv_reheader, tv_email;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ScrollView scrollView2;
    private String addresses;

    @Override
    public void onCloseActivity() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateEvent event) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        addresses = sharedPreferences.getString("address", "Số 8, ngõ 445/25, Nguyễn Khang, P.Yên Hòa, Cầu Giấy, Hà Nội");
        address = findViewById(R.id.address);
        address.setText(addresses);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventBus.getDefault().register(this);
        imv_cart = findViewById(R.id.imv_cart);
        search = findViewById(R.id.editTextText2);
        address = findViewById(R.id.address);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        bt_menu = findViewById(R.id.imageView5);
        tv_username = findViewById(R.id.tv_username);
        tv_reheader = findViewById(R.id.tv_reheader);
        scrollView2 = findViewById(R.id.scrollView2);
        tv_email = findViewById(R.id.tv_email);

        SharePreferences sharePreferences = new SharePreferences();
        tv_username.setText(sharePreferences.getSharedPreferences(HomeActivity.this, "username", "Toàn phạm"));
        tv_email.setText("Email: " + sharePreferences.getSharedPreferences(HomeActivity.this, "email", "pht@gmail.com"));


        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        imv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        tv_reheader.setPaintFlags(tv_reheader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_reheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView2.smoothScrollTo(0, 0);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        addresses = sharedPreferences.getString("address", "68 Ngõ 445 Nguyễn Khang");
        address.setText(addresses);

        ViewPager();

//        runOnUiThread(() -> {
        setGridView();
        setStore();
        setStoreMeal();
        setListview();
        setStoreFastFood();
        setListviewFastFood();
        setListviewMenu();
        setListDrink();
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setListDrink() {
        recycleView_drink = findViewById(R.id.recycleView_drink);
        listviewAdapter = new ListviewAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleView_drink.setLayoutManager(linearLayoutManager);

        RequestStore requestStore = new RequestStore();
        CompletableFuture<List<Stores>> future = requestStore.requestListStore("1");

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
//                runOnUiThread(()->{
                if (result != null) {
//                        runOnUiThread(() -> {
                    listviewAdapter.setData(result);
                    recycleView_drink.setAdapter(listviewAdapter);
//                        });
//                    Log.e("ok", "setRecycleView: "+ result);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(HomeActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
//                });

            }
        });
        future.join();
    }

    private void setListviewMenu() {
        rcv_menu = findViewById(R.id.rcv_menu);
        List<ItemDrawer> list = new ArrayList<>();
        list.add(new ItemDrawer(0, "Thông tin cá nhân", R.drawable.baseline_person_24));
        list.add(new ItemDrawer(1, "Quản lý đơn hàng", R.drawable.baseline_event_note_24));
        list.add(new ItemDrawer(2, "Đổi mật khẩu", R.drawable.baseline_lock_reset_24));
        list.add(new ItemDrawer(5, "Thông báo", R.drawable.baseline_notifications_24));
        list.add(new ItemDrawer(3, "Cài đặt", R.drawable.baseline_settings_24));
        list.add(new ItemDrawer(4, "Đăng xuất", R.drawable.baseline_logout_24));
        listItemMenuAdapter = new ListItemMenuAdapter(this, list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_menu.setLayoutManager(linearLayoutManager);
        rcv_menu.setAdapter(listItemMenuAdapter);
    }


    private void setListviewFastFood() {
        listview_fastfood = findViewById(R.id.listview_ff);
        listviewAdapter = new ListviewAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listview_fastfood.setLayoutManager(linearLayoutManager);

        RequestStore requestStore = new RequestStore();
        CompletableFuture<List<Stores>> future = requestStore.requestListStore("3");

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
//                runOnUiThread(()->{
                if (result != null) {
                    listviewAdapter.setData(result);
                    listview_fastfood.setAdapter(listviewAdapter);
//                    Log.e("ok", "setRecycleView: "+ result);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(HomeActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
//                });

            }
        });
        future.join();
    }

    private void setStoreFastFood() {
        getRecyclerView_store_fastfood = findViewById(R.id.recycleView_store_fastfood);
        storeAdapter = new StoreAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        getRecyclerView_store_fastfood.setLayoutManager(linearLayoutManager);

        RequestStore requestStore = new RequestStore();
        CompletableFuture<List<Stores>> future = requestStore.requestListStore("4");
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
//                runOnUiThread(()->{
                if (result != null) {
                    storeAdapter.setData(result);
                    getRecyclerView_store_fastfood.setAdapter(storeAdapter);
//                    Log.e("ok", "setRecycleView: "+ result);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(HomeActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
//                });
            }
        });
        future.join();
    }

    private void setListview() {
        listview = findViewById(R.id.listview_meal);
        listviewAdapter = new ListviewAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listview.setLayoutManager(linearLayoutManager);

        RequestStore requestStore = new RequestStore();
        CompletableFuture<List<Stores>> future = requestStore.requestListStore("0");
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
//                runOnUiThread(()->{
                if (result != null) {
                    listviewAdapter.setData(result);
                    listview.setAdapter(listviewAdapter);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(HomeActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
//                });


            }
        });
        future.join();
    }

    private void setStoreMeal() {
        recyclerView_store_meal = findViewById(R.id.recycleView_store_1);
        storeAdapter = new StoreAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView_store_meal.setLayoutManager(linearLayoutManager);

        RequestStore requestStore = new RequestStore();
        CompletableFuture<List<Stores>> future = requestStore.requestListStore("2");
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
//                runOnUiThread(()->{
                if (result != null) {
                    storeAdapter.setData(result);
                    recyclerView_store_meal.setAdapter(storeAdapter);
//                    Log.e("ok", "setRecycleView: "+ result);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(HomeActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
//                });
            }
        });
        future.join();
    }

    private void setStore() {
        recyclerView_store = findViewById(R.id.recycleView_store);
        storeAdapter = new StoreAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView_store.setLayoutManager(linearLayoutManager);

        RequestStore requestStore = new RequestStore();
        CompletableFuture<List<Stores>> future = requestStore.requestAllStore();

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
//                runOnUiThread(()->{
                if (result != null) {
                    List<Stores> storesList = new ArrayList<>();
                    for (int a = 0; a < result.size(); a++) {
                        if (result.get(a).getRate().equals("5.00")) {
                            storesList.add(result.get(a));
                        }
                    }
                    storeAdapter.setData(storesList);
                    recyclerView_store.setAdapter(storeAdapter);
                } else {
                    Log.d("test", "onResponse: d");
                    Toast.makeText(HomeActivity.this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
//                });

            }
        });
        future.join();
    }

    private void setGridView() {
        gridView = findViewById(R.id.gridviewItem);
        arrayList = new ArrayList<>();
        arrayList.add(new item_gridview(7, "Nearby", R.drawable.imge1));
        arrayList.add(new item_gridview(0, "Bữa tối nửa giá", R.drawable.image2));
        arrayList.add(new item_gridview(5, "Khuyến mại", R.drawable.image3));
        arrayList.add(new item_gridview(9, "Quán ngon đặc tuyển", R.drawable.star));
        arrayList.add(new item_gridview(3, "Đồ ăn nhanh", R.drawable.pop_2));
        arrayList.add(new item_gridview(6, "Phở ngon", R.drawable.pho));
        arrayList.add(new item_gridview(1, "Đồ uống ngon rẻ", R.drawable.trasua));
        arrayList.add(new item_gridview(8, "Tất cả các quán", R.drawable.food));
        gridviewItemAdapter = new GridviewItemAdapter(this, R.layout.item_gridview, arrayList);
        gridView.setAdapter(gridviewItemAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeActivity.this, ItemGridviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", arrayList.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    public void ViewPager() {
        viewPager2 = findViewById(R.id.viewPager);
        List<sliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new sliderItem(R.drawable.slider1));
        sliderItems.add(new sliderItem(R.drawable.slider2));
        sliderItems.add(new sliderItem(R.drawable.slider3));
        viewPager2.setAdapter(new SlideAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable, 4000);
            }
        });
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000);
    }


}