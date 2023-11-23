package com.example.projectapp.ui.Address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectapp.Adapter.PredictionAdapter;
import com.example.projectapp.ObjectClass.Prediction;
import com.example.projectapp.R;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity implements PredictionAdapter.OnCloseActivity{
    private PlacesClient placesClient;
    private ImageView bt_back;
    private ConstraintLayout layout_main, layout_list_address, location_curr, location_used;
    private EditText edt_search;
    private RecyclerView rcv_search;
    private PredictionAdapter predictionAdapter;
    private TextView tv_address_curr, tv_address_used;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        bt_back = findViewById(R.id.imageView9);
        layout_main = findViewById(R.id.layout_main);
        edt_search = findViewById(R.id.autoCompleteTextView);
        layout_list_address = findViewById(R.id.layout_list_address);
        rcv_search = findViewById(R.id.rcv_search);
        layout_list_address.setVisibility(View.GONE);
        location_curr = findViewById(R.id.constraintLayout11);
        location_used = findViewById(R.id.constraintLayout12);
        tv_address_curr = findViewById(R.id.textView46);
        tv_address_used = findViewById(R.id.textView48);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String retrievedString = sharedPreferences.getString("address", "Số 8, Ngách 25, ngõ 445, Nguyễn Khang");
        tv_address_used.setText(retrievedString);

        location_curr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String myString = tv_address_curr.getText().toString();
                editor.putString("address", myString);
                editor.apply();  // Hoặc dùng editor.commit() nếu bạn muốn đồng bộ ngay lập tức

                EventBus.getDefault().post(new UpdateEvent());
                finish();
            }
        });

        location_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String myString = tv_address_used.getText().toString();
                editor.putString("address", myString);
                editor.apply();  // Hoặc dùng editor.commit() nếu bạn muốn đồng bộ ngay lập tức
                EventBus.getDefault().post(new UpdateEvent());
                finish();
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    layout_main.setVisibility(View.GONE);
                    layout_list_address.setVisibility(View.VISIBLE);
                    handleSearch(edt_search.getText().toString());
                } else {
                    layout_main.setVisibility(View.VISIBLE);
                    layout_list_address.setVisibility(View.GONE);
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    // Trong một phương thức hoặc Activity/Fragment
    GoongApiService apiService = RetrofitClient.getRetrofitInstance().create(GoongApiService.class);
    private void handleSearch(String text) {
        Call<PredictionResponse> call = apiService.getPredictions(text, "country:VN", "uGwlo6yHxKnoqSPqp0Enla92wOd1YpmpbYrEy3GK"); // Thay YOUR_API_KEY bằng API key của bạn
        call.enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                if (response.isSuccessful()) {
                    PredictionResponse predictionResponse = response.body();
                    if (predictionResponse != null && "OK".equals(predictionResponse.getStatus())) {
                        List<Prediction> predictions = predictionResponse.getPredictions();
                        if (predictions != null && predictions.size() > 0) {
                            // Xử lý danh sách các dự đoán ở đây
                            rcv_search =  findViewById(R.id.rcv_search);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this, RecyclerView.VERTICAL,false);
                            rcv_search.setLayoutManager(linearLayoutManager);
                            predictionAdapter = new PredictionAdapter(predictions,  AddressActivity.this, AddressActivity.this);
                            rcv_search.setAdapter(predictionAdapter);
                        } else {
                            // Danh sách dự đoán trống
                        }
                    } else {
                        // Xử lý trạng thái không thành công
                    }
                } else {
                    // Xử lý lỗi từ API ở đây (response.errorBody())
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                Log.e("API Error", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCloseActivity() {
        finish();
    }

//    @Override
//    public void onCloseActivity() {
//
//    }



}