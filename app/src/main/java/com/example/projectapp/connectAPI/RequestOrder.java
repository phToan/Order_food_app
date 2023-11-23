package com.example.projectapp.connectAPI;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectapp.ObjectClass.FoodOrderRes;
import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.OrderDetail;
import com.example.projectapp.ObjectClass.OrderRequest;
import com.example.projectapp.ObjectClass.OrderResponse;
import com.example.projectapp.ObjectClass.Stores;
import com.example.projectapp.ObjectClass.User;
import com.google.gson.Gson;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestOrder {
    OkHttpClient client = new OkHttpClient();
    String hostUrl = "https://food-app-api-z0uw.onrender.com/";
    public CompletableFuture<String> requestAddOrder(Context context, OrderRequest orderRequest) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String jsonBody = new Gson().toJson(orderRequest);
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Log.d("storename", "requestAddOrder: " + orderRequest.getStore_name());

        Request request = new Request.Builder()
                .url(hostUrl+"orders/add")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                future.completeExceptionally(e);
                Notification notification = new Notification();
                notification.showAlertDialog(context, "Đã xảy ra lỗi kết nối mạng ");
                Log.d("ok", "onFailure: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    Log.d("order", "response: " + response);

                    if (response.isSuccessful()) {

                        try {
                            ResponseBody responseBody = response.body();
                            String responseData = responseBody.string();

                            Log.d("order", "onResponse: " + responseData);

                            JSONObject jsonResponse = new JSONObject(responseData);
                            String message = jsonResponse.getString("message");
                            future.complete(message);
                        } catch (JsonSyntaxException ee) {
                            future.completeExceptionally(new IOException("Response body is null"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        future.completeExceptionally(new IOException("Unsuccessful response: " + response.code()));
                    }
                } catch (IOException e) {
                    // Bắt và xử lý ngoại lệ IOException
                    future.completeExceptionally(e);
                }

            }
        });

        return future;
    }


    public CompletableFuture<List<OrderResponse>> requestOrder(String userid) {
        CompletableFuture<List<OrderResponse>> future = new CompletableFuture<>();
        String url = hostUrl+ "orders/getorderbyuserid/" + userid;

        Request request = new Request.Builder()
                .url(url)
                .build();
        List<OrderResponse> orderResponses = new ArrayList<>();
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
                                Log.d("ok", "onResponse: " + responseData);
                                JSONObject jsonResponse = new JSONObject(responseData);

                                JSONArray dataArray = jsonResponse.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject orderObject = dataArray.getJSONObject(i);

                                    int order_id = orderObject.getInt("order_id");
                                    int user_id = orderObject.getInt("user_id");
                                    int status = orderObject.getInt("status");
                                    String order_date = orderObject.getString("order_date");
                                    String total_price = orderObject.getString("total_price");
                                    String store_name = orderObject.getString("store_name");

                                    OrderResponse orderResponse = new OrderResponse(order_id, user_id, status, order_date, total_price, store_name);
                                    orderResponses.add(orderResponse);
                                }
                                Log.d("ok", "onResponse: " + orderResponses);
                                future.complete(orderResponses);
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
                } catch (IOException e) {
                    // Bắt và xử lý ngoại lệ IOException
                    Log.e("Tag", "IOException occurred: " + e.getMessage());
                    future.complete(new ArrayList<>());
                }
            }
        });

        return future;
    }

    public CompletableFuture<List<OrderDetail>> requestDetailOrder(String orderid) {
        CompletableFuture<List<OrderDetail>> future = new CompletableFuture<>();
        String url = hostUrl+ "orders/detail/" + orderid;
        Request request = new Request.Builder()
                .url(url)
                .build();
        List<OrderDetail> orderDetailList = new ArrayList<>();
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
                                Log.d("ok", "onResponse: " + responseData);
                                JSONObject jsonResponse = new JSONObject(responseData);

                                JSONArray dataArray = jsonResponse.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject orderObject = dataArray.getJSONObject(i);

                                    OrderDetail store = new OrderDetail();
                                    store.setOrderDetailId(orderObject.getInt("order_detail_id"));
                                    store.setOrderId(orderObject.getInt("order_id"));
                                    store.setProductId(orderObject.getInt("product_id"));
                                    store.setQuantity(orderObject.getInt("quantity"));
                                    store.setStoreId(orderObject.getInt("store_id"));
                                    store.setPrice(orderObject.getString("price"));
                                    store.setProductName(orderObject.getString("product_name"));
                                    store.setAvatar(orderObject.getString("avatar"));
                                    store.setDiscount(orderObject.getString("discount"));
                                    store.setStatus(orderObject.getBoolean("status"));
                                    store.setRate(orderObject.getString("rate"));
                                    orderDetailList.add(store);
                                }
                                Log.d("ok", "onResponse: " + orderDetailList);
                                future.complete(orderDetailList);
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
                } catch (IOException e) {
                    // Bắt và xử lý ngoại lệ IOException
                    Log.e("cart", "IOException occurred: " + e.getMessage());
                    future.complete(new ArrayList<>());
                }
            }
        });

        return future;
    }


}
