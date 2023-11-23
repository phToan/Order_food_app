package com.example.projectapp.connectAPI;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projectapp.ObjectClass.Cart;
import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.OrderRequest;
import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.ObjectClass.Stores;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

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

public class RequestCart {
    OkHttpClient client = new OkHttpClient();
    String hostUrl = "https://food-app-api-z0uw.onrender.com/";

    public CompletableFuture<Integer> addToCart(String userid, String productid, String quantity) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        RequestBody formBody = new FormBody.Builder()
                .add("userid", userid)
                .add("productid", productid)
                .add("quantity", quantity)
                .build();
        Request request = new Request.Builder()
                .url(hostUrl+"carts/add")
                .post(formBody)
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
                            String responseData = responseBody.string();
                            Log.d("ok", "onResponse: " + responseData);
                            JSONObject jsonResponse = new JSONObject(responseData);
                            String message = jsonResponse.getString("message");
                            int check;
                            if (message.equals("Add to cart successfully")) {
                                check = 1;
                            } else if(message.equals("Update quantity  successfully")){
                                check = 2;
                            }else {
                                check = 0;
                            }
                            future.complete(check);
                        } catch (JsonSyntaxException ee) {
                            future.complete(0);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        future.complete(0);
                    }
                } catch (IOException e) {
                    // Bắt và xử lý ngoại lệ IOException
                    Log.e("Tag", "IOException occurred: " + e.getMessage());
                    future.complete(0);
                }
            }
        });

        return future;
    }

    public CompletableFuture<List<Cart>> getCart(String userid) {
        CompletableFuture<List<Cart>> future = new CompletableFuture<>();
        Log.d("cart", "userid: " + userid);

        HttpUrl url = HttpUrl.parse(  hostUrl + "carts/getCartItems/")
                .newBuilder()
                .addQueryParameter("userid", userid)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        List<Cart> listCartItem = new ArrayList<>();
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
                                Log.d("cart", "responseData: " + responseData);
                                JSONObject jsonResponse = new JSONObject(responseData);

                                JSONArray dataArray = jsonResponse.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject cartObject = dataArray.getJSONObject(i);
                                    Log.d("cart", "cartObject: " + cartObject);
                                    // Tạo đối tượng Store từ các giá trị thu được
                                    Cart cart = new Cart();
                                    cart.setAvatar(cartObject.getString("avatar"));
                                    cart.setId(cartObject.getInt("cart_id"));
                                    cart.setUserId(cartObject.getInt("user_id"));
                                    cart.setProductId(cartObject.getInt("product_id"));
                                    cart.setQuantity(cartObject.getInt("quantity"));
                                    cart.setProductName(cartObject.getString("product_name"));
                                    cart.setStoreId(cartObject.getInt("store_id"));
////                                    cart.setSize(cartObject.getString("size"));
                                    cart.setPrice(cartObject.getString("price"));
                                    cart.setDiscount(cartObject.getString("discount"));
                                    cart.setStatus(cartObject.getBoolean("status"));
                                    cart.setRate(cartObject.getString("rate"));
                                    cart.setStoreName(cartObject.getString("store_name"));
                                    cart.setAddress(cartObject.getString("address"));
                                    Log.d("cart", "store name: "+ cart.getStoreName());
                                    listCartItem.add(cart);
                                }
                                Log.d("cart", "onResponse: " + listCartItem);

                                future.complete(listCartItem);
                            } else {
                                future.complete(new ArrayList<>());
                            }
                        } catch (JsonSyntaxException ee) {
                            future.complete(new ArrayList<>());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        future.complete(new ArrayList<>());
                        Log.d("cart", "err: 1");
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

    public CompletableFuture<String> requestDelCartItem(Context context, String id) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String url = hostUrl+ "carts/delete/" + id;
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                future.completeExceptionally(e);
                Notification notification = new Notification();
                notification.showAlertDialog(context, "Đã xảy ra lỗi kết nối mạng ");
                Log.d("dag", "onFailure: "+e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    Log.d("dag", "response: "+response);
                    if (response.isSuccessful()) {
                        try {
                            ResponseBody responseBody = response.body();
                            String responseData = responseBody.string();
                            JSONObject jsonResponse = new JSONObject(responseData);
                            String message = jsonResponse.getString("message");
                            Log.d("dag", "message: "+message);
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

    public CompletableFuture<String> updateQuantity(Context context, String id, String count) {
        CompletableFuture<String> future = new CompletableFuture<>();
        RequestBody formBody = new FormBody.Builder()
                .add("cart_id", id)
                .add("quantity", count)
                .build();
        Request request = new Request.Builder()
                .url(hostUrl+"carts/update/")
                .put(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                future.completeExceptionally(e);
                Notification notification = new Notification();
                notification.showAlertDialog(context, "Đã xảy ra lỗi kết nối mạng ");
                Log.d("dag", "onFailure: "+e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    Log.d("dag", "response: "+response);
                    if (response.isSuccessful()) {
                        try {
                            ResponseBody responseBody = response.body();
                            String responseData = responseBody.string();
                            JSONObject jsonResponse = new JSONObject(responseData);
                            String message = jsonResponse.getString("message");
                            Log.d("dag", "message: "+message);
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

}
