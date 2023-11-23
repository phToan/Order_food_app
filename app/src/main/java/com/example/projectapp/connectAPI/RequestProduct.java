package com.example.projectapp.connectAPI;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.ObjectClass.Stores;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestProduct {
    OkHttpClient client = new OkHttpClient();
    String hostUrl = "https://food-app-api-z0uw.onrender.com/";
    public CompletableFuture<List<Product>> requestListProducts(String storeid) {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();

        HttpUrl url = HttpUrl.parse(hostUrl+"products/getAllProducts/")
                .newBuilder()
                .addQueryParameter("storeid", storeid)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        List<Product> listProducts = new ArrayList<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        try {
                            ResponseBody responseBody = response.body();
                            if (responseBody != null) {
                                String responseData = responseBody.string();
                                Log.d("ok", "onResponse: "+ responseData);
                                JSONObject jsonResponse = new JSONObject(responseData);

                                JSONArray dataArray = jsonResponse.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject productObject = dataArray.getJSONObject(i);

//                                    int storeId = storeObject.getInt("store_id");
//                                    String storeName = storeObject.getString("store_name");
//                                    String avatarUrl = storeObject.getString("avatar");
//                                    String address = storeObject.getString("address");
//                                    String phone = storeObject.getString("phone");
//                                    String rate = storeObject.getString("rate");
//                                    String timeOpen = storeObject.getString("time_open");
//                                    String timeClose = storeObject.getString("time_close");
//                                    int storeType = storeObject.getInt("store_type");
                                    // Tạo đối tượng Store từ các giá trị thu được
                                    Product product = new Product();
                                    product.setProductId(productObject.getInt("product_id"));
                                    product.setProductName(productObject.getString("product_name"));
                                    product.setStoreId(productObject.getInt("store_id"));
                                    product.setAvatarUrl(productObject.getString("avatar"));
                                    product.setPrice(Double.parseDouble(productObject.getString("price")));
                                    product.setDiscount(Double.parseDouble(productObject.getString("discount")));
                                    product.setRate(Double.parseDouble(productObject.getString("rate")));
                                    product.setStatus(productObject.getBoolean("status"));
//                                    product.setSize(productObject.getString("size"));
//                                    product.setDescription(productObject.getString("description"));

                                    listProducts.add(product);
                                }
//                                Log.d("ok", "onResponse: "+ liststore);
                                future.complete(listProducts);
                            } else {
                                Log.d("test", "onResponse: 3");
                                future.complete(new ArrayList<>());
                            }
                        } catch (JsonSyntaxException ee) {
                            future.complete(new ArrayList<>());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        future.complete(new ArrayList<>());
                    }
                } catch(IOException e){
                    // Bắt và xử lý ngoại lệ IOException
                    Log.e("Tag", "IOException occurred: " + e.getMessage());
                    future.complete(new ArrayList<>());
                }
            }
        });

        return future;
    }
}
