package com.apri.kas.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_kas";
    private static final int DATABASE_VERSION = 1;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBAccess.CREATE_KAS_MASUK);
        sqLiteDatabase.execSQL(DBAccess.CREATE_KAS_KELUAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBAccess.TABLE_KAS_MASUK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBAccess.TABLE_KAS_KELUAR);
        onCreate(sqLiteDatabase);
    }
}
