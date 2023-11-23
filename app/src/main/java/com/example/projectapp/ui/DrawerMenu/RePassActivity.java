package com.example.projectapp.ui.DrawerMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectapp.R;

public class RePassActivity extends AppCompatActivity {
    private boolean hint = true, reHint = true, newHint= true;
    private ImageView bt_hint, bt_re_hint, bt_new_hint;
    private ImageView bt_back;
    private EditText edt_pass,edt_re_pass,edt_new_pass;
    private ConstraintLayout bt_change_pass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_pass);
        bt_back =  findViewById(R.id.imageView23);
        bt_hint = findViewById(R.id.imageView24);
        bt_new_hint = findViewById(R.id.imageView25);
        bt_re_hint =  findViewById(R.id.imageView26);
        edt_pass = findViewById(R.id.editTextTextPassword);
        edt_new_pass = findViewById(R.id.editTextTextPassword4);
        edt_re_pass = findViewById(R.id.editTextTextPassword5);
        bt_change_pass = findViewById(R.id.bt_change_pass);

        bt_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_pass.getText().length() == 0 || edt_new_pass.getText().length() == 0 || edt_re_pass.getText().length() == 0){
                    showAlertDialog("Vui lòng điền đầy đủ thông tin!");
                }else if(edt_pass.getText().toString().equals(edt_new_pass.getText().toString())){
                    showAlertDialog("Mật khẩu mới không được giống mật khẩu hiện tại!");
                    edt_new_pass.setText("");
                    edt_re_pass.setText("");
                    edt_new_pass.requestFocus();
                } else if (!edt_new_pass.getText().toString().equals(edt_re_pass.getText().toString())) {
                    showAlertDialog("Nhập lại mật khẩu mới chưa chính xác!");
                    edt_re_pass.setText("");
                    edt_re_pass.requestFocus();
                }else {
                    Toast.makeText(RePassActivity.this, "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        bt_new_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newHint = !newHint;
                if(newHint){
                    bt_new_hint.setImageResource(R.drawable.baseline_eye_off_24);
                    edt_new_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edt_new_pass.setSelection(edt_new_pass.getText().length());
                }else {
                    bt_new_hint.setImageResource(R.drawable.baseline_remove_red_eye_24);
                    edt_new_pass.setInputType(1);
                    edt_new_pass.setSelection(edt_new_pass.getText().length());
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

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo"); // Tiêu đề của AlertDialog
        builder.setMessage(message); // Nội dung của AlertDialog
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
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