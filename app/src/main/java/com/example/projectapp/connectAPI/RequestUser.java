package com.example.projectapp.connectAPI;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.concurrent.CompletableFuture;

public class RequestUser {
    OkHttpClient client = new OkHttpClient();
    String hostUrl = "https://food-app-api-z0uw.onrender.com/";
    public CompletableFuture<User> requestUserAsync(Context context,String email, String password) {
        CompletableFuture<User> future = new CompletableFuture<>();
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(hostUrl+"users/signin")
                .post(formBody)
                .build();
        Log.d("test", "onResponse: 1 +"+ email);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                future.completeExceptionally(e);
                Log.d("ok", "onFailure: "+e);
                Notification notification = new Notification();
                notification.showAlertDialog(context, "Đã xảy ra lỗi kết nối mạng ");
//                Toast.makeText(context, , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        Log.d("test", "onResponse: 1");
                        try {
                            ResponseBody responseBody = response.body();
                            User user = new User();
                            Log.d("test", "onResponse: 1");
                            if(responseBody != null){
                                String responseData = responseBody.string();
                                JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();
                                JsonObject data = jsonObject.getAsJsonObject("data");

                                user.setUserId(data.get("user_id").getAsInt());
                                user.setUsername(data.get("username").getAsString());
                                user.setPassword(data.get("password").getAsString());
                                user.setEmail(data.get("email").getAsString());
                                user.setFullname(data.get("fullname").getAsString());
                                user.setGender(data.get("gender").getAsString());
                                user.setDob(data.get("dob").getAsString());
                                user.setPhone(data.get("phone").getAsString());
                                user.setAddress(data.get("address").getAsString());
                                future.complete(user);
                                Log.d("test", "onResponse: 2");
                            }else {
                                Log.d("test", "onResponse: 3");
                                future.complete(new User());
                            }
                        } catch (JsonSyntaxException ee) {
                            future.complete(new User());
                        }
                    } else {
                        future.complete(new User());                    }
                } catch (IOException e) {
                    // Bắt và xử lý ngoại lệ IOException
                    Log.e("Tag", "IOException occurred: " + e.getMessage());
                    future.complete(new User());
                }

            }
        });

        return future;
    }

    public CompletableFuture<String> requestSignupUser(Context context, User user) {
        CompletableFuture<String> future = new CompletableFuture<>();
        RequestBody formBody = new FormBody.Builder()
                .add("email", user.getEmail())
                .add("password", user.getPassword())
                .add("username", user.getUsername())
                .add("fullname", user.getFullname())
                .add("gender", user.getGender())
                .add("dateofbirth", user.getDob())
                .add("phone", user.getPhone())
                .build();
        Request request = new Request.Builder()
                .url(hostUrl+"users/signup")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                future.completeExceptionally(e);
                Notification notification = new Notification();
                notification.showAlertDialog(context, "Đã xảy ra lỗi kết nối mạng ");
                Log.d("ok", "onFailure: "+e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        try {
                            ResponseBody responseBody = response.body();
                            String responseData = responseBody.string();

                            Log.d("ok", "onResponse: "+responseData);

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

    public CompletableFuture<User> requestUpdateUser(Context context,String userid,String fullname, String gender,String dateofbirth, String phone) {
        CompletableFuture<User> future = new CompletableFuture<>();
        RequestBody formBody = new FormBody.Builder()
                .add("fullname", fullname)
                .add("gender", gender)
                .add("dateofbirth", dateofbirth)
                .add("phone", phone)
                .add("address", "abc")
                .build();
        String url =  hostUrl+"users/update/" +userid;
        Request request = new Request.Builder()
                .url(url)
                .put(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                future.completeExceptionally(e);
                Notification notification = new Notification();
                notification.showAlertDialog(context, "Đã xảy ra lỗi kết nối mạng ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        try {
                            ResponseBody responseBody = response.body();
                            User user = new User();
                            if(responseBody != null){
                                String responseData = responseBody.string();
                                JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();
                                JsonObject data = jsonObject.getAsJsonObject("data");

                                user.setUserId(data.get("user_id").getAsInt());
                                user.setUsername(data.get("username").getAsString());
                                user.setPassword(data.get("password").getAsString());
                                user.setEmail(data.get("email").getAsString());
                                user.setFullname(data.get("fullname").getAsString());
                                user.setGender(data.get("gender").getAsString());
                                user.setDob(data.get("dob").getAsString());
                                user.setPhone(data.get("phone").getAsString());
//                                user.setAddress(data.get("address").getAsString());
                                future.complete(user);
                                Log.d("test", "onResponse: 2");
                            }else {
                                future.complete(new User());
                            }
                        } catch (JsonSyntaxException ee) {
                            future.complete(new User());
                            Log.d("ok", "JsonSyntaxException: "+ee);
                        }
                    } else {
                        future.complete(new User());
                    }
                } catch (IOException e) {
                    // Bắt và xử lý ngoại lệ IOException
                    Log.e("Tag", "IOException occurred: " + e.getMessage());
                    future.complete(new User());
                }

            }
        });

        return future;
    }

}

