package com.example.projectapp.ui.Signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.R;

public class SingupActivity extends AppCompatActivity {

    private ConstraintLayout bt_continue;
    private ImageView bt_back;
    private EditText edt_name, edt_dob, edt_phone;
    private RadioGroup rg_gender;
    private RadioButton rb_nam, rb_nu;
    private String gender = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        bt_continue = findViewById(R.id.bt_continue);
        bt_back = findViewById(R.id.imageView19);
        edt_name = findViewById(R.id.editTextText7);
        edt_dob = findViewById(R.id.editTextText8);
        rg_gender = findViewById(R.id.radioGroup);
        rb_nam = findViewById(R.id.radioButton);
        rb_nu = findViewById(R.id.radioButton2);
        edt_phone = findViewById(R.id.editTextText9);

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioButton){
                    gender = "1";
                } else if (i == R.id.radioButton2) {
                    gender = "0";
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePreferences sharePreferences = new SharePreferences();
                Notification notification = new Notification();
                if(edt_name.getText().length() == 0 || edt_dob.getText().length() == 0 || edt_phone.getText().length() == 0 || gender.equals("")){
                    notification.showAlertDialog(SingupActivity.this, "Vui lòng điền đầy đủ thông tin đăng ký");
                }else {
                    sharePreferences.sharePreferences(SingupActivity.this, edt_name.getText().toString(), "signup_name");
                    sharePreferences.sharePreferences(SingupActivity.this, edt_dob.getText().toString(), "signup_dob");
                    sharePreferences.sharePreferences(SingupActivity.this, edt_phone.getText().toString(), "signup_phone");
                    sharePreferences.sharePreferences(SingupActivity.this, gender, "signup_gender");
                    Intent intent = new Intent(SingupActivity.this, SingupUserPassActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}