package com.example.projectapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.projectapp.MainActivity;
import com.example.projectapp.R;
import com.example.projectapp.ui.Home.HomeActivity;
import com.example.projectapp.ui.Login.LoginActivity;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ConstraintLayout startBt  = findViewById(R.id.bt_start);
        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Start.this, LoginActivity.class));
            }
        });
    }
}