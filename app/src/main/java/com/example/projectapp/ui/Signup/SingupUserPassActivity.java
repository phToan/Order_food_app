package com.example.projectapp.ui.Signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
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
import com.example.projectapp.ui.Home.HomeActivity;
import com.example.projectapp.ui.Login.LoginActivity;

import java.util.concurrent.CompletableFuture;

public class SingupUserPassActivity extends AppCompatActivity {

    private ImageView bt_back, bt_hint, bt_re_hint;
    private ConstraintLayout bt_signup;
    private boolean hint =true, reHint = true;
    private EditText edt_pass, edt_re_pass, edt_username, edt_email;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_user_pass);

        bt_back= findViewById(R.id.imageView20);
        bt_signup = findViewById(R.id.bt_signup);
        bt_hint = findViewById(R.id.imageView21);
        bt_re_hint = findViewById(R.id.imageView22);
        edt_pass = findViewById(R.id.editTextTextPassword2);
        edt_re_pass = findViewById(R.id.editTextTextPassword3);
        edt_username = findViewById(R.id.editTextText10);
        edt_email = findViewById(R.id.edt_email);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification notification = new Notification();
                SharePreferences sharePreferences = new SharePreferences();
                if(edt_email.getText().length() == 0 || edt_username.getText().length() == 0 || edt_pass.getText().length() == 0 || edt_re_pass.getText().length() == 0){
                    notification.showAlertDialog(SingupUserPassActivity.this, "Vui lòng đền đầy đủ thông tin đăng ký");
                }else if(!edt_pass.getText().toString().equals(edt_re_pass.getText().toString())){
                    notification.showAlertDialog(SingupUserPassActivity.this, "Mật khẩu nhập lại chưa khớp với mật khẩu đăng ký!");
                }else {
                    requestAPI();
                }

            }
        });

        bt_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint = !hint;
                if(hint){
                    bt_hint.setImageResource(R.drawable.baseline_eye_off_24);
                    edt_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_pass.setSelection(edt_pass.getText().length());
                }else {
                    bt_hint.setImageResource(R.drawable.baseline_remove_red_eye_24);
                    edt_pass.setInputType(1);
                    edt_pass.setSelection(edt_pass.getText().length());
                }
            }
        });

        bt_re_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reHint = !reHint;
                if(reHint){
                    bt_re_hint.setImageResource(R.drawable.baseline_eye_off_24);
                    edt_re_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_re_pass.setSelection(edt_re_pass.getText().length());
                }else {
                    bt_re_hint.setImageResource(R.drawable.baseline_remove_red_eye_24);
                    edt_re_pass.setInputType(1);
                    edt_re_pass.setSelection(edt_re_pass.getText().length());
                }
            }
        });
    }

    private void requestAPI() {
        RequestUser requestUser = new RequestUser();
        User user = new User();
        SharePreferences sharePreferences = new SharePreferences();

        user.setEmail(edt_email.getText().toString());
        user.setUsername(edt_username.getText().toString());
        user.setPassword(edt_pass.getText().toString());
        user.setFullname(sharePreferences.getSharedPreferences(SingupUserPassActivity.this,"signup_name","err"));
        user.setPhone(sharePreferences.getSharedPreferences(SingupUserPassActivity.this,"signup_phone","err"));
        user.setGender(sharePreferences.getSharedPreferences(SingupUserPassActivity.this,"signup_gender","1"));
        user.setDob(sharePreferences.getSharedPreferences(SingupUserPassActivity.this,"signup_dob","err"));

        CompletableFuture<String> future = requestUser.requestSignupUser(SingupUserPassActivity.this, user);
        Notification notification = new Notification();
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(SingupUserPassActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                runOnUiThread(() -> {
                    if (result.equals("Signup successfully")) {
                        LoginActivity loginActivity = new LoginActivity();
                        notification.showAlertDialogIntent(SingupUserPassActivity.this, "Đăng ký tài khoản thành công!",loginActivity);

                    } else {
                        Log.d("test", "onResponse: d");
                        Toast.makeText(SingupUserPassActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SingupUserPassActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        future.join();
        }
}