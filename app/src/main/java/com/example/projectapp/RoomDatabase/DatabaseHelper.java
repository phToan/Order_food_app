package com.example.projectapp.RoomDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_STORE_ID = "store_id";
    private static final String COLUMN_AVATAR_URL = "avatar_url";
    private static final String COLUMN_SIZE = "size";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DISCOUNT = "discount";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_RATE = "rate";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PRODUCT_NAME + " TEXT,"
            + COLUMN_STORE_ID + " INTEGER,"
            + COLUMN_AVATAR_URL + " TEXT,"
            + COLUMN_SIZE + " TEXT,"
            + COLUMN_PRICE + " REAL,"
            + COLUMN_DISCOUNT + " REAL,"
            + COLUMN_STATUS + " INTEGER,"
            + COLUMN_RATE + " REAL,"
            + COLUMN_DESCRIPTION + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Thực hiện các thao tác nâng cấp cơ sở dữ liệu khi cần thiết
    }
}
