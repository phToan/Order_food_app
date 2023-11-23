package com.example.projectapp.ObjectClass;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {
    public void sharePreferences(Context context, String value, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSharedPreferences(Context context, String key, String s1){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String retrievedString = sharedPreferences.getString(key, s1);
        return retrievedString;
    }
}
