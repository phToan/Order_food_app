package com.example.projectapp.ui.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.ObjectClass.User;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestUser;
import com.example.projectapp.ui.DrawerMenu.EditUserActivity;
import com.example.projectapp.ui.Home.HomeActivity;
import com.example.projectapp.ui.Signup.SingupActivity;
import com.example.projectapp.ui.Start;

import java.util.concurrent.CompletableFuture;

public class LoginActivity extends AppCompatActivity {

    private ImageView bt_hint_pass;
    private EditText edt_pass, edt_name;
    private boolean hint = true;
    private ConstraintLayout bt_login;
    private TextView bt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_hint_pass = findViewById(R.id.imageView18);
        edt_pass = findViewById(R.id.editTextText6);
        edt_name = findViewById(R.id.editTextText5);
        bt_login = findViewById(R.id.constraintLayout17);
        bt_signup = findViewById(R.id.textView62);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                startActivity(intent);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification notification = new Notification();
                if(edt_pass.getText().length() == 0 || edt_name.getText().length() == 0){
                    notification.showAlertDialog(LoginActivity.this, "Bạn chưa nhập đủ thông tin đăng nhập!");
                }
                 else {
                    requestAPI();
                }
            }
        });

        bt_hint_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint = !hint;
                if(hint){
                    bt_hint_pass.setImageResource(R.drawable.baseline_eye_off_24);
                    edt_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_pass.setSelection(edt_pass.getText().length());
                }else {
                    bt_hint_pass.setImageResource(R.drawable.baseline_remove_red_eye_24);
                    edt_pass.setInputType(1);
                    edt_pass.setSelection(edt_pass.getText().length());
                }
            }
        });

    }

    private void requestAPI() {
        RequestUser requestUser = new RequestUser();
        String email = edt_name.getText().toString();
        String password = edt_pass.getText().toString();

        CompletableFuture<User> future = requestUser.requestUserAsync(LoginActivity.this,email, password);
        Log.d("test", "onResponse: a");
        Notification notification = new Notification();
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                runOnUiThread(() -> {
                    if (result != null) {
                        Log.d("test", "onResponse: c");
                        if(result.getUserId() == 0){
                            notification.showAlertDialog(LoginActivity.this, "Email hoặc mật khẩu không chính xác vui lòng nhập lại!");
                            edt_name.setText("");
                            edt_pass.setText("");
                            edt_name.requestFocus();
                        }else {
                            Toast.makeText(LoginActivity.this, "Xin chào, " + result.getFullname()  + "!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            edt_pass.setText("");
                            SharePreferences sharePreferences = new SharePreferences();
                            sharePreferences.sharePreferences(LoginActivity.this, result.getFullname(),"fullname");
                            sharePreferences.sharePreferences(LoginActivity.this, result.getEmail(),"email");
                            sharePreferences.sharePreferences(LoginActivity.this, result.getUsername(),"username");
                            sharePreferences.sharePreferences(LoginActivity.this,String.valueOf(result.getUserId()),"userid");
                            sharePreferences.sharePreferences(LoginActivity.this, result.getDob(),"dob");
                            sharePreferences.sharePreferences(LoginActivity.this, result.getPhone(),"phone");
                            sharePreferences.sharePreferences(LoginActivity.this,result.getGender(),"gender");
                        }
                    } else {
                        Log.d("test", "onResponse: d");
                        Toast.makeText(LoginActivity.this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
//
////         Điều này giúp đợi cho future hoàn thành trước khi chương trình kết thúc.
        future.join();
//
//        }
    }

}