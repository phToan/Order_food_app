package com.example.projectapp.ui.DrawerMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projectapp.ObjectClass.Notification;
import com.example.projectapp.ObjectClass.SharePreferences;
import com.example.projectapp.ObjectClass.User;
import com.example.projectapp.R;
import com.example.projectapp.connectAPI.RequestUser;
import com.example.projectapp.ui.Home.HomeActivity;
import com.example.projectapp.ui.Login.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class EditUserActivity extends AppCompatActivity {
    private ImageView bt_back;
    private EditText edt_name, edt_dob, edt_phone;
    private RadioButton rb_female, rb_male;
    private RadioGroup rg_gender;
    private ConstraintLayout bt_change_infor;
    private int gender = 1;
    private String address = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        bt_back = findViewById(R.id.imageView27);
        edt_name =findViewById(R.id.editTextText11);
        edt_dob = findViewById(R.id.editTextText12);
        rb_male = findViewById(R.id.radioButton3);
        rb_female = findViewById(R.id.radioButton4);
        bt_change_infor = findViewById(R.id.bt_change_infor);
        edt_phone = findViewById(R.id.editTextText13);
        rg_gender = findViewById(R.id.radioGroup2);


        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioButton4){
                    gender = 0;
                }
            }
        });

        SharePreferences sharePreferences = new SharePreferences();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputDate = sharePreferences.getSharedPreferences(this, "dob", "2001-03-30");
        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String outputDate = outputFormat.format(date);

        edt_name.setText(sharePreferences.getSharedPreferences(EditUserActivity.this, "fullname", "Phạm Hải Toàn"));
        edt_phone.setText(sharePreferences.getSharedPreferences(EditUserActivity.this, "phone", "023984839"));
        edt_dob.setText(outputDate);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt_change_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_name.getText().length() > 0 || edt_dob.getText().length() > 0 || edt_phone.getText().length() > 0){
                    requestAPI(EditUserActivity.this, sharePreferences.getSharedPreferences(EditUserActivity.this, "userid", "2"), edt_name.getText().toString(),String.valueOf(gender),edt_dob.getText().toString(), edt_phone.getText().toString() );
                }else {
                    showAlertDialog("Vui lòng điền ít nhất thông tin cần thay đổi!");
                }
            }
        });
    }

    private void requestAPI(Context context,String userid,String fullname, String gender,String dateofbirth, String phone) {
        RequestUser requestUser = new RequestUser();
        CompletableFuture<User> future = requestUser.requestUpdateUser(context, userid, fullname, gender, dateofbirth, phone);
        Log.d("test", "onResponse: a");
        Notification notification = new Notification();
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Xử lý lỗi
                runOnUiThread(() -> {
                    Toast.makeText(EditUserActivity.this, "Đã xảy ra lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            } else {
                // Xử lý dữ liệu user
                runOnUiThread(() -> {
                    if (result != null) {
                        Log.d("test", "onResponse: c");
                        if(result.getUserId() == 0){
                            notification.showAlertDialog(EditUserActivity.this, "Cập nhật thất bại!");
                        }else {
                            Toast.makeText(EditUserActivity.this, " Cập nhật thành công! ", Toast.LENGTH_SHORT).show();
                            SharePreferences sharePreferences = new SharePreferences();
                            sharePreferences.sharePreferences(EditUserActivity.this, result.getFullname(),"fullname");
                            sharePreferences.sharePreferences(EditUserActivity.this, result.getDob(),"dob");
                            sharePreferences.sharePreferences(EditUserActivity.this, result.getPhone(),"phone");
                            sharePreferences.sharePreferences(EditUserActivity.this,result.getGender(),"gender");
                            finish();
                        }
                    } else {
                        Log.d("test", "onResponse: d");
                        Toast.makeText(EditUserActivity.this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//         Điều này giúp đợi cho future hoàn thành trước khi chương trình kết thúc.
        future.join();
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo"); // Tiêu đề của AlertDialog
        builder.setMessage(message); // Nội dung của AlertDialog
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}