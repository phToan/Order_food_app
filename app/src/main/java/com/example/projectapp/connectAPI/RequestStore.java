package com.example.projectapp.connectAPI;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectapp.ObjectClass.Stores;
import com.google.gson.JsonObject;
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
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestStore {
    OkHttpClient client = new OkHttpClient();
    String hostUrl = "https://food-app-api-z0uw.onrender.com/";
    public CompletableFuture<List<Stores>> requestListStore(String storeid) {
        CompletableFuture<List<Stores>> future = new CompletableFuture<>();
        int type = Integer.parseInt(storeid);
        Log.d("ok", "requestListStore: " + type);
        HttpUrl url = HttpUrl.parse(hostUrl+"stores/getAllStores/")
                .newBuilder()
                .addQueryParameter("type", storeid)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        List<Stores> liststore = new ArrayList<>();
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
                                    JSONObject storeObject = dataArray.getJSONObject(i);

                                    int storeId = storeObject.getInt("store_id");
                                    String storeName = storeObject.getString("store_name");
                                    String avatarUrl = storeObject.getString("avatar");
                                    String address = storeObject.getString("address");
                                    String phone = storeObject.getString("phone");
                                    String rate = storeObject.getString("rate");
                                    String timeOpen = storeObject.getString("time_open");
                                    String timeClose = storeObject.getString("time_close");
                                    int storeType = storeObject.getInt("store_type");
                                    // Tạo đối tượng Store từ các giá trị thu được
                                    Stores store = new Stores();
                                    store.setStoreId(storeId);
                                    store.setStoreName(storeName);
                                    store.setAvatarUrl(avatarUrl);
                                    store.setAddress(address);
                                    store.setPhone(phone);
                                    store.setRate(rate);
                                    store.setTimeOpen(timeOpen);
                                    store.setTimeClose(timeClose);
                                    store.setStoreType(storeType);
                                    liststore.add(store);
                                }
                                Log.d("ok", "onResponse: " + liststore);
                                future.complete(liststore);
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

    public CompletableFuture<List<Stores>> requestAllStore() {
        CompletableFuture<List<Stores>> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url(hostUrl+"stores/getAllStores/")
                .build();
        List<Stores> liststore = new ArrayList<>();
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
                                    JSONObject storeObject = dataArray.getJSONObject(i);

                                    int storeId = storeObject.getInt("store_id");
                                    String storeName = storeObject.getString("store_name");
                                    String avatarUrl = storeObject.getString("avatar");
                                    String address = storeObject.getString("address");
                                    String phone = storeObject.getString("phone");
                                    String rate = storeObject.getString("rate");
                                    String timeOpen = storeObject.getString("time_open");
                                    String timeClose = storeObject.getString("time_close");
                                    int storeType = storeObject.getInt("store_type");
                                    // Tạo đối tượng Store từ các giá trị thu được
                                    Stores store = new Stores();
                                    store.setStoreId(storeId);
                                    store.setStoreName(storeName);
                                    store.setAvatarUrl(avatarUrl);
                                    store.setAddress(address);
                                    store.setPhone(phone);
                                    store.setRate(rate);
                                    store.setTimeOpen(timeOpen);
                                    store.setTimeClose(timeClose);
                                    store.setStoreType(storeType);
                                    liststore.add(store);
                                }
                                Log.d("ok", "onResponse: " + liststore);
                                future.complete(liststore);
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

    public CompletableFuture<Stores> requestAStore(String storeid) {
        CompletableFuture<Stores> future = new CompletableFuture<>();
        String url = hostUrl+"stores/getStore/" + storeid;
        Request request = new Request.Builder()
                .url(url)
                .build();

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

                                JSONObject jsonResponse = new JSONObject(responseData);
                                JSONObject data = jsonResponse.getJSONObject("data");

                                Stores store = new Stores();
                                store.setStoreName(data.getString("store_name"));
                                store.setAvatarUrl(data.getString("avatar"));
                                store.setStoreId(data.getInt("store_id"));
                                store.setStoreType(data.getInt("store_type"));
                                store.setAddress(data.getString("address"));
                                store.setPhone(data.getString("phone"));
                                store.setRate(data.getString("rate"));
                                store.setTimeOpen(data.getString("time_open"));
                                store.setTimeClose(data.getString("time_close"));

                                future.complete(store);
                            } else {
                                Log.d("test", "onResponse: 3");
                                future.complete(new Stores());
                            }
                        } catch (JsonSyntaxException ee) {
                            future.complete(new Stores());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        future.complete(new Stores());
                    }
                } catch (IOException e) {
                    // Bắt và xử lý ngoại lệ IOException
                    Log.e("cart", "IOException occurred: " + e.getMessage());
                    future.complete(new Stores());
                }
            }
        });

        return future;
    }

}
