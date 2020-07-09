package com.apri.kas;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apri.kas.DB.DBAccess;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahKasActivity extends AppCompatActivity {

    @BindView(R.id.actTambahKasMenu)
    AutoCompleteTextView actTambahKasMenu;
    @BindView(R.id.tieTambahKasJumlah)
    TextInputEditText tieTambahKasJumlah;
    @BindView(R.id.tieTambahKasKeterangan)
    TextInputEditText tieTambahKasKeterangan;
    @BindView(R.id.btnTambahKasSimpan)
    Button btnTambahKasSimpan;
    private List<String> menu = new ArrayList<>();
    private DBAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kas);
        ButterKnife.bind(this);
        db = new DBAccess(this);

        initToolbar();
        initView();
    }

    private void initView() {
        menu.add("Kas Masuk");
        menu.add("Kas Keluar");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_menu, menu);
        actTambahKasMenu.setAdapter(adapter);
    }

    private void initToolbar() {
        getSupportActionBar().setTitle("Tambah Kas");
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_baseline_arrow_back_24_white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnTambahKasSimpan)
    public void onViewClicked() {
        if(tieTambahKasJumlah.getText().toString().equals("")) {
            Toast.makeText(this, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        if(actTambahKasMenu.getText().toString().equals("Kas Masuk")) {
            db.addKasMasuk(Integer.parseInt(tieTambahKasJumlah.getText().toString()), tieTambahKasKeterangan.getText().toString());
            Toast.makeText(this, "Kas Masuk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            finish();
        } else if (actTambahKasMenu.getText().toString().equals("Kas Keluar")) {
            db.addKasKeluar(Integer.parseInt(tieTambahKasJumlah.getText().toString()), tieTambahKasKeterangan.getText().toString());
            Toast.makeText(this, "Kas Masuk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan kas, silahkan pilih tipe kas dahulu", Toast.LENGTH_SHORT).show();
        }

    }
}