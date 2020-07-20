package com.apri.kas.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.apri.kas.Models.M_KasKeluar;
import com.apri.kas.Models.M_KasMasuk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBAccess {
    public static final String TABLE_KAS_MASUK = "tbl_kas_masuk";
    public static final String TABLE_KAS_KELUAR = "tbl_kas_keluar";
    public static final String CREATE_KAS_MASUK = "CREATE TABLE " + TABLE_KAS_MASUK + "("
            + "id_kas_masuk INTEGER PRIMARY KEY,"
            + "jumlah INTEGER,"
            + "keterangan TEXT,"
            + "tgl REAL"
            + ")";
    public static final String CREATE_KAS_KELUAR = "CREATE TABLE " + TABLE_KAS_KELUAR + "("
            + "id_kas_keluar INTEGER PRIMARY KEY,"
            + "jumlah INTEGER,"
            + "keterangan TEXT,"
            + "tgl REAL"
            + ")";
    private final SQLiteDatabase database;

    public DBAccess(Context context) {
        DBManager manager = new DBManager(context);
        this.database = manager.getWritableDatabase();
    }

    public void addKasMasuk(Integer jumlah, String keterangan) {
        Calendar calendar = Calendar.getInstance();
        database.execSQL("INSERT OR REPLACE INTO " + TABLE_KAS_MASUK + "(id_kas_masuk, jumlah, keterangan, tgl)" +
                " VALUES ('" + setIdKasMasuk() + "', '" + jumlah + "', '" + keterangan + "', '" + calendar.getTimeInMillis() + "')");
    }

    public void addKasKeluar(Integer jumlah, String keterangan) {
        Calendar calendar = Calendar.getInstance();
        database.execSQL("INSERT OR REPLACE INTO " + TABLE_KAS_KELUAR + "(id_kas_keluar, jumlah, keterangan, tgl)" +
                " VALUES ('" + setIdKasKeluar() + "', '" + jumlah + "', '" + keterangan + "', '" + calendar.getTimeInMillis() + "')");
    }

    public void updateKasMasuk(Integer jumlah, String keterangan, Integer id) {
        database.execSQL("UPDATE " + TABLE_KAS_MASUK + " SET jumlah = " + jumlah + ", keterangan = '" + keterangan + "' WHERE id_kas_masuk = " + id);
    }

    public void updateKasKeluar(Integer jumlah, String keterangan, Integer id) {
        database.execSQL("UPDATE " + TABLE_KAS_KELUAR + " SET jumlah = " + jumlah + ", keterangan = '" + keterangan + "' WHERE id_kas_keluar = " + id);
    }

    public List<M_KasMasuk> getAllKasMasuk() {
        List<M_KasMasuk> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_KAS_MASUK + " ORDER BY tgl DESC", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(new M_KasMasuk(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),cursor.getLong(3)));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public List<M_KasKeluar> getAllKasKeluar() {
        List<M_KasKeluar> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_KAS_KELUAR + " ORDER BY tgl DESC", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(new M_KasKeluar(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),cursor.getLong(3)));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public Integer getSumKasMasuk() {
        String selectQuery = "SELECT SUM(jumlah) as jumlah FROM " + TABLE_KAS_MASUK + " LIMIT 1";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public Integer getSumKasKeluar() {
        String selectQuery = "SELECT SUM(jumlah) as jumlah FROM " + TABLE_KAS_KELUAR + " LIMIT 1";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    private Integer setIdKasMasuk() {
        String selectQuery = "SELECT id_kas_masuk FROM " + TABLE_KAS_MASUK + " ORDER BY id_kas_masuk DESC LIMIT 1";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        try {
            return cursor.getInt(0) + 1;
        } catch (IndexOutOfBoundsException e) {
            return 1;
        }
    }

    private Integer setIdKasKeluar() {
        String selectQuery = "SELECT id_kas_keluar FROM " + TABLE_KAS_KELUAR + " ORDER BY id_kas_keluar DESC LIMIT 1";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        try {
            return cursor.getInt(0) + 1;
        } catch (IndexOutOfBoundsException e) {
            return 1;
        }
    }

    public void deletKasMasuk(Integer id) {
        database.execSQL("DELETE FROM " + TABLE_KAS_MASUK + " WHERE id_kas_masuk = " + id);
    }

    public void deletKasKeluar(Integer id) {
        database.execSQL("DELETE FROM " + TABLE_KAS_KELUAR + " WHERE id_kas_keluar = " + id);
    }

    public void closeDB() {
        if (database != null && database.isOpen())
            database.close();
    }
}
