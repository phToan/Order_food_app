package com.example.projectapp.ObjectClass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

public class Notification {
    public void showAlertDialog(Context context, String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo"); // Tiêu đề của AlertDialog
        builder.setMessage(mess); // Nội dung của AlertDialog

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showAlertDialogIntent(Context context1, String mess, Context context2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context1);
        builder.setTitle("Thông báo"); // Tiêu đề của AlertDialog
        builder.setMessage(mess); // Nội dung của AlertDialog

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(context1, context2.getClass());
                context1.startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
